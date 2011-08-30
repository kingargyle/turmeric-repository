/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

// TODO: Auto-generated Javadoc
/**
 * AssertionResultItem contains an AssertionResult failure or warning
 * information.
 * 
 * @author pcopeland
 */
public class AssertionResultItem
{
    
    /** The content name. */
    private String contentName;
    
    /** The message text. */
    private String messageText;
    
    /** The line number. */
    private String lineNumber;

    /**
     * Instantiates a new assertion result item.
     *
     * @param contentName the content name
     * @param messageText the message text
     * @param lineNumber the line number
     */
    public AssertionResultItem(
            String contentName,
            String messageText,
            String lineNumber)
    {
        this.contentName = contentName;
        this.messageText = messageText;
        this.lineNumber = lineNumber;
    }

    /**
     * Returns the name of the content associated with the result item,
     * or null if not known.
     * 
     * @return the name of the content associated with the result item.
     */
    public String getContentName() { return contentName; }

    /**
     * Returns the message text associated with the result item.
     * 
     * @return the message text associated with the result item.
     */
    public String getMessageText() { return messageText; }

    /**
     * Returns the line number associated with the result item, or null.
     * 
     * @return the line number associated with the result item.
     */
    public String getLineNumber() { return lineNumber; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        String tsContent = (contentName == null) ? "" : ",content="+contentName;
        String tsLine = (lineNumber == null) ? "" : ",line="+lineNumber;
        return "["+messageText + tsContent + tsLine + "]";
    }
}
