/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.common.util;

import java.io.File;
import java.io.FileInputStream;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.errorlibrary.assertion.ErrorConstants;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;



public class AssertionsServiceOperationUtil 
{

	/**
	 * @author csubhash
	 * @param exception
	 * @return returns a message parsed error data object
	 */
	public static CommonErrorData parseGeneralExceptionMessage(Exception exception)
	{
		StackTraceElement[] stackTraceElements = exception.getStackTrace();
		int index = 0;
		
		String exceptionClass = exception.getClass().getCanonicalName();
		String fileName = stackTraceElements[index].getFileName();
		String className = stackTraceElements[index].getClassName();
		String methodName = stackTraceElements[index].getMethodName();
		String lineNo = Integer.valueOf(stackTraceElements[index].getLineNumber()).toString();
		String message = exception.getMessage();
		
		String[] params = new String[]{exceptionClass,fileName,className,methodName,lineNo,message};
		
		CommonErrorData errorData = ErrorDataFactory.createErrorData(ErrorConstants.UNKNOWN_EXCEPTION, ErrorConstants.ERRORDOMAIN, params);
		return errorData;
	}
	
	/**
	 * Fetches a property value from a file
	 * 
	 * @param fileLocation --> location of the property file
	 * @param propName --> Key of the property whose value is to be fetched
	 * @return
	 */
	public static String getPropertyValue(String fileLocation, String propName) {
		java.util.Properties props = new java.util.Properties();
		try {
			File file = new File(fileLocation);
			FileInputStream fi = new FileInputStream(file);
			props.load(fi);
		} catch (Exception e) {
			return null;
		}
		return props.getProperty(propName);
	}
}
