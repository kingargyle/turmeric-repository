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

import org.ebayopensource.turmeric.repositorymanager.assertions.Assertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;


/**
 * BasicAssertion implements Assertion.  This is a base class that can
 * be extended with other constructors. For example, a class that is
 * constructed with a Repository asset can extend BasicAssertion.
 * 
 * @author pcopeland
 */
public class BasicAssertion
    implements Assertion
{
    
    /** The name. */
    private String name;
    
    /** The version. */
    private String version;
    
    /** The description. */
    protected String description;
    
    /** The error severity. */
    protected ValidationResult errorSeverity = ValidationResult.MUST;
    
    /** The assertion script. */
    protected AssertionContent assertionScript;
    
    /** The assertion processor type. */
    protected String assertionProcessorType;
    
    /** The assertion modules. */
    protected Collection<AssertionModule> assertionModules = null;
    
    /** The hash code. */
    private int hashCode;

    /**
     * Constructs a "hollow" Assertion.  Subclasses are responsible for
     * completing the state of the object.
     * 
     * @param name the name of the Assertion
     * @param version the version of the Assertion
     */
    public BasicAssertion(String name, String version)
    {
        this.name = name;
        this.version = version;
        this.description = "{hollow}";
        this.hashCode = (name+"#"+version).hashCode();
    }

    /**
     * Creates a BasicAssertion.
     *
     * @param name name of this BasicAssertion.
     * @param version version of this BasicAssertion.
     * @param description description of this BasicAssertion.
     * @param errorSeverity severity level when this assertion fails.
     * @param assertionScript assertionScript for this BasicAssertion.
     * @param assertionProcessorType the assertion processor type
     * @param assertionModules AssertionModules for this BasicAssertion.
     */
    public BasicAssertion(
            String name,
            String version,
            String description,
            ValidationResult errorSeverity,
            AssertionContent assertionScript,
            String assertionProcessorType,
            Collection<AssertionModule> assertionModules)
    {
        this(name, version);
        this.description = description;
        this.errorSeverity = errorSeverity;
        this.assertionScript = assertionScript;
        this.assertionProcessorType = assertionProcessorType;
        this.errorSeverity = errorSeverity;
        this.assertionModules = assertionModules;
        this.hashCode = (name+"#"+version).hashCode();
    }

    /**
     * Returns the name of this Assertion.
     * 
     * @return the name of this Assertion.
     */
    @Override
	public String getName() { return name; } 

    /**
     * Returns the version of this Assertion.
     * 
     * @return the version of this Assertion.
     */
    @Override
	public String getVersion() { return version; } 

    /**
     * Returns the description of this Assertion.
     * 
     * @return the description of this Assertion.
     */
    @Override
	public String getDescription()  { return description; } 

    /**
     * Returns the failure or warning severity for this Assertion.
     * 
     * @return the failure or warning severity for this Assertion.
     */
    @Override
	public ValidationResult getErrorSeverity() { return errorSeverity; }

    /**
     * Returns the executable script that is the validation rule.
     * 
     * @return the executable script that is the validation rule.
     */
    @Override
	public AssertionContent getAssertionScript() { return assertionScript; }

    /**
     * Returns the AssertionProcessor type that executes the validation
     * rule.
     * 
     * @return the AssertionProcessor type that executes the validation
     * rule.
     */
    @Override
	public String getAssertionProcessorType()
    {
        return assertionProcessorType;
    }

    /**
     * Returns the AssertionModules that are used by the AssertionScript or null.
     * 
     * @return the AssertionModules that are used by the AssertionScript or null.
     */
    @Override
	public Collection<AssertionModule> getAssertionModules() { return assertionModules; }

    /**
     * Returns true if the argument is a Assertion with equal name and version.
     *
     * @param o the o
     * @return true if the argument is an Assertion with equal name and version.
     */
    @Override
	public boolean equals(Object o)
    {
        if (o == null || !(o instanceof Assertion))
            return false;

        Assertion oa = (Assertion)o;
        String oaName = oa.getName();
        String oaVersion = oa.getVersion();
        return
            ((name == null && oaName == null)
                    || (name != null && name.equals(oaName)))
            &&
            ((version == null && oaVersion == null)
                    || (version != null && version.equals(oaVersion)));
    }

    /**
     * Returns the hashCode for this BasicAssertion.
     * 
     * @return the hashCode for this BasicAssertion.
     */
    @Override
	public int hashCode() { return hashCode; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return "Assertion["+name
            +",version="+version
            +",processor="+assertionProcessorType
            +",script="+assertionScript+"]";
    }
}
