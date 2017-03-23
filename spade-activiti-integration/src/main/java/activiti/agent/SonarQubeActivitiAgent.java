package activiti.agent;
import java.util.Iterator;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import activiti.agent.bean.JenkinsInfo;
import activiti.agent.db.JenkinsAgentDAO;
import activiti.agent.db.SonarQubeAgentDAO;

public class SonarQubeActivitiAgent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delexe) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("called in service... Executing "+this.getClass().getName()+" Agent...");
		/*Map<String, Object> variables = delexe.getVariables();
		Iterator<String> it = variables.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = variables.get(key);
			System.out.println("Key: " + key + " Value: " + value);
		}*/
		SonarQubeActivitiAgent instance = new SonarQubeActivitiAgent();

		if (delexe.getProcessDefinitionId().startsWith("UserOnboardingProcess")) {
			System.out.println("There is no useronboarding in SonarQube");

		} else if (delexe.getProcessDefinitionId().startsWith("ProjectOnboardingProcess")) {
			instance.updateSonarQubeUrl(delexe);
		}
		
	}

	/**
	 * @param delexe
	 */
	private void updateSonarQubeUrl(DelegateExecution delexe) {
		SonarQubeAgentDAO sonarAgentDAO = new SonarQubeAgentDAO();
		String custId=delexe.getVariable("custid", String.class);
		JenkinsInfo jenkinsInfo =sonarAgentDAO.getSonarQubeDetails(custId);
		if(jenkinsInfo!=null)
		delexe.setVariable("SonarQubeURL", jenkinsInfo.getUrl());
		System.out.println("After setting SonarQubeURL in the workflow "+delexe.getVariable("SonarQubeURL", String.class));

	}

}
