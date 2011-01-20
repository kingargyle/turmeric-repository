package org.ebayopensource.turmeric.repository.runner;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Resource;


public class RepositoryServiceRunnerSetupTest {

    @BeforeClass
    /**
     * Utility method used to create a necessary asset in the wso2 registry, before a given test
     * runs
     */
    public static void createLifecycleInfoInWso2() {
        try {
            RemoteRegistry _registry = new RemoteRegistry(
                            new URL(
                                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
            String assetName = "TurmericLifeCycle";
            String assetKey = "/_system/config/repository/components/org.wso2.carbon.governance/lifecycles/"
                            + assetName;

            if (!_registry.resourceExists(assetKey)) {
                Resource asset = _registry.newResource();
                asset.setMediaType("application/octet-stream");
                // need to read the lifecyle content from a file
                InputStream lifecycleIstrm = RepositoryServiceRunnerSetupTest.class.getClassLoader().getResourceAsStream(
                                "TurmericLifecycle.xml");
                BufferedReader in = new BufferedReader(new InputStreamReader(lifecycleIstrm));
                String str;
                StringBuilder strContent = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    strContent.append(str);
                }
                in.close();
                if (strContent.length() > 0) {
                    InputStream contentStream = new ByteArrayInputStream(strContent.toString()
                                    .getBytes("UTF-8"));
                    asset.setContentStream(contentStream);
                }
                _registry.put(assetKey, asset);// i put the lifecycle in the wso2 registry instance

            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testRequiredAssetsLoadedInWso2() {
        try {
            RemoteRegistry _registry = new RemoteRegistry(
                            new URL(
                                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
            String assetName = "TurmericLifeCycle";
            String assetKey = "/_system/config/repository/components/org.wso2.carbon.governance/lifecycles/"
                            + assetName;

            if (!_registry.resourceExists(assetKey)) {
                fail("Turmeric lifecycle not loaded in the wso2 instance");
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Error checking turmeric-wo2 lifecycle");
        }
    }
}
