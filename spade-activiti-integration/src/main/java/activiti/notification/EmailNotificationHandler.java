package activiti.notification;

import java.util.HashSet;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class EmailNotificationHandler implements ExecutionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 602563154724614647L;

	public static void main(String[] args) throws Exception {
		EmailNotificationHandler test = new EmailNotificationHandler();
		test.sendMail("test", null, "start");
	}

	public void sendMail(String activityName, String toList, String eventName) throws AddressException {
		System.out.println("called in service... Executing " + this.getClass().getName() + " Agent...");
		String from = "as-ca-user@cisco.com";
		String timeout = "1*60*1000";
		String connTimeout = "1*30*1000";
		String host = "mail.cisco.com";
		String port = "25";
		HashSet<InternetAddress> toUniqueList = new HashSet<InternetAddress>();
		toList="sarbr@cisco.com";
		if (toList != null) {
			String[] users = toList.split(",");
			for (int i = 0; i < users.length; i++) {
				toUniqueList.add(new InternetAddress(users[i].trim()));
			}
		}

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.smtp.timeout", timeout);
		properties.setProperty("mail.smtp.connectiontimeout", connTimeout);
		Session session = Session.getDefaultInstance(properties);
		InternetAddress[] toAddList = new InternetAddress[toUniqueList.size()];
		try {
			toAddList = toUniqueList.toArray(toAddList);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			message.addRecipients(Message.RecipientType.TO, toAddList);
			message.setSubject("Onboarding Status update");
			// message.setSubject();
			message.setText("Hi,\n\n" + activityName + " " + eventName
					+ " for project onboarding! \n\nRegards,\nOnBoarding Team ");
			// message.setText(msg);

			// Send message
			Transport.send(message);
			System.out.println("message sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	@Override
	public void notify(DelegateExecution delExe) throws Exception {
		// TODO Auto-generated method stub
		if ("ProjectOnboardingProcess".equalsIgnoreCase(delExe.getProcessBusinessKey())) {
			sendMail(delExe.getCurrentActivityName(), delExe.getVariable("To").toString(), delExe.getEventName());
		}
	}

}
