/*
 * IVersion.java 
 * --------------------------------------------------------------------------------------
 * Copyright (c) Reveal Technologies, LLC. All rights reserved. http://www.reveal-tech.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spi.system;

import com.sitewhere.spi.server.ISiteWhereServer;

/**
 * Interface for getting version information.
 * 
 * @author Derek
 */
public interface IVersion {

	/**
	 * Get full edition name.
	 * 
	 * @return
	 */
	public String getEdition();

	/**
	 * Get the short identifier for edition.
	 * 
	 * @return
	 */
	public String getEditionIdentifier();

	/**
	 * Gets the Maven version identifier.
	 * 
	 * @return
	 */
	public String getVersionIdentifier();

	/**
	 * Gets the build timestamp.
	 * 
	 * @return
	 */
	public String getBuildTimestamp();

	/**
	 * Get edition-specific server class that implements {@link ISiteWhereServer}.
	 * 
	 * @return
	 */
	public Class<? extends ISiteWhereServer> getServerClass();
}