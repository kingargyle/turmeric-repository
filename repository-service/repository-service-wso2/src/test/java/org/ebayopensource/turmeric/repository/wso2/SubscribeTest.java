package org.ebayopensource.turmeric.repository.wso2;

import org.ebayopensource.turmeric.repository.v2.services.SubscribeRequest;
import org.junit.Test;
import static org.junit.Assert.*;


public class SubscribeTest extends Wso2Base {

	@Test
	public void testSubscribeResponseNotNull() throws Exception {
		RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
		SubscribeRequest srq = new SubscribeRequest();
		assertNotNull("Response returned null", provider.subscribe(srq));
	}
	
	@Test
	public void testInvalidSubscription() throws Exception {
		fail();
	}
	
	@Test
	public void testInsufficientPriviledges() throws Exception {
		fail();
	}
	
}
