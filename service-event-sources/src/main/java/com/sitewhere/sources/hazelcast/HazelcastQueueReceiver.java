/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.sources.hazelcast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hazelcast.core.IQueue;
import com.sitewhere.communication.hazelcast.IHazelcastConfiguration;
import com.sitewhere.sources.DecodedDeviceRequest;
import com.sitewhere.sources.InboundEventReceiver;
import com.sitewhere.sources.spi.IInboundEventReceiver;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.hazelcast.ISiteWhereHazelcast;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;

/**
 * Implementation of {@link IInboundEventReceiver} that reads events from a
 * Hazelcast queue and pushes them into the processing pipeline.
 * 
 * @author Derek
 */
public class HazelcastQueueReceiver extends InboundEventReceiver<DecodedDeviceRequest<?>> {

    /** Static logger instance */
    private static Logger LOGGER = LogManager.getLogger();

    /** Hazelcast configuration */
    private IHazelcastConfiguration hazelcastConfiguration;

    /** Queue of events to be processed */
    private IQueue<DecodedDeviceRequest<?>> eventQueue;

    /** Used to queue processing in a separate thread */
    private ExecutorService executor;

    /** Name of Hazelcast queue to listen on */
    private String queueName = ISiteWhereHazelcast.QUEUE_ALL_EVENTS;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#start(com.sitewhere.spi
     * .server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	this.eventQueue = getHazelcastConfiguration().getHazelcastInstance().getQueue(getQueueName());
	LOGGER.info("Receiver listening for events on Hazelcast queue: " + getQueueName());
	this.executor = Executors.newSingleThreadExecutor(new ProcessorsThreadFactory());
	executor.submit(new HazelcastQueueProcessor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#stop(com.sitewhere.spi.
     * server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void stop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	if (executor != null) {
	    executor.shutdownNow();
	}
	this.executor = null;
    }

    /**
     * Handles Hazelcast queue processing in a separate thread.
     * 
     * @author Derek
     */
    private class HazelcastQueueProcessor implements Runnable {

	@Override
	public void run() {
	    while (true) {
		try {
		    DecodedDeviceRequest<?> payload = getEventQueue().take();
		    onEventPayloadReceived(payload, null);
		    LOGGER.debug("Processed event from " + payload.getHardwareId() + " from Hazelcast event queue.");
		} catch (InterruptedException e) {
		    LOGGER.warn("Hazelcast queue processor interrupted.");
		    return;
		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.server.lifecycle.ILifecycleComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
	return LOGGER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * getDisplayName()
     */
    @Override
    public String getDisplayName() {
	return "Hazelcast";
    }

    /** Used for naming processor threads */
    private class ProcessorsThreadFactory implements ThreadFactory {

	/** Counts threads */
	private AtomicInteger counter = new AtomicInteger();

	public Thread newThread(Runnable r) {
	    return new Thread(r, "SiteWhere Hazelcast(" + getEventSource().getSourceId() + ") Receiver "
		    + counter.incrementAndGet());
	}
    }

    public IHazelcastConfiguration getHazelcastConfiguration() {
	return hazelcastConfiguration;
    }

    public void setHazelcastConfiguration(IHazelcastConfiguration hazelcastConfiguration) {
	this.hazelcastConfiguration = hazelcastConfiguration;
    }

    public IQueue<DecodedDeviceRequest<?>> getEventQueue() {
	return eventQueue;
    }

    public void setEventQueue(IQueue<DecodedDeviceRequest<?>> eventQueue) {
	this.eventQueue = eventQueue;
    }

    public String getQueueName() {
	return queueName;
    }

    public void setQueueName(String queueName) {
	this.queueName = queueName;
    }
}