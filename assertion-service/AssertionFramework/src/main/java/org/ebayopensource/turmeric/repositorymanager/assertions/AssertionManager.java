/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssetReferent;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertableResult;


/**
 * AssertionManager uses AssertionProcessors to validate assertions.
 * 
 * @author pcopeland
 */
public class AssertionManager
{
	
	/** The s_logger. */
	private static Logger s_logger = Logger.getLogger(AssertionManager.class);
    
    /** The assertion processor classes. */
    static private Map<String, Class<? extends AssertionProcessor>>
        assertionProcessorClasses = Collections.synchronizedMap(
            new HashMap<String, Class<? extends AssertionProcessor>>());

    /** The rs location. */
    private String rsLocation; // RepositoryService URL

    // Returns an AssertionProcessor Class. The returned class name is
    // org.ebayopensource.turmeric.repositorymanager.assertions.[assertionProcessorType].AssertionProcessor
    // (with assertionProcessorType converted to lowercase)
    /**
     * New assertion processor class.
     *
     * @param assertionProcessorType the assertion processor type
     * @return the class<? extends assertion processor>
     */
    static private Class<? extends AssertionProcessor> newAssertionProcessorClass(
            String assertionProcessorType)
    {
        Class<? extends AssertionProcessor> apClass = null;
        String apClassName = AssertionManager.class.getPackage().getName()
        + "." + assertionProcessorType.toLowerCase()
        + ".AssertionProcessor";
        try {
            synchronized (assertionProcessorClasses) {
                apClass = assertionProcessorClasses.get(assertionProcessorType);
                if (apClass == null) {
                    apClass = Class.forName(apClassName).asSubclass(AssertionProcessor.class);
                    if (!AssertionProcessor.class.isAssignableFrom(apClass))
                        throw new IllegalArgumentException(apClassName
                                + " is not a "
                                + AssertionProcessor.class.getName());
                    assertionProcessorClasses.put(assertionProcessorType,
                            apClass);
                }
            }
            return apClass;
        } catch (ClassNotFoundException cnfEx) {
            throw new IllegalArgumentException(
                    "Cannot create AssertionProcessor of type " + apClassName,
                    cnfEx);
        }
    }

     // Returns an AssertionProcessor instance for the given type.
    /**
      * New assertion processor.
      *
      * @param assertionProcessorType the assertion processor type
      * @param context the context
      * @return the assertion processor
      */
     static private AssertionProcessor newAssertionProcessor(
            String assertionProcessorType,
            AssertionProcessorContext context)
    {
        Class<? extends AssertionProcessor> apClass = null;
        try {
            apClass = assertionProcessorClasses.get(assertionProcessorType);
            if (apClass == null)
                apClass = newAssertionProcessorClass(assertionProcessorType);
            AssertionProcessor ap = apClass.newInstance();
            ap.init(context);
            return ap;
        } catch (IllegalAccessException iaex) {
            throw new IllegalArgumentException(
                    "Cannot create AssertionProcessor type=" + apClass, iaex);
        } catch (InstantiationException iex) {
            throw new IllegalArgumentException(
                    "Cannot create AssertionProcessor type=" + apClass, iex);
        }
    }

    /**
     * Returns the URL for the RepositoryService.
     * 
     * @return the URL for the RepositoryService.
     */
    public String getRepositoryServiceLocation() { return rsLocation; }

    /**
     * Sets the RepositoryService URL.
     * 
     * @param rsLocation the RepositoryService URL.
     */
    public void setRepositoryServiceLocation(String rsLocation)
    {
    	s_logger.error("setRepositoryServiceLocation: setting rsLocation="+rsLocation);
        this.rsLocation = rsLocation;
    }

    /**
     * Returns an AssertionReport for the input Assertions.
     *
     * @param userid the userid
     * @param password the password
     * @param assertions the Assertions to apply to the content.
     * @param content the AssertionContent content to validate.
     * @return an AssertionReport for the input Assertions.
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public AssertionReport applyAssertions(
            String userid, String password,
            List<Assertion> assertions,
            List<AssertionContent> content)
        throws AssertionIllegalArgumentException, IOException
    {
        return new AssertionReport(content,
                applyAssertables(userid, password, assertions, content));
    }

    /**
     * Returns an AssertionReport for the input AssertionGroups.
     *
     * @param userid the userid
     * @param password the password
     * @param groups the AssertionGroups to apply to the content.
     * @param content the AssertionContent content to validate.
     * @return an AssertionReport for the input AssertionGroups.
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public AssertionReport applyAssertionGroups(
            String userid, String password,
            List<AssertionGroup> groups,
            List<AssertionContent> content)
        throws AssertionIllegalArgumentException, IOException
    {
        return new AssertionReport(content,
                applyAssertables(userid, password, groups, content));
    }

    /**
     * Returns an ordered collection of AssertableResults for the input
     * Assertables. An Assertable that appears more than once is only applied
     * the first time. However the result of each Assertable will affect the
     * results of all the groups and groups of groups that contain it anywhere
     * in the input collection.
     * <p>
     * The content names are adjusted when the names clash.
     * For instance, if there are several content objects named "WSDL"
     * the names are adjusted so the first one is "WSDL", followed
     * by "WSDL-2", "WSDL-3", etc.
     *
     * @param userid the userid
     * @param password the password
     * @param assertables the Assertables to apply to the content.
     * @param content the AssertionContent content to validate.
     * @return an ordered collection of AssertableResults.
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Collection<AssertableResult> applyAssertables(
            String userid, String password,
            List<? extends Assertable> assertables,
            List<AssertionContent> content)
        throws AssertionIllegalArgumentException, IOException
    {
        // Ordered collection of results
        Collection<AssertableResult> assertableResults =
            new LinkedHashSet<AssertableResult>(assertables.size());

        // Cache of AssertionProcessorts used in the operation
        Map<String, AssertionProcessor> assertionProcessors =
            new HashMap<String, AssertionProcessor>();

        // Cache of assertions that have been applied already
        Map<Assertable, AssertableResult> completedAssertables =
            new HashMap<Assertable, AssertableResult>();

        AssertionProcessorContext context = (rsLocation != null)
            ? new AssertionProcessorContext(userid, password, rsLocation)
            : new AssertionProcessorContext();

        try {
            adjustDuplicateContentNames(content);

            for (Assertable assertable : assertables) {
                assertableResults.add(validateAssertable(
                        context, assertable, content,
                        assertionProcessors, completedAssertables));
            }
            return assertableResults;
        } finally {
            for (AssertionProcessor ap : assertionProcessors.values())
                ap.close(); // release resources
        }
    }

    // If more than one content has the same name the duplicate names
    // are modified. The modified names add a -N suffix (-2, -3, etc.).
    /**
     * Adjust duplicate content names.
     *
     * @param content the content
     */
    private void adjustDuplicateContentNames(List<AssertionContent> content)
    {
        for (int i = 0; i < content.size() - 1; i++) {
            String name = content.get(i).getName();
            int n = 2; // name, name-2, name-3, etc.
            Iterator<AssertionContent> it = content.listIterator(i+1);
            while (it.hasNext()) {
                AssertionContent ac = it.next();
                if (ac.getName().equals(name)) {
                    ac.setName(name+'-'+n);
                    n++;
                } 
            }          
        }
    }

    /**
     * Validate assertable.
     *
     * @param context the context
     * @param assertable the assertable
     * @param content the content
     * @param assertionProcessors the assertion processors
     * @param completedAssertables the completed assertables
     * @return the assertable result
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private AssertableResult validateAssertable(
            AssertionProcessorContext context,
            Assertable assertable,
            List<AssertionContent> content,
            Map<String, AssertionProcessor> assertionProcessors,
            Map<Assertable, AssertableResult> completedAssertables)
        throws AssertionIllegalArgumentException, IOException
    {
        AssertableResult assertableResult = completedAssertables.get(assertable);
        if (assertableResult == null) { // else already processed
            if (assertable instanceof AssetReferent)
                ((AssetReferent)assertable).dereference(context);
            if (assertable instanceof Assertion) {
                Assertion assertion = (Assertion)assertable;
                String apType = assertion.getAssertionProcessorType();
                AssertionProcessor ap = assertionProcessors.get(apType);
                if (ap == null) {
                    ap = newAssertionProcessor(apType, context);
                    assertionProcessors.put(apType, ap);
                }
                assertableResult = ap.applyAssertion(assertion, content);
            } else if (assertable instanceof AssertionGroup) {
                AssertionGroup group = (AssertionGroup) assertable;
                assertableResult = new BasicAssertableResult(group);
                for (Assertable member : group.getGroupMembers()) {
                    // recursive descent into nested groups
                    AssertableResult groupMemberResult = validateAssertable(
                            context, member, content,
                            assertionProcessors, completedAssertables);
                    assertableResult.addAssertableResult(groupMemberResult);
                }
            }
            completedAssertables.put(assertable, assertableResult);
        }
        return assertableResult;
    }
}
