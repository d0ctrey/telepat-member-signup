package com.github.doctrey.telegram.client.impl;

import com.github.doctrey.telegram.client.PhoneNumberService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.net.URI;

/**
 * Created by s_tayari on 4/4/2018.
 */
public class AppRyanPhoneNumberService implements PhoneNumberService {

    private Client client;
    private static final String TOKEN = "0aab98d3382729296cdf29cc5f37284c";
    private static final String USERNAME = "apprayan358@apprayan.com";


    private void initClient() {
        ClientConfig cc = new ClientConfig();
        cc.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        cc.property(ClientProperties.READ_TIMEOUT, 15000);
        cc.property(ClientProperties.MOXY_JSON_FEATURE_DISABLE, true);
//        cc.property(ClientProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
//        cc.property(ClientProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);


        client = ClientBuilder.newClient(cc);
        client.register(JacksonJsonProvider.class);
    }

    @Override
    public String getMobileNumber() {
        initClient();
        String action = "getmobile";
        URI uri = UriBuilder.fromUri("http://www.smsrayan.com/do.php").build();
        MultivaluedMap<String, String> body = new MultivaluedHashMap<>();
        body.add("action", action);
        body.add("username", USERNAME);
        body.add("token", TOKEN);
        body.add("pid", "10");
        Response response = null;
        try {
            response = client.target(uri).request().post(Entity.form(body));
            String number = response.readEntity(new GenericType<String>() {
            });
            return number;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null)
                response.close();
            closeClient();
        }
    }

    @Override
    public void addToBlackList(String mobileNumber) throws Exception {
        initClient();
        String action = "addblack";
        URI uri = UriBuilder.fromUri("http://www.smsrayan.com/do.php").build();
        MultivaluedMap<String, String> body = new MultivaluedHashMap<>();
        body.add("action", action);
        body.add("username", USERNAME);
        body.add("token", TOKEN);
        body.add("pid", "10");
        body.add("mobile", mobileNumber);
        Response response = null;
        try {
            response = client.target(uri).request().post(Entity.form(body));
            if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL))
                throw new Exception("Failed to add to blacklist.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null)
                response.close();
            closeClient();
        }
    }

    @Override
    public String getCode(String mobileNumber) {
        return null;
    }

    private void closeClient() {
        client.close();
    }
}
