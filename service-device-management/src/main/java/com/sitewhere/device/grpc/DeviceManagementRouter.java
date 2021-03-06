/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.device.grpc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sitewhere.device.spi.microservice.IDeviceManagementMicroservice;
import com.sitewhere.device.spi.microservice.IDeviceManagementTenantEngine;
import com.sitewhere.grpc.service.*;
import com.sitewhere.microservice.grpc.TenantTokenServerInterceptor;
import com.sitewhere.security.UserContextManager;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.RuntimeServiceNotAvailableException;

import io.grpc.stub.StreamObserver;

/**
 * Routes GRPC calls to service implementations in tenants.
 * 
 * @author Derek
 */
public class DeviceManagementRouter extends DeviceManagementGrpc.DeviceManagementImplBase {

    /** Static logger instance */
    @SuppressWarnings("unused")
    private static Logger LOGGER = LogManager.getLogger();

    /** Parent microservice */
    private IDeviceManagementMicroservice microservice;

    public DeviceManagementRouter(IDeviceManagementMicroservice microservice) {
	this.microservice = microservice;
    }

    /**
     * Based on token passed via GRPC header, look up service implementation running
     * in tenant engine.
     * 
     * @return
     */
    protected DeviceManagementGrpc.DeviceManagementImplBase getTenantImplementation() {
	String tenantId = TenantTokenServerInterceptor.TENANT_ID_KEY.get();
	if (tenantId == null) {
	    throw new RuntimeException("Tenant id not found in device management request.");
	}
	try {
	    IDeviceManagementTenantEngine engine = getMicroservice().getTenantEngineByTenantId(tenantId);
	    if (engine != null) {
		UserContextManager.setCurrentTenant(engine.getTenant());
		return engine.getDeviceManagementImpl();
	    }
	    throw new RuntimeServiceNotAvailableException("Tenant engine not found.");
	} catch (SiteWhereException e) {
	    throw new RuntimeServiceNotAvailableException("Error locating tenant engine.", e);
	}
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceType(com.sitewhere.grpc.service.GCreateDeviceTypeRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceType(GCreateDeviceTypeRequest request,
	    StreamObserver<GCreateDeviceTypeResponse> responseObserver) {
	getTenantImplementation().createDeviceType(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceType(com.sitewhere.grpc.service.GGetDeviceTypeRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceType(GGetDeviceTypeRequest request, StreamObserver<GGetDeviceTypeResponse> responseObserver) {
	getTenantImplementation().getDeviceType(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceTypeByToken(com.sitewhere.grpc.service.GGetDeviceTypeByTokenRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceTypeByToken(GGetDeviceTypeByTokenRequest request,
	    StreamObserver<GGetDeviceTypeByTokenResponse> responseObserver) {
	getTenantImplementation().getDeviceTypeByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceType(com.sitewhere.grpc.service.GUpdateDeviceTypeRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceType(GUpdateDeviceTypeRequest request,
	    StreamObserver<GUpdateDeviceTypeResponse> responseObserver) {
	getTenantImplementation().updateDeviceType(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceTypes(com.sitewhere.grpc.service.GListDeviceTypesRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceTypes(GListDeviceTypesRequest request,
	    StreamObserver<GListDeviceTypesResponse> responseObserver) {
	getTenantImplementation().listDeviceTypes(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceType(com.sitewhere.grpc.service.GDeleteDeviceTypeRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceType(GDeleteDeviceTypeRequest request,
	    StreamObserver<GDeleteDeviceTypeResponse> responseObserver) {
	getTenantImplementation().deleteDeviceType(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceCommand(com.sitewhere.grpc.service. GCreateDeviceCommandRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceCommand(GCreateDeviceCommandRequest request,
	    StreamObserver<GCreateDeviceCommandResponse> responseObserver) {
	getTenantImplementation().createDeviceCommand(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceCommand(com.sitewhere.grpc.service.GGetDeviceCommandRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceCommand(GGetDeviceCommandRequest request,
	    StreamObserver<GGetDeviceCommandResponse> responseObserver) {
	getTenantImplementation().getDeviceCommand(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceCommandByToken(com.sitewhere.grpc.service.
     * GGetDeviceCommandByTokenRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceCommandByToken(GGetDeviceCommandByTokenRequest request,
	    StreamObserver<GGetDeviceCommandByTokenResponse> responseObserver) {
	getTenantImplementation().getDeviceCommandByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceCommand(com.sitewhere.grpc.service. GUpdateDeviceCommandRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceCommand(GUpdateDeviceCommandRequest request,
	    StreamObserver<GUpdateDeviceCommandResponse> responseObserver) {
	getTenantImplementation().updateDeviceCommand(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceCommands(com.sitewhere.grpc.service.GListDeviceCommandsRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceCommands(GListDeviceCommandsRequest request,
	    StreamObserver<GListDeviceCommandsResponse> responseObserver) {
	getTenantImplementation().listDeviceCommands(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceCommand(com.sitewhere.grpc.service. GDeleteDeviceCommandRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceCommand(GDeleteDeviceCommandRequest request,
	    StreamObserver<GDeleteDeviceCommandResponse> responseObserver) {
	getTenantImplementation().deleteDeviceCommand(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceStatus(com.sitewhere.grpc.service.GCreateDeviceStatusRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceStatus(GCreateDeviceStatusRequest request,
	    StreamObserver<GCreateDeviceStatusResponse> responseObserver) {
	getTenantImplementation().createDeviceStatus(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceStatusByCode(com.sitewhere.grpc.service.
     * GGetDeviceStatusByCodeRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceStatusByCode(GGetDeviceStatusByCodeRequest request,
	    StreamObserver<GGetDeviceStatusByCodeResponse> responseObserver) {
	getTenantImplementation().getDeviceStatusByCode(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceStatus(com.sitewhere.grpc.service.GUpdateDeviceStatusRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceStatus(GUpdateDeviceStatusRequest request,
	    StreamObserver<GUpdateDeviceStatusResponse> responseObserver) {
	getTenantImplementation().updateDeviceStatus(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceStatuses(com.sitewhere.grpc.service.GListDeviceStatusesRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceStatuses(GListDeviceStatusesRequest request,
	    StreamObserver<GListDeviceStatusesResponse> responseObserver) {
	getTenantImplementation().listDeviceStatuses(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceStatus(com.sitewhere.grpc.service.GDeleteDeviceStatusRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceStatus(GDeleteDeviceStatusRequest request,
	    StreamObserver<GDeleteDeviceStatusResponse> responseObserver) {
	getTenantImplementation().deleteDeviceStatus(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDevice(com.sitewhere.grpc.service.GCreateDeviceRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDevice(GCreateDeviceRequest request, StreamObserver<GCreateDeviceResponse> responseObserver) {
	getTenantImplementation().createDevice(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDevice(com.sitewhere.grpc.service.GGetDeviceRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDevice(GGetDeviceRequest request, StreamObserver<GGetDeviceResponse> responseObserver) {
	getTenantImplementation().getDevice(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceByHardwareId(com.sitewhere.grpc.service.
     * GGetDeviceByHardwareIdRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceByHardwareId(GGetDeviceByHardwareIdRequest request,
	    StreamObserver<GGetDeviceByHardwareIdResponse> responseObserver) {
	getTenantImplementation().getDeviceByHardwareId(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDevice(com.sitewhere.grpc.service.GUpdateDeviceRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDevice(GUpdateDeviceRequest request, StreamObserver<GUpdateDeviceResponse> responseObserver) {
	getTenantImplementation().updateDevice(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDevices(com.sitewhere.grpc.service.GListDevicesRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDevices(GListDevicesRequest request, StreamObserver<GListDevicesResponse> responseObserver) {
	getTenantImplementation().listDevices(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceElementMapping(com.sitewhere.grpc.service.
     * GCreateDeviceElementMappingRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceElementMapping(GCreateDeviceElementMappingRequest request,
	    StreamObserver<GCreateDeviceElementMappingResponse> responseObserver) {
	getTenantImplementation().createDeviceElementMapping(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceElementMapping(com.sitewhere.grpc.service.
     * GDeleteDeviceElementMappingRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceElementMapping(GDeleteDeviceElementMappingRequest request,
	    StreamObserver<GDeleteDeviceElementMappingResponse> responseObserver) {
	getTenantImplementation().deleteDeviceElementMapping(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDevice(com.sitewhere.grpc.service.GDeleteDeviceRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDevice(GDeleteDeviceRequest request, StreamObserver<GDeleteDeviceResponse> responseObserver) {
	getTenantImplementation().deleteDevice(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceGroup(com.sitewhere.grpc.service.GCreateDeviceGroupRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceGroup(GCreateDeviceGroupRequest request,
	    StreamObserver<GCreateDeviceGroupResponse> responseObserver) {
	getTenantImplementation().createDeviceGroup(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceGroup(com.sitewhere.grpc.service.GGetDeviceGroupRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceGroup(GGetDeviceGroupRequest request,
	    StreamObserver<GGetDeviceGroupResponse> responseObserver) {
	getTenantImplementation().getDeviceGroup(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceGroupByToken(com.sitewhere.grpc.service.
     * GGetDeviceGroupByTokenRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceGroupByToken(GGetDeviceGroupByTokenRequest request,
	    StreamObserver<GGetDeviceGroupByTokenResponse> responseObserver) {
	getTenantImplementation().getDeviceGroupByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceGroup(com.sitewhere.grpc.service.GUpdateDeviceGroupRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceGroup(GUpdateDeviceGroupRequest request,
	    StreamObserver<GUpdateDeviceGroupResponse> responseObserver) {
	getTenantImplementation().updateDeviceGroup(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceGroups(com.sitewhere.grpc.service.GListDeviceGroupsRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceGroups(GListDeviceGroupsRequest request,
	    StreamObserver<GListDeviceGroupsResponse> responseObserver) {
	getTenantImplementation().listDeviceGroups(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceGroupsWithRole(com.sitewhere.grpc.service.
     * GListDeviceGroupsWithRoleRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceGroupsWithRole(GListDeviceGroupsWithRoleRequest request,
	    StreamObserver<GListDeviceGroupsWithRoleResponse> responseObserver) {
	getTenantImplementation().listDeviceGroupsWithRole(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceGroup(com.sitewhere.grpc.service.GDeleteDeviceGroupRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceGroup(GDeleteDeviceGroupRequest request,
	    StreamObserver<GDeleteDeviceGroupResponse> responseObserver) {
	getTenantImplementation().deleteDeviceGroup(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * addDeviceGroupElements(com.sitewhere.grpc.service.
     * GAddDeviceGroupElementsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addDeviceGroupElements(GAddDeviceGroupElementsRequest request,
	    StreamObserver<GAddDeviceGroupElementsResponse> responseObserver) {
	getTenantImplementation().addDeviceGroupElements(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * removeDeviceGroupElements(com.sitewhere.grpc.service.
     * GRemoveDeviceGroupElementsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void removeDeviceGroupElements(GRemoveDeviceGroupElementsRequest request,
	    StreamObserver<GRemoveDeviceGroupElementsResponse> responseObserver) {
	getTenantImplementation().removeDeviceGroupElements(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceGroupElements(com.sitewhere.grpc.service.
     * GListDeviceGroupElementsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceGroupElements(GListDeviceGroupElementsRequest request,
	    StreamObserver<GListDeviceGroupElementsResponse> responseObserver) {
	getTenantImplementation().listDeviceGroupElements(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceAssignment(com.sitewhere.grpc.service.
     * GCreateDeviceAssignmentRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceAssignment(GCreateDeviceAssignmentRequest request,
	    StreamObserver<GCreateDeviceAssignmentResponse> responseObserver) {
	getTenantImplementation().createDeviceAssignment(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceAssignment(com.sitewhere.grpc.service.GGetDeviceAssignmentRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceAssignment(GGetDeviceAssignmentRequest request,
	    StreamObserver<GGetDeviceAssignmentResponse> responseObserver) {
	getTenantImplementation().getDeviceAssignment(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceAssignmentByToken(com.sitewhere.grpc.service.
     * GGetDeviceAssignmentByTokenRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceAssignmentByToken(GGetDeviceAssignmentByTokenRequest request,
	    StreamObserver<GGetDeviceAssignmentByTokenResponse> responseObserver) {
	getTenantImplementation().getDeviceAssignmentByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getCurrentAssignmentForDevice(com.sitewhere.grpc.service.
     * GGetCurrentAssignmentForDeviceRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getCurrentAssignmentForDevice(GGetCurrentAssignmentForDeviceRequest request,
	    StreamObserver<GGetCurrentAssignmentForDeviceResponse> responseObserver) {
	getTenantImplementation().getCurrentAssignmentForDevice(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteDeviceAssignment(com.sitewhere.grpc.service.
     * GDeleteDeviceAssignmentRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteDeviceAssignment(GDeleteDeviceAssignmentRequest request,
	    StreamObserver<GDeleteDeviceAssignmentResponse> responseObserver) {
	getTenantImplementation().deleteDeviceAssignment(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceAssignmentMetadata(com.sitewhere.grpc.service.
     * GUpdateDeviceAssignmentMetadataRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceAssignmentMetadata(GUpdateDeviceAssignmentMetadataRequest request,
	    StreamObserver<GUpdateDeviceAssignmentMetadataResponse> responseObserver) {
	getTenantImplementation().updateDeviceAssignmentMetadata(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateDeviceAssignmentStatus(com.sitewhere.grpc.service.
     * GUpdateDeviceAssignmentStatusRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateDeviceAssignmentStatus(GUpdateDeviceAssignmentStatusRequest request,
	    StreamObserver<GUpdateDeviceAssignmentStatusResponse> responseObserver) {
	getTenantImplementation().updateDeviceAssignmentStatus(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * endDeviceAssignment(com.sitewhere.grpc.service. GEndDeviceAssignmentRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void endDeviceAssignment(GEndDeviceAssignmentRequest request,
	    StreamObserver<GEndDeviceAssignmentResponse> responseObserver) {
	getTenantImplementation().endDeviceAssignment(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceAssignmentHistory(com.sitewhere.grpc.service.
     * GGetDeviceAssignmentHistoryRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceAssignmentHistory(GGetDeviceAssignmentHistoryRequest request,
	    StreamObserver<GGetDeviceAssignmentHistoryResponse> responseObserver) {
	getTenantImplementation().getDeviceAssignmentHistory(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceAssignmentsForSite(com.sitewhere.grpc.service.
     * GGetDeviceAssignmentsForSiteRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceAssignmentsForSite(GGetDeviceAssignmentsForSiteRequest request,
	    StreamObserver<GGetDeviceAssignmentsForSiteResponse> responseObserver) {
	getTenantImplementation().getDeviceAssignmentsForSite(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceAssignmentsForAsset(com.sitewhere.grpc.service.
     * GGetDeviceAssignmentsForAssetRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceAssignmentsForAsset(GGetDeviceAssignmentsForAssetRequest request,
	    StreamObserver<GGetDeviceAssignmentsForAssetResponse> responseObserver) {
	getTenantImplementation().getDeviceAssignmentsForAsset(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createDeviceStream(com.sitewhere.grpc.service.GCreateDeviceStreamRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createDeviceStream(GCreateDeviceStreamRequest request,
	    StreamObserver<GCreateDeviceStreamResponse> responseObserver) {
	getTenantImplementation().createDeviceStream(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getDeviceStreamByStreamId(com.sitewhere.grpc.service.
     * GGetDeviceStreamByStreamIdRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceStreamByStreamId(GGetDeviceStreamByStreamIdRequest request,
	    StreamObserver<GGetDeviceStreamByStreamIdResponse> responseObserver) {
	getTenantImplementation().getDeviceStreamByStreamId(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listDeviceStreams(com.sitewhere.grpc.service.GListDeviceStreamsRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listDeviceStreams(GListDeviceStreamsRequest request,
	    StreamObserver<GListDeviceStreamsResponse> responseObserver) {
	getTenantImplementation().listDeviceStreams(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createSite(com.sitewhere.grpc.service.GCreateSiteRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createSite(GCreateSiteRequest request, StreamObserver<GCreateSiteResponse> responseObserver) {
	getTenantImplementation().createSite(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getSite(com.sitewhere.grpc.service.GGetSiteRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getSite(GGetSiteRequest request, StreamObserver<GGetSiteResponse> responseObserver) {
	getTenantImplementation().getSite(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getSiteByToken(com.sitewhere.grpc.service.GGetSiteByTokenRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getSiteByToken(GGetSiteByTokenRequest request,
	    StreamObserver<GGetSiteByTokenResponse> responseObserver) {
	getTenantImplementation().getSiteByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateSite(com.sitewhere.grpc.service.GUpdateSiteRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateSite(GUpdateSiteRequest request, StreamObserver<GUpdateSiteResponse> responseObserver) {
	getTenantImplementation().updateSite(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listSites(com.sitewhere.grpc.service.GListSitesRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listSites(GListSitesRequest request, StreamObserver<GListSitesResponse> responseObserver) {
	getTenantImplementation().listSites(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteSite(com.sitewhere.grpc.service.GDeleteSiteRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteSite(GDeleteSiteRequest request, StreamObserver<GDeleteSiteResponse> responseObserver) {
	getTenantImplementation().deleteSite(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * createZone(com.sitewhere.grpc.service.GCreateZoneRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createZone(GCreateZoneRequest request, StreamObserver<GCreateZoneResponse> responseObserver) {
	getTenantImplementation().createZone(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getZone(com.sitewhere.grpc.service.GGetZoneRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getZone(GGetZoneRequest request, StreamObserver<GGetZoneResponse> responseObserver) {
	getTenantImplementation().getZone(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * getZoneByToken(com.sitewhere.grpc.service.GGetZoneByTokenRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getZoneByToken(GGetZoneByTokenRequest request,
	    StreamObserver<GGetZoneByTokenResponse> responseObserver) {
	getTenantImplementation().getZoneByToken(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * updateZone(com.sitewhere.grpc.service.GUpdateZoneRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateZone(GUpdateZoneRequest request, StreamObserver<GUpdateZoneResponse> responseObserver) {
	getTenantImplementation().updateZone(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * listZones(com.sitewhere.grpc.service.GListZonesRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listZones(GListZonesRequest request, StreamObserver<GListZonesResponse> responseObserver) {
	getTenantImplementation().listZones(request, responseObserver);
    }

    /*
     * @see
     * com.sitewhere.grpc.service.DeviceManagementGrpc.DeviceManagementImplBase#
     * deleteZone(com.sitewhere.grpc.service.GDeleteZoneRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteZone(GDeleteZoneRequest request, StreamObserver<GDeleteZoneResponse> responseObserver) {
	getTenantImplementation().deleteZone(request, responseObserver);
    }

    public IDeviceManagementMicroservice getMicroservice() {
	return microservice;
    }

    public void setMicroservice(IDeviceManagementMicroservice microservice) {
	this.microservice = microservice;
    }
}