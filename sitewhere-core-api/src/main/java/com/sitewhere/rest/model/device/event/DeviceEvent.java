/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.device.event;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sitewhere.rest.model.common.MetadataProvider;
import com.sitewhere.rest.model.datatype.JsonDateSerializer;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.asset.IAssetReference;
import com.sitewhere.spi.device.DeviceAssignmentType;
import com.sitewhere.spi.device.event.DeviceEventType;
import com.sitewhere.spi.device.event.IDeviceEvent;

/**
 * Model object for an event originating from a remote device.
 * 
 * @author dadams
 */
@JsonInclude(Include.NON_NULL)
public abstract class DeviceEvent extends MetadataProvider
	implements IDeviceEvent, Comparable<IDeviceEvent>, Serializable {

    /** For Java serialization */
    private static final long serialVersionUID = 6581066174724675701L;

    /** Unqiue id for event */
    private String id;

    /** Alternate (external) id for event */
    private String alternateId;

    /** Event type indicator */
    private DeviceEventType eventType;

    /** Site id */
    private UUID siteId;

    /** Device assignment id */
    private UUID deviceAssignmentId;

    /** Device assignment type */
    private DeviceAssignmentType assignmentType;

    /** Asset reference */
    private IAssetReference assetReference;

    /** Date event occurred */
    private Date eventDate;

    /** Date event was received */
    private Date receivedDate;

    public DeviceEvent(DeviceEventType type) {
	this.eventType = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDeviceEvent#getId()
     */
    @Override
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.event.IDeviceEvent#getAlternateId()
     */
    @Override
    public String getAlternateId() {
	return alternateId;
    }

    public void setAlternateId(String alternateId) {
	this.alternateId = alternateId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.event.IDeviceEvent#getEventType()
     */
    @Override
    public DeviceEventType getEventType() {
	return eventType;
    }

    public void setEventType(DeviceEventType eventType) {
	this.eventType = eventType;
    }

    /*
     * @see com.sitewhere.spi.device.event.IDeviceEvent#getSiteId()
     */
    @Override
    public UUID getSiteId() {
	return siteId;
    }

    public void setSiteId(UUID siteId) {
	this.siteId = siteId;
    }

    /*
     * @see com.sitewhere.spi.device.event.IDeviceEvent#getDeviceAssignmentId()
     */
    @Override
    public UUID getDeviceAssignmentId() {
	return deviceAssignmentId;
    }

    public void setDeviceAssignmentId(UUID deviceAssignmentId) {
	this.deviceAssignmentId = deviceAssignmentId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDeviceEvent#getAssignmentType()
     */
    @Override
    public DeviceAssignmentType getAssignmentType() {
	return assignmentType;
    }

    public void setAssignmentType(DeviceAssignmentType assignmentType) {
	this.assignmentType = assignmentType;
    }

    /*
     * @see com.sitewhere.spi.device.event.IDeviceEvent#getAssetReference()
     */
    @Override
    public IAssetReference getAssetReference() {
	return assetReference;
    }

    public void setAssetReference(IAssetReference assetReference) {
	this.assetReference = assetReference;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDeviceEvent#getEventDate()
     */
    @Override
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getEventDate() {
	return eventDate;
    }

    public void setEventDate(Date eventDate) {
	this.eventDate = eventDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDeviceEvent#getReceivedDate()
     */
    @Override
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getReceivedDate() {
	return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
	this.receivedDate = receivedDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(IDeviceEvent other) {
	if ((getEventDate() != null) && (other.getEventDate() != null)) {
	    return getEventDate().compareTo(other.getEventDate());
	}
	return 0;
    }

    /**
     * Create a copy of an SPI object. Used by web services for marshaling.
     * 
     * @param source
     * @param target
     */
    public static void copy(IDeviceEvent source, DeviceEvent target) throws SiteWhereException {
	target.setId(source.getId());
	target.setAlternateId(source.getAlternateId());
	target.setSiteId(source.getSiteId());
	target.setDeviceAssignmentId(source.getDeviceAssignmentId());
	target.setAssignmentType(source.getAssignmentType());
	target.setAssetReference(source.getAssetReference());
	target.setReceivedDate(source.getReceivedDate());
	target.setEventDate(source.getEventDate());
	MetadataProvider.copy(source, target);
    }
}