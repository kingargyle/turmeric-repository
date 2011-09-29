/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.datatypes;

import java.util.Iterator;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import org.ebayopensource.turmeric.tools.codegen.exception.PreProcessFailedException;
import org.ebayopensource.turmeric.tools.codegen.external.wsdl.parser.WSDLParserFactory;

public class WSDLUtilForRep {

   public static QName getFirstServiceQName(String wsdlLoc) throws WSDLException, Exception {
      Definition wsdlDef = getWSDLDefinition(wsdlLoc);
      return getFirstServiceQName(wsdlDef);
   }

   public static QName getFirstServiceQName(Definition wsdlDef) throws Exception {
      Map servicesMap = wsdlDef.getServices();
      if (servicesMap == null || servicesMap.isEmpty()) {
         throw new Exception("No services defined in WSDL.");
      }
      QName serviceQName = null;

      Iterator keySetItr = servicesMap.keySet().iterator();
      while (keySetItr.hasNext()) {
         serviceQName = (QName) keySetItr.next();
         break;
      }
      return serviceQName;
   }

   public static Definition getWSDLDefinition(String wsdlLoc) throws WSDLException, PreProcessFailedException {
      WSDLParserFactory.getInstance();
      WSDLFactory factory = WSDLFactory.newInstance();
      WSDLReader wsdlReader = factory.newWSDLReader();
      wsdlReader.setFeature("javax.wsdl.importDocuments", true);

      Definition wsdlDef = wsdlReader.readWSDL(wsdlLoc);

      return wsdlDef;
   }
}
