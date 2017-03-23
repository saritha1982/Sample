package org.cisco.spadeportal.helpers.commons;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

public class JerseyClientProvider {

	public static Client getClient() {
		return ClientBuilder.newClient();
	}

	public static Client getClient(String user, String pwd) {
		ClientConfig config = new ClientConfig();

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, pwd);

		Client client = ClientBuilder.newClient(config);
		client.register(feature).register(JacksonFeature.class);
		return client;
	}
}
