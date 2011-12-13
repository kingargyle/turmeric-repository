package org.ebayopensource.turmeric.repository.wso2;

import javax.xml.namespace.QName;

import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.governance.api.wsdls.WsdlManager;
import org.wso2.carbon.governance.api.wsdls.dataobjects.Wsdl;
import org.wso2.carbon.registry.core.Registry;
import static org.junit.Assert.*;

public class WSO2GovRegTest extends Wso2Base {

   Registry registry = null;

   @Before
   public void init() {
      registry = RSProviderUtil.getRegistry();
   }

   @Test
   public void testCreateUpdateService() throws Exception {

      ServiceManager sm = new ServiceManager(RSProviderUtil.getRegistry());
      Service service = sm.newService(new QName("http://www.example.org", "Example"));

      service.setAttribute(AssetConstants.TURMERIC_NAME, "Example Service");
      sm.addService(service);
      String assetId = service.getId();

      GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactById(registry, service.getId());
      assertNotNull(artifact);
      assertEquals("Example Service", artifact.getAttribute(AssetConstants.TURMERIC_NAME));

      service = sm.getService(assetId);
      assertNotNull("Missing service.", service);
      service.setAttribute(AssetConstants.TURMERIC_NAME, "New Name");
      sm.addService(service);

      service = sm.getService(assetId);
      assertEquals("Service Name attribute did not update.", "New Name",
               service.getAttribute(AssetConstants.TURMERIC_NAME));
   }

   @Test
   public void testCreateUpdateWSDL() throws Exception {

      WsdlManager wm = new WsdlManager(registry);
      Wsdl wsdl = wm.newWsdl(
               loadFile("src/main/resources/META-INF/soa/services/wsdl/CreateServiceTest/CreateServiceTest.wsdl"),
               "Example.wsdl");
      wsdl.setAttribute(AssetConstants.TURMERIC_NAME, "Wsdl Name");
      assertNotNull(wsdl);
      wm.addWsdl(wsdl);
      String id = wsdl.getId();

      GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactById(registry, id);
      assertNotNull("Unable to find wsdl", artifact);

      wsdl = wm.getWsdl(id);
      wsdl.setAttribute(AssetConstants.TURMERIC_NAME, "Wsdl New Name");
      wm.addWsdl(wsdl);

      artifact = GovernanceUtils.retrieveGovernanceArtifactById(registry, id);
      assertNotNull("Unable to find wsdl after update", artifact);
      assertEquals("oops..did not update wsdl", "Wsdl New Name", artifact.getAttribute(AssetConstants.TURMERIC_NAME));

   }

}
