/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;


import java.util.Collection;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule;


/**
 * BasicAssertionModule implements AssertionModule.
 * 
 * @author pcopeland
 */
public class BasicAssertionModule
    implements AssertionModule
{
    
    /** The name. */
    private String name;
    
    /** The version. */
    private String version;
    
    /** The description. */
    private String description;
    
    /** The module scripts. */
    private Collection<AssertionContent> moduleScripts;
    
    /** The assertion processor type. */
    private String assertionProcessorType;

    /**
     * Creates a BasicAssertionModule. By default FailureResult = FAILED and
     * AssertionModules = null.
     *
     * @param name name of this BasicAssertionModule.
     * @param version version of this BasicAssertionModule.
     * @param description description of this BasicAssertionModule.
     * @param moduleScripts the module scripts
     * @param assertionProcessorType the assertion processor type
     */
    public BasicAssertionModule(
            String name,
            String version,
            String description,
            Collection<AssertionContent> moduleScripts,
            String assertionProcessorType)
    {
        this.name = name;
        this.version = version;
        this.description = description;
        this.moduleScripts = moduleScripts;
        this.assertionProcessorType = assertionProcessorType;
    }

    /**
     * Returns the name of this AssertionModule.
     * 
     * @return the name of this AssertionModule.
     */
    @Override
	public String getName() { return name; }

    /**
     * Returns the version of this AssertionModule.
     * 
     * @return the version of this AssertionModule.
     */
    @Override
	public String getVersion() { return version; }

    /**
     * Returns the description of this AssertionModule.
     * 
     * @return the description of this AssertionModule.
     */
    @Override
	public String getDescription() { return description; }

    /**
     * Returns the AssertionScripts contained in this AssertionModule.
     * 
     * @return the AssertionScripts contained in this AssertionModule.
     */
    @Override
	public Collection<AssertionContent> getModuleScripts()
    {
        return moduleScripts;
    }

    /**
     * Returns the AssertionProcessor type that uses this AssertionModule.
     * 
     * @return the AssertionProcessor type that uses this AssertionModule.
     */
    @Override
	public String getAssertionProcessorType() { return assertionProcessorType; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return "AssertionModule["+name
            +",version="+version
            +",processor="+assertionProcessorType+"]";
    }
}
