/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.xquery;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.saxon.AugmentedSource;
import net.sf.saxon.Configuration;
import net.sf.saxon.event.Builder;
import net.sf.saxon.query.ModuleURIResolver;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.xqj.SaxonXQDataSource;

import org.xml.sax.InputSource;
import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.repositorymanager.assertions.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertableResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.ClassResourceContent;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQConstants;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItem;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import javax.xml.xquery.XQStaticContext;

/**
 * The AssertionProcessor for XQuery assertions.
 * 
 * @author pcopeland
 */
public class AssertionProcessor
    implements org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor
{
    
    /** The Constant logger. */
    static private final Logger logger = Logger.getLogger(AssertionProcessor.class); 
    
    /** The Constant assertionProcessorName. */
    static private final String assertionProcessorName = "XQueryAssertionProcessor";
    
    /** The Constant CORELIB_SCRIPT. */
    static private final AssertionContent CORELIB_SCRIPT;
    //static private final String baseResolverURI = "http://ebay.com/rm/assert/";
    /** The Constant baseResolverURI. */
    static private final String baseResolverURI = "/assertion/";
    static {
        CORELIB_SCRIPT = new ClassResourceContent(
                baseResolverURI+"assert_corelib.xq",
                "assert_corelib.xq",
                org.ebayopensource.turmeric.repositorymanager.assertions.xquery.AssertionProcessor.class);
    }

    /** The context. */
    private AssertionProcessorContext context;
    
    /** The xq connection. */
    private XQConnection xqConnection;
    
    /** The xq document type. */
    private XQItemType xqDocumentType;
    
    /** The doc item cache. */
    private DocItemCache docItemCache = new DocItemCache();
    
    /** The module resolver. */
    private ModuleResolver moduleResolver = new ModuleResolver();

    /**
     * Instantiates a new assertion processor.
     */
    public AssertionProcessor()
    {
        try {
            Configuration configuration = new Configuration();
            configuration.setModuleURIResolver(moduleResolver);
            // TODO - delete - configuration.setLineNumbering(true);
            moduleResolver.addModuleScript(null, CORELIB_SCRIPT);
            XQDataSource xqDataSource = new SaxonXQDataSource(configuration);

            xqConnection = xqDataSource.getConnection();
            xqDocumentType = xqConnection.createDocumentType();
            XQStaticContext staticContext = xqConnection.getStaticContext();
            staticContext.setBaseURI(baseResolverURI);
            staticContext.setBindingMode(XQConstants.BINDING_MODE_DEFERRED);
            staticContext.setBoundarySpacePolicy(XQConstants.BOUNDARY_SPACE_STRIP);
            xqConnection.setStaticContext(staticContext);
        } catch (XQException xqex) {
            throw new RuntimeException(xqex);
        }
    }

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#getName()
     */
    @Override
	public String getName() { return assertionProcessorName; }

    /**
     * Sets the context for this AssertionProcessor.
     *
     * @param apContext the ap context
     */
    @Override
	public void init(AssertionProcessorContext apContext)
    {
        this.context = apContext;
    }

    /**
     * Returns an AssertionContentSource for a given AssertionContent.
     *
     * @param assertionContent the assertion content
     * @return an AssertionContentSource for a given AssertionContent.
     */
    @Override
	public AssertionContentSource getAssertionContentSource(
            AssertionContent assertionContent)
    {
        return context.getAssertionContentSource(assertionContent);
    }

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#close()
     */
    @Override
	public void close()
    {
        try {
            if (xqConnection != null) xqConnection.close();
        } catch (XQException xqex) {
            throw new RuntimeException(xqex);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() { return getName(); }

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#applyAssertion(org.ebayopensource.turmeric.repositorymanager.assertions.Assertion, java.util.List)
     */
    @Override
	public AssertableResult applyAssertion(
            Assertion assertion,
            List<AssertionContent> content)
        throws IOException
    {
    	logger.debug("Entered applyAssertion method of Xquery AssertionProcessor");
        XQPreparedExpression xqExpr = null;
        XQResultSequence xqResult = null;

        try {
            // Add modules to resolver
            Collection<AssertionModule> mods = assertion.getAssertionModules();
            if (mods != null) {
                for (AssertionModule module: mods) {
                    for (AssertionContent scriptContent: module.getModuleScripts())
                        moduleResolver.addModuleScript(module, scriptContent);
                }
            }
            logger.debug("Resolved assertion modules");
            // Prepare query expression
            String script = getAssertionContentSource(
                    assertion.getAssertionScript()).getStringContent();
            logger.debug(assertion+"\n------ script -------\n"+script);
            xqExpr = xqConnection.prepareExpression(script);

            // Set external variables with content values
            docItemCache.setContentVariables(xqExpr, content);
            logger.debug("After setting external variables");
            
            // Check for unbound variables - any left over are errors
            int unboundCnt = xqExpr.getAllUnboundExternalVariables().length;
            if (unboundCnt > 0) {
                QName[] unboundVars = xqExpr.getAllUnboundExternalVariables();
                StringBuffer unboundMsg = new StringBuffer("Unbound variables=");
                String comma = "";
                for (QName unVar: unboundVars) {
                    unboundMsg.append(comma).append(unVar.getLocalPart());
                    comma = ",";
                }
                logger.error("There are " + unboundCnt + " unbound external variables, they are " + unboundMsg.toString());
                throw new IllegalStateException(unboundMsg.toString()+" "+assertion);
            }

            // Execute query and extract results
            logger.debug("before xqExpr.executeQuery()");
            xqResult = xqExpr.executeQuery();
            logger.debug("after xqExpr.executeQuery()");
            List<AssertionResultItem> resultItems = null;
            while (xqResult.next()) {
                XQItem resultItem = xqResult.getItem();
                if (resultItems == null)
                    resultItems = new ArrayList<AssertionResultItem>();
                if (resultItem.getItemType().getItemKind() == XQItemType.XQITEMKIND_ELEMENT) 
                {
                    Element resultEle = (Element)resultItem.getNode();
                    if(resultEle.getNodeName().equalsIgnoreCase("assertion"))
                    {
                	NodeList nodeList = resultEle.getChildNodes();
                	/*
                	 * The assertion script must be written a way that there is one assertionMessage 
                	 * for every error discovered by the script.
                	 * e.g:
                	 * 
                	 *   <assertion name="theNameOfTheAssertion">
                	 *   	<asertionMessage line-number="lineNo" artifact-name="">
                	 *   		The error message
                	 *   	</asertionMessage>
                	 *   	.....
                	 *   </assertion>
                	 */
                	for(int i =0;i<nodeList.getLength();i++)
                        {
                			
                    		Node childNode = nodeList.item(i);
                    		if(childNode.getNodeName()!= null && childNode.getNodeName().equalsIgnoreCase("assertionMessage"))
                    		{
	                    		NamedNodeMap namedNodeMap = childNode.getAttributes();
	                    		String lineNumber = null;
	                    		String artifact = null;
	                    		if(namedNodeMap!= null && namedNodeMap.getNamedItem("line-number")!= null)
	                    		{
	                    		    lineNumber = namedNodeMap.getNamedItem("line-number").getNodeValue();
	                    		}
	                    		if(namedNodeMap!= null &&  namedNodeMap.getNamedItem("artifact-name")!= null)
	                    		{
	                    		    artifact = namedNodeMap.getNamedItem("artifact-name").getNodeValue();
	                    		}
	                                if(childNode.getTextContent()!= null && !childNode.getTextContent().equals(""))
	                                resultItems.add(new AssertionResultItem(
	                                        artifact, childNode.getTextContent(), lineNumber));
                    		}
                        }
                    }
                    
                    
                    
                    
                } else
                {
                    resultItems.add(new AssertionResultItem(null, resultItem.getItemAsString(null), null));                
                }
                logger.debug("After processing the assertion results");
            }
            return new BasicAssertableResult(assertion, resultItems);
        } catch (XQException xqex) {
        	logger.error("Exception occured while trying to execute the assertion '" + assertion.getName() + "' for the wsdl inside the asset '" + content.get(0).getName() + "' The error message is: " + xqex.getMessage());
        	xqex.printStackTrace();
            throw new RuntimeException(xqex);
        } finally {
        	logger.debug("Inside finally block");
            try { if (xqExpr != null) xqExpr.close(); } catch(Exception ex) {throw new RuntimeException(ex);}
            try { if (xqResult != null) xqResult.close(); } catch(Exception ex) {throw new RuntimeException(ex);}
            logger.debug("completed filally block");
        }
    }

    // Caches parsed content documents
    /**
     * The Class DocItemCache.
     */
    private class DocItemCache
    {
        
        /** The doc items. */
        private Map<QName, XQItem> docItems = new HashMap<QName, XQItem>();

        /**
         * Sets the content variables.
         *
         * @param xqExpr the xq expr
         * @param content the content
         * @throws IOException Signals that an I/O exception has occurred.
         * @throws XQException the xQ exception
         */
        public void setContentVariables(
                XQPreparedExpression xqExpr,
                List<AssertionContent> content)
            throws IOException, XQException
        {
            QName[] externalVars = xqExpr.getAllExternalVariables();
            boolean firstMatch = true;
            QName contentQName = null; // used when only one content
            Map<String, AssertionContent> contentMap = null; // multiple contents
            AssertionContent assertionContent = null;
            for (QName exVar: externalVars)
            {
                XQItem docItem = docItems.get(exVar);
                if (docItem == null) {
                    if (firstMatch) {
                        firstMatch = false;
                        int cnt = content.size();
                        // skip Map construction if only one content
                        if (cnt == 1) {
                            assertionContent = content.get(0);
                            contentQName = new QName(assertionContent.getName());
                        } else {
                            contentMap =
                                new HashMap<String, AssertionContent>(cnt);
                            for (AssertionContent ac: content)
                                contentMap.put(ac.getName(), ac);
                        }
                    }
                    if (contentQName != null) {
                        if (exVar.equals(contentQName))
                            docItem = makeDocItem(exVar, assertionContent);
                    } else {
                        String exVarKey = exVar.getLocalPart();
                        if (contentMap.containsKey(exVarKey))
                            docItem = makeDocItem(exVar, contentMap.get(exVarKey));
                    }
                }
                if (docItem != null)
                    xqExpr.bindItem(exVar, docItem);
            }
        }

        /**
         * Make doc item.
         *
         * @param docQName the doc q name
         * @param assertionContent the assertion content
         * @return the xQ item
         * @throws IOException Signals that an I/O exception has occurred.
         * @throws XQException the xQ exception
         */
        private XQItem makeDocItem(
                QName docQName,
                AssertionContent assertionContent)
            throws IOException, XQException
        {
            AssertionContentSource assertionContentSource =
                getAssertionContentSource(assertionContent);
            Reader reader = assertionContentSource.getContentReader();
            InputSource inputSource = (reader != null)
                ? new InputSource(assertionContentSource.getContentReader())
                : new InputSource(assertionContentSource.getContentStream());
            // For Saxon SAXSource recommended over DOMSource.
            // Saxon claims TinyTree is faster and smaller than a Dom object.
            // TODO - delete - SAXSource saxSource = new SAXSource(inputSource);
            AugmentedSource source = 
                AugmentedSource.makeAugmentedSource(new SAXSource(inputSource));
            source.setTreeModel(Builder.TINY_TREE);
            source.setLineNumbering(true);
            source.setPleaseCloseAfterUse(true);
            if (logger.isDebugEnabled())
                logger.debug("AssertionContent doc="+docQName
                    +" for "+assertionContent.getSourceName()
                    +", Saxon TinyTree="+(source.getTreeModel()==Builder.TINY_TREE)
                    +", line numbering mode="+source.isLineNumbering());
            XQItem docItem = xqConnection.createItemFromDocument(
                    source, xqDocumentType);
            docItems.put(docQName, docItem);
            return docItem;
        }
    }

    /**
     * The Class ModuleResolver.
     */
    public class ModuleResolver
        implements ModuleURIResolver
    {
        
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1;

        /** The module scripts. */
        public Map<String, AssertionContent> moduleScripts =
            Collections.synchronizedMap(new HashMap<String, AssertionContent>());

        /**
         * Adds a module script to this ModuleResolver with a URI
         * of the form module-name/module-version/module-script-name.
         * Space in module-name are converted to dash '-' and periods
         * in module-version are converted to dash '-'.
         *
         * @param module the AssertionModule that contains the module script.
         * @param scriptContent the script content
         */
        public void addModuleScript(
                AssertionModule module,
                AssertionContent scriptContent)
        {
            // URI = [base]/module_name/version/script_name
            String moduleURI = null;//(module != null)
                /*? (new StringBuffer(baseResolverURI))
                    .append(module.getName().replace(' ', '-'))
                    .append('/').append(module.getVersion().replace('.', '-'))
                    .append('/').append(scriptContent.getName())
                    .toString()
                : scriptContent.getName(); // used for core script
*/             if(module!= null)
             {
            	 StringBuffer moduleURIStringBuffer = new StringBuffer(baseResolverURI).append(module.getName().replace(' ', '-')).append('/').append(module.getVersion().replace('.', '-'));
            	 if(scriptContent.getClass().equals(AssetContent.class))
            	 {
            		 moduleURIStringBuffer.append('/').append(((AssetContent) scriptContent).getArtifactName().replace(' ', '-'));
            	 }
            	 else
            	 {
            		 moduleURIStringBuffer.append('/').append(scriptContent.getName());
            	 }
            	 moduleURI = moduleURIStringBuffer.toString();
             }
			else
			{
				moduleURI = scriptContent.getName();
			}
                    System.out.println("module : " + module);
                    System.out.println("moduleURI : "+ moduleURI);
                    System.out.println("scriptContent : "+ scriptContent);
            logger.debug(
                    "Adding Module="+module
                    +",URI="+moduleURI
                    +","+scriptContent);
            moduleScripts.put(moduleURI, scriptContent);
        }

        /**
         * Implements ModuleURIResolver.
         *
         * @param href the href
         * @param base the base
         * @param locations the locations
         * @return the stream source[]
         * @throws XPathException the x path exception
         */
        @Override
		public StreamSource[] resolve(
                String href,
                String base,
                String[] locations)
            throws XPathException
        {
            try {
                StreamSource[] sources = null;
                for (String location: locations) {
                    AssertionContent script = moduleScripts.get(location);
                    if (script != null) {
                        AssertionContentSource acs =
                            getAssertionContentSource(script);
                        logger.debug("Resolved Module: "+script+" at "+location);
                        Reader reader = acs.getContentReader();
                        if (reader == null) {
                            // reader = new java.io.InputStreamReader(acs.getContentStream());
                            // For a binary, URL, or Asset type module we
                            // replace it once in the cache with the string
                            // equivalent since the same module may be
                            // used by many assertions in the same context.
                            StringContent sc = new StringContent(
                                    script.getName(),
                                    script.getSourceName(),
                                    acs.getStringContent());
                            context.replaceAssertionContent(sc);
                            acs = getAssertionContentSource(sc);
                            reader = acs.getContentReader();
                        }
                        StreamSource source = new StreamSource(reader, base);
                        sources = new StreamSource[] { source };
                        break;
                    }
                }
                return sources; // if null saxon looks in standard locations
            } catch (IOException ioex) {
                throw new XPathException(
                        "Failure resolving artifact named='"+href+"'", ioex);
            }
        }
    }
}
