package activiti.agent;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import activiti.agent.bean.CustomerInfo;
import activiti.agent.bean.GitHubInfo;
import activiti.agent.bean.JenkinsInfo;
import activiti.agent.db.JenkinsAgentDAO;
import activiti.agent.utils.UpdateXML;

public class JenkinsActivitiAgent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delexe) throws Exception {
		System.out.println("called in service... Executing " + this.getClass().getName() + " Agent...");
		JenkinsActivitiAgent jenkinsActivitiAgent = new JenkinsActivitiAgent();
		if (delexe.getProcessDefinitionId().startsWith("UserOnboardingProcess")) {
			System.out.println("There is no useronboarding in Jenkins");
		} else if (delexe.getProcessDefinitionId().startsWith("ProjectOnboardingProcess")) {
			jenkinsActivitiAgent.createJenkinsJob(delexe.getVariable("custid", String.class),
					delexe.getVariable("name", String.class),delexe);
		}
	}

	/**
	 * @param customerId
	 * @param projectName
	 * @return
	 */
	public String createJenkinsJob(String customerId, String projectName,DelegateExecution delexe) {
		String newJobURL = "";
		try {
			JenkinsAgentDAO jenkinsAgentDAO = new JenkinsAgentDAO();
			JenkinsInfo jenkinsInfo = jenkinsAgentDAO.getJenkinsDetails(customerId);
			CustomerInfo customerInfo=jenkinsAgentDAO.getCustomerDetails(customerId);
			if(customerInfo.getCustomerName().equalsIgnoreCase("AS Community")||customerInfo.getCustomerName().equalsIgnoreCase("AS Community Test")){
				jenkinsInfo.setUrl("https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/AS-Community/");
			}
			else if(customerInfo.getCustomerName().equalsIgnoreCase("AS Internal")|| customerInfo.getCustomerName().equalsIgnoreCase("AS Internal Test")){
				jenkinsInfo.setUrl("https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/AS-Internal/");
			}
			else{
				jenkinsInfo.setUrl("https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/Customers/");
			}
			System.out.println("JENKINS URL "+jenkinsInfo.getUrl());
			projectName=projectName.trim();
			projectName=projectName.replaceAll(" ", "-");
			String techName=delexe.getVariable("tech", String.class);
			System.out.println("techName "+techName);
			techName = techName.trim();
			techName = techName.replaceAll(" ", "-");
			GitHubInfo gitHubInfo = jenkinsAgentDAO.getGithubDetails(techName);
			List<String> jobList = listJobs(jenkinsInfo.getUrl(), jenkinsInfo);
			String templateJob = "Template_Job";
			boolean jobExists = checkJobExists(jobList, templateJob);
			if (jobExists) {
				System.out.println("Template Job Exists with name "+templateJob);
				String configXMLString = readJob(templateJob, jenkinsInfo);
				System.out.println("Current directory path " + Paths.get(".").toAbsolutePath().normalize().toString());
				String configFileName = Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
						+ "config.xml";
				jobExists = checkJobExists(jobList, projectName);
				if (!jobExists) {
					String fileName = convertStringtoXML(configXMLString, configFileName);
					newJobURL = createJobWithTemplateJobXML(fileName, jenkinsInfo, gitHubInfo, projectName);
					delexe.setVariable("JenkinsJobURL", newJobURL);
					System.out.println("After setting JenkinsURL in the workflow "+delexe.getVariable("JenkinsJobURL", String.class));

					// jenkinsAgentDAO.createJenkinsInstance(jenkinsInfo.getJenkinsId(),
					// jenkinsInfo.getUrl(),
					// projectName);
				} else {
					System.out.println("Job Exists by that name ..Give some other name");
				}
			} else {
				System.out.println(
						"Template Job doesnt exists so getting the java_config.xml from the current directory");
				String configXML = Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
						+ "config.xml";
				newJobURL = createJobWithTemplateJobXML(configXML, jenkinsInfo, gitHubInfo, projectName);
				delexe.setVariable("JenkinsJobURL", newJobURL);
				System.out.println("After setting JenkinsURL in the workflow "+delexe.getVariable("JenkinsJobURL", String.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newJobURL;
	}

	/**
	 * @param fileName
	 * @param jenkinsInfo
	 * @param githubInfo
	 * @param jobName
	 * @return
	 * @throws IOException
	 */
	protected String createJobWithTemplateJobXML(String fileName, JenkinsInfo jenkinsInfo, GitHubInfo githubInfo,
			String jobName) throws IOException {
		String configXMLString;
		System.out.println("File name \n"+new File(fileName));
		new UpdateXML().updateXML(new File(fileName), "RepositoryURL", githubInfo.getUrl());
		configXMLString = FileUtils.readFileToString(new File(fileName));
		System.out.println("After updating the xml file");
		return createJob(jobName, configXMLString, jenkinsInfo);
	}
	
	/**
	 * @param configXML
	 * @param fileName
	 * @return String
	 */
	private String convertStringtoXML(String configXML, String fileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(configXML)));
			// Write the parsed document to an xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return fileName;

	}

	/**
	 * @param jobList
	 * @param templateJob
	 * @return boolean
	 */
	private boolean checkJobExists(List<String> jobList, String templateJob) {
		return jobList.contains(templateJob);
	}

	/**
	 * @param url
	 * @param jenkinsInfo
	 * @return joblist
	 */
	private List<String> listJobs(String url, JenkinsInfo jenkinsInfo) {
		List<String> jobList = null;
		try {
			Client client = Client.create();
			client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(jenkinsInfo.getUserName(),
					jenkinsInfo.getPassword()));
			WebResource webResource = client.resource(url + "/api/xml");
			ClientResponse response = webResource.get(ClientResponse.class);
			String jsonResponse = response.getEntity(String.class);
			String fileName = convertStringtoXML(jsonResponse, "job_list.xml");
			jobList = getJobList(fileName);
			client.destroy();
		} catch (Exception e) {
			System.out.println("Joblists is not happening. Sorry Got some exception");
		}
		return jobList;
	}

	/**
	 * @param fileName
	 * @return
	 */
	private List<String> getJobList(String fileName) {
		List<String> jobList = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileName);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("job");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Job Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
					jobList.add(eElement.getElementsByTagName("name").item(0).getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}

	/**
	 * @param newJobName
	 * @param configXML
	 * @param jenkinsInfo
	 * @return String
	 */
	private String createJob(String newJobName, String configXML, JenkinsInfo jenkinsInfo) {
		System.out.println("Creating new job ");
		String jsonResponse = "";
		String jenkinsUrl = "";
		try {
			Client client = Client.create();
			client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(jenkinsInfo.getUserName(),
					jenkinsInfo.getPassword()));
			WebResource webResource = client.resource(jenkinsInfo.getUrl() + "createItem?name=" + newJobName);
			ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, configXML);
			System.out.println("Json Response " + response);
			if (response.getStatus() == 200) {
				jenkinsUrl = jenkinsInfo.getUrl() + "job/" + newJobName;
			}
			jsonResponse = response.getEntity(String.class);
			client.destroy();
			return jenkinsUrl;
		} catch (Exception e) {
			System.out.println("Job is not created. Sorry Got some exception");
			e.printStackTrace();
		}
		return jsonResponse;
	}

	/**
	 * @param jobName
	 * @param jenkinsInfo
	 * @return
	 */
	private String readJob(String jobName, JenkinsInfo jenkinsInfo) {
		Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(jenkinsInfo.getUserName(),
				jenkinsInfo.getPassword()));
		WebResource webResource = client.resource(jenkinsInfo.getUrl() + "/job/" + jobName + "/config.xml");
		ClientResponse response = webResource.get(ClientResponse.class);
		String jsonResponse = response.getEntity(String.class);
		client.destroy();
		return jsonResponse;
	}

	/*public static void main(String args[]) {
		JenkinsActivitiAgent jenkinsActivitiAgent = new JenkinsActivitiAgent();
		System.out
				.println(jenkinsActivitiAgent.createJenkinsJob("24334e12-d66b-11e6-95ed-507b9dc361f2", "REST_API_JOB"));
	}*/

}
