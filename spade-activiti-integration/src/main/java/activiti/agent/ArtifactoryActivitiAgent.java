package activiti.agent;
import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import activiti.agent.bean.JenkinsInfo;
import activiti.agent.db.ArtifactoryAgentDAO;
import activiti.agent.db.SonarQubeAgentDAO;

public class ArtifactoryActivitiAgent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delexe) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("called in service... Executing "+this.getClass().getName()+" Agent...");
		/*Map<String, Object> variables = arg0.getVariables();
		Iterator<String> it = variables.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = variables.get(key);
			System.out.println("Key: " + key + " Value: " + value);
		}*/


		ArtifactoryActivitiAgent instance = new ArtifactoryActivitiAgent();

		if (delexe.getProcessDefinitionId().startsWith("UserOnboardingProcess")) {
			System.out.println("There is no useronboarding in Artifcatory");

		} else if (delexe.getProcessDefinitionId().startsWith("ProjectOnboardingProcess")) {
			instance.updateArtifactoryUrl(delexe);
		}
	}

	/**
	 * @param delexe
	 */
	private void updateArtifactoryUrl(DelegateExecution delexe) {
		ArtifactoryAgentDAO artifactoryAgentDAO = new ArtifactoryAgentDAO();
		String custId=delexe.getVariable("custid", String.class);
		JenkinsInfo jenkinsInfo = artifactoryAgentDAO.getArtifactoryDetails(custId);
		if(jenkinsInfo!=null){
			delexe.setVariable("ArtifactoryURL", jenkinsInfo.getUrl());
		}
		System.out.println("After setting ArtifactoryURL in the workflow "+delexe.getVariable("ArtifactoryURL", String.class));
	}

}
