/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sitewhere.grpc.client.ApiDemux;
import com.sitewhere.grpc.client.spi.client.IDeviceEventManagementApiChannel;
import com.sitewhere.grpc.client.spi.client.IDeviceEventManagementApiDemux;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.IMicroservice;
import com.sitewhere.spi.microservice.IMicroserviceIdentifiers;

/**
 * Demultiplexes device event management requests across one or more API
 * channels.
 * 
 * @author Derek
 *
 * @param <IAssetManagementApiChannel>
 */
public class DeviceEventManagementApiDemux extends ApiDemux<IDeviceEventManagementApiChannel>
	implements IDeviceEventManagementApiDemux {

    /** Static logger instance */
    private static Logger LOGGER = LogManager.getLogger();

    public DeviceEventManagementApiDemux(IMicroservice microservice) {
	super(microservice);
    }

    /*
     * @see com.sitewhere.grpc.model.spi.IApiDemux#getTargetIdentifier()
     */
    @Override
    public String getTargetIdentifier() {
	return IMicroserviceIdentifiers.EVENT_MANAGEMENT;
    }

    /*
     * @see
     * com.sitewhere.grpc.model.spi.IApiDemux#createApiChannel(java.lang.String)
     */
    @Override
    public IDeviceEventManagementApiChannel createApiChannel(String host) throws SiteWhereException {
	return new DeviceEventManagementApiChannel(this, getMicroservice(), host);
    }

    /*
     * @see com.sitewhere.spi.server.lifecycle.ILifecycleComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
	return LOGGER;
    }
}