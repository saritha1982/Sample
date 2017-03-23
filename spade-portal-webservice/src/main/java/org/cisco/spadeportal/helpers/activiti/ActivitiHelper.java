package org.cisco.spadeportal.helpers.activiti;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.cisco.spadeportal.bean.activiti.StartProcessRequest;
import org.cisco.spadeportal.bean.activiti.StartProcessResponse;
import org.cisco.spadeportal.bean.activiti.Variable;
import org.cisco.spadeportal.bean.request.ProjectCreateRequest;
import org.cisco.spadeportal.bean.request.User;
import org.cisco.spadeportal.constants.CommonConstants;
import org.cisco.spadeportal.db.PortalConfigDAO;
import org.cisco.spadeportal.db.ProjectDAO;
import org.cisco.spadeportal.exceptions.ErrorMessage;
import org.cisco.spadeportal.exceptions.SystemException;
import org.cisco.spadeportal.helpers.commons.JerseyClientProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivitiHelper {
	private static final Logger LOGGER = Logger.getLogger(ActivitiHelper.class.getName());
	private static ActivitiHelper instance = null;
	private static final Object LOCK = new Object();
	private static String activitiURL = null;
	private static String user = null;
	private static String pwd = null;

	private ActivitiHelper() throws SystemException {
		if (instance != null) {
			throw new SystemException(ErrorMessage.SYS_ERR_DUPLICATE_OBJECT);
		}
		PortalConfigDAO portalDao = new PortalConfigDAO();
		activitiURL = portalDao.getConfig(CommonConstants.ACTIVITI_URL);
		user = portalDao.getConfig(CommonConstants.ACTIVITI_USER);
		pwd = portalDao.getConfig(CommonConstants.ACTIVITI_PWD);
	}

	public static ActivitiHelper getInstance() throws SystemException {

		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new ActivitiHelper();
				}
			}
		}
		return instance;
	}

	public static String getActivitiURL() {
		return activitiURL;
	}

	public static void setActivitiURL(String activitiURL) {
		ActivitiHelper.activitiURL = activitiURL;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		ActivitiHelper.user = user;
	}

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		ActivitiHelper.pwd = pwd;
	}

	/*public static void main(String[] args) throws Exception {
		ActivitiHelper instance = new ActivitiHelper();
		instance.setActivitiURL("http://127.0.0.1:8090/activiti-rest/service");
		instance.setPwd("kermit");
		instance.setUser("kermit");
		ProjectCreateRequest req = new ProjectCreateRequest();
		req.setProjectName("Test1");
		req.setProjectId("Test1");
		instance.startProjectOnBoardProcess(req, null);
	}*/

	public void startProjectOnBoardProcess(ProjectCreateRequest createReq, String custid) {
		try {
			WebTarget target = JerseyClientProvider.getClient(user, pwd).target(activitiURL)
					.path("runtime/process-instances");
			StartProcessRequest req = new StartProcessRequest();
			req.setBusinessKey(createReq.getProjectName());
			req.setProcessDefinitionKey(CommonConstants.PROJECT_ONBOARDING_PROCESS_NAME);

			ObjectMapper mapper = new ObjectMapper();
			// variables
			List<Variable> vars = new ArrayList<Variable>();
			Variable custidVar = new Variable();
			custidVar.setName(CommonConstants.CUST_ID_KEY);
			custidVar.setValue(custid);

			Variable var1 = new Variable();
			var1.setName(CommonConstants.OWNER_KEY);
			var1.setValue(createReq.getProjectManager());

			Variable var2 = new Variable();
			var2.setName(CommonConstants.PROCESS_NAME_KEY);
			var2.setValue(createReq.getProjectName());

			Variable var3 = new Variable();
			var3.setName(CommonConstants.NOTE_KEY);
			var3.setValue(createReq.getNotes());

			Variable var4 = new Variable();
			var4.setName(CommonConstants.PARENT_PROJECT_KEY);
			var4.setValue(createReq.getParentProject());

			Variable var5 = new Variable();
			var5.setName(CommonConstants.PROJECT_ONBOARD_ID_KEY);
			var5.setValue(createReq.getOnBoardId());

			Variable var6 = new Variable();
			var6.setName(CommonConstants.EMAIL_TO_KEY);
			var6.setValue(createReq.getProjectManager());

			Variable var7 = new Variable();
			var7.setName(CommonConstants.APPROVAL_KEY);
			var7.setValue("no");

			Variable var8 = new Variable();
			var8.setName(CommonConstants.PROJECT_NAME);
			var8.setValue(createReq.getProjectName());

			Variable var9 = new Variable();
			var9.setName(CommonConstants.TECH_KEY);
			var9.setValue(createReq.getTechnologyName());

			Variable var10 = new Variable();
			var10.setName(CommonConstants.USER_NAME);
			var10.setValue(createReq.getTechnologyName());
			
			Variable var11 = new Variable();
			var11.setName(CommonConstants.CATEGORY_NAME);
			var11.setValue(createReq.getCategoryName());

			vars.add(custidVar);
			vars.add(var1);
			vars.add(var2);
			vars.add(var3);
			vars.add(var4);
			vars.add(var5);
			vars.add(var6);
			vars.add(var7);
			vars.add(var8);
			vars.add(var9);
			vars.add(var10);
			vars.add(var11);
			req.setVariables(vars);

			String strReq = mapper.writeValueAsString(req);
			System.out.println(strReq);
			StartProcessResponse response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(strReq, MediaType.APPLICATION_JSON_TYPE), StartProcessResponse.class);
			LOGGER.log(Level.INFO, "Onboarding process started for project " + createReq.getProjectName()
					+ ", with process instance URL " + response.getUrl());
			// update the DB with process instance details
			ProjectDAO prjDao = new ProjectDAO();
			prjDao.updateActivitiProcessInstanceId(response.getId(), createReq.getOnBoardId());
		} catch (Throwable exp) {
			exp.printStackTrace();
		}
	}

	public void startUserOnBoardProcess(String custid, String onBoardId) {
		try {
			WebTarget target = JerseyClientProvider.getClient(user, pwd).target(activitiURL)
					.path("runtime/process-instances");
			StartProcessRequest req = new StartProcessRequest();
			req.setBusinessKey(CommonConstants.USER_ONBOARDING_PROCESS_NAME + System.currentTimeMillis());
			req.setProcessDefinitionKey(CommonConstants.USER_ONBOARDING_PROCESS_NAME);
		
			List<Variable> vars = new ArrayList<Variable>();
			// variables
			Variable custidVar = new Variable();
			custidVar.setName("custid");
			custidVar.setValue(custid);
			
			Variable onBoardIdVar = new Variable();
			onBoardIdVar.setName(CommonConstants.PROJECT_ONBOARD_ID_KEY);
			onBoardIdVar.setValue(onBoardId);
			
			Variable addOnUser = new Variable();
			addOnUser.setName("addOnUser");
			addOnUser.setValue("false");
			
			vars.add(custidVar);
			vars.add(onBoardIdVar);
			
			vars.add(addOnUser);
			
			req.setVariables(vars);

			ObjectMapper mapper = new ObjectMapper();
			String strReq = mapper.writeValueAsString(req);

			System.out.println(mapper.writeValueAsString(req));
			StartProcessResponse response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(strReq, MediaType.APPLICATION_JSON_TYPE), StartProcessResponse.class);
			LOGGER.log(Level.INFO, "Onboarding process started for User " + response.getBusinessKey()
					+ ", with process instance URL " + response.getUrl());
			// update the DB with process instance details
		} catch (Throwable exp) {
			exp.printStackTrace();
		}
	}
	
	public void startAddUserProcess(String custid, User createReq, String onBoardId) {
		try {
			WebTarget target = JerseyClientProvider.getClient(user, pwd).target(activitiURL)
					.path("runtime/process-instances");
			StartProcessRequest req = new StartProcessRequest();
			req.setBusinessKey(CommonConstants.USER_ONBOARDING_PROCESS_NAME + System.currentTimeMillis());
			req.setProcessDefinitionKey(CommonConstants.USER_ONBOARDING_PROCESS_NAME);
		
			List<Variable> vars = new ArrayList<Variable>();
			// variables
			Variable custidVar = new Variable();
			custidVar.setName("custid");
			custidVar.setValue(custid);
			
			Variable onBoardIdVar = new Variable();
			onBoardIdVar.setName(CommonConstants.PROJECT_ONBOARD_ID_KEY);
			onBoardIdVar.setValue(onBoardId);
			
			Variable user = new Variable();
			user.setName("emailAddress");
			user.setValue(createReq.getEmailAddress());
			
			Variable addOnUser = new Variable();
			addOnUser.setName("addOnUser");
			addOnUser.setValue("true");
			
			Variable var10 = new Variable();
			var10.setName(CommonConstants.USER_NAME);
			var10.setValue(createReq.getUserName());
			
			vars.add(custidVar);
			vars.add(onBoardIdVar);
			vars.add(addOnUser);
			vars.add(user);
			vars.add(var10);
			req.setVariables(vars);
			

			ObjectMapper mapper = new ObjectMapper();
			String strReq = mapper.writeValueAsString(req);

			System.out.println(mapper.writeValueAsString(req));
			StartProcessResponse response = target.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(strReq, MediaType.APPLICATION_JSON_TYPE), StartProcessResponse.class);
			LOGGER.log(Level.INFO, "Onboarding process started for User " + response.getBusinessKey()
					+ ", with process instance URL " + response.getUrl());
			// update the DB with process instance details
		} catch (Throwable exp) {
			exp.printStackTrace();
		}
	}
}
