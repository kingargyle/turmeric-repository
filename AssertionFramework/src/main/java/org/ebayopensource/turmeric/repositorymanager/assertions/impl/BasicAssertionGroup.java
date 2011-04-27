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

import org.ebayopensource.turmeric.repositorymanager.assertions.Assertable;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup;


/**
 * An AssertionGroup contains a collection of Assertables.
 * Two AssertionGroups with the same name and version are "equal"
 * (group1.equals(group2) is true).
 * 
 * @author pcopeland
 */
public class BasicAssertionGroup
    implements AssertionGroup, Assertable
{
    private String name;
    private String version;
    protected String description;
    protected Collection<Assertable> groupMembers;
    private int hashCode;

    /**
     * Constructs a "hollow" AssertionGroup. Subclasses are responsible
     * for completing the state of the object.
     * 
     * @param name the name of the AssertionGroup.
     * @param version the version of the AssertionGroup.
     */
    public BasicAssertionGroup(String name, String version)
    {
        this.name = name;
        this.version = version;
        this.description = "{hollow}";
        this.hashCode = (name+"#"+version).hashCode();
    }

    /**
     * Constructs an AssertionGroup.
     * 
     * @param name the name of the AssertionGroup.
     * @param version the version of the AssertionGroup.
     * @param description the description of the AssertionGroup.
     * @param groupMembers the collection of Assertables.
     */
    public BasicAssertionGroup(
            String name,
            String version,
            String description,
            Collection<Assertable> groupMembers)
    {
        this(name, version);
        this.description = description;
        this.groupMembers = groupMembers;
        this.hashCode = (name+"#"+version).hashCode();
    }

    /**
     * Returns the name of this AssertionGroup.
     * 
     * @return the name of this AssertionGroup.
     */
    @Override
	public String getName() { return name; }

    /**
     * Returns the version of this AssertionGroup. When the definition
     * of an AssertionGroup is updated a new version should be created.
     * 
     * @return the version of this AssertionGroup.
     */
    @Override
	public String getVersion() { return version; }

    /**
     * Returns the description of this AssertionGroup.
     * 
     * @return the description of this AssertionGroup.
     */
    @Override
	public String getDescription() { return description; }

    /**
     * Returns the Assertablea in this AssertionGroup.
     * 
     * @return the Assertablea in this AssertionGroup.
     */
    @Override
	public Collection<? extends Assertable> getGroupMembers() { return groupMembers; }

    /**
     * Returns true if the argument is a AssertionGroup with equal name and version.
     * 
     * @return true if the argument is an AssertionGroup with equal name and version.
     */
    @Override
	public boolean equals(Object o)
    {
        if (!(o instanceof AssertionGroup))
            return false;

        AssertionGroup oag = (AssertionGroup)o;
        String oagName = oag.getName();
        String oagVersion = oag.getVersion();
        return
            ((name == null && oagName == null)
                    || (name != null && name.equals(oagName)))
            &&
            ((version == null && oagVersion == null)
                    || (version != null && version.equals(oagVersion)));
    }

    /**
     * Returns the hashCode for this AssertionGroup.
     * 
     * @return the hashCode for this AssertionGroup.
     */
    @Override
	public int hashCode() { return hashCode; }

    @Override
	public String toString()
    {
        return "AssertionGroup["+name+",version="+version+"]";
    }
}
