/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation.util;

import java.io.File;

/**
 * The Class FileUtil.
 */
public class FileUtil {
	
	/**
	 * Checks if is file exist.
	 *
	 * @param assetDestinationLocation the asset destination location
	 * @param id the id
	 * @param assetId the asset id
	 * @return true, if is file exist
	 */
	public static boolean isFileExist(String assetDestinationLocation, String id, String assetId)
	{
		String filePath = assetDestinationLocation+"/"+id+"/"+assetId+".xml";
		return (new File(filePath).isFile());
	}
	
	/**
	 * Find number of assets.
	 *
	 * @param dirPath the dir path
	 * @return the int
	 */
	public static int findNumberOfAssets(String dirPath)
	{
		int count = 0;
		String[] fileNames = new File(dirPath).list();
		for(String fileName : fileNames)
		{
			if(fileName.endsWith(".xml"))
				count++;
		}
		return (count-1);
	}
	
	/**
	 * Delete dir.
	 *
	 * @param dir the dir
	 * @return true, if successful
	 */
	public static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
	}

}
