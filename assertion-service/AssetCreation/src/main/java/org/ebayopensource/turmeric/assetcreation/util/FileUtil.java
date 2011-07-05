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

public class FileUtil {
	
	public static boolean isFileExist(String assetDestinationLocation, String id, String assetId)
	{
		String filePath = assetDestinationLocation+"/"+id+"/"+assetId+".xml";
		return (new File(filePath).isFile());
	}
	
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
