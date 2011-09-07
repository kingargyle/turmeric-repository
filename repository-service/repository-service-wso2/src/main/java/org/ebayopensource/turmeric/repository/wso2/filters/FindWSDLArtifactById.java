package org.ebayopensource.turmeric.repository.wso2.filters;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.wsdls.WsdlFilter;
import org.wso2.carbon.governance.api.wsdls.dataobjects.Wsdl;

public class FindWSDLArtifactById implements WsdlFilter {

	private ArtifactInfo artifactInfo = null;
	
	public FindWSDLArtifactById(ArtifactInfo artifactInfo) {
		this.artifactInfo = artifactInfo;
	}
	
	@Override
	public boolean matches(Wsdl wsdl) throws GovernanceException {
		
		if (artifactInfo.getArtifact().getArtifactIdentifier().equals(wsdl.getId())) {
			return true;
		}
		
		return false;
	}

}
