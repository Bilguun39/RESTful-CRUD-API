package backendAssignment02A_client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import backendAssignment02A.bilguun.sipree.jaxb.model.User;
import backendAssignment02A.bilguun.sipree.service.utils.Constants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MainTester {
	
	public static void main(String[] args){
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(clientConfig);
		client.addFilter(new HTTPBasicAuthFilter(Constants.SAMPLE_USER, Constants.SAMPLE_PASSWORD));
		
		WebResource webResource = client.resource(getBaseUri());
		ClientResponse clientResponse = null;
		
		// ********************** Testing BackendAssignment02A Rest client post user by User object ***************************** //
		
		System.out.println("#------------ Rest Client posting user by User object ---------------#");
		
		User user = new User("Bilguun", "Oyunchimeg", "bill.oyunchimeg@gmail.com", "7074100835");
		
		clientResponse = webResource.accept(MediaType.TEXT_HTML).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);
		
		System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		// *********************** Testing BackendAssignment02A Rest Client Post user with Form Param *************************** //
		
		System.out.println("#------------ Rest Client posting user with Form param --------------#");
		
		MultivaluedMap<String, String> formValue = new MultivaluedMapImpl();
		
		formValue.add("firstname", "Jonh");
		formValue.add("lastname", "Doe");
		formValue.add("email", "john.doe@example.com");
		formValue.add("phonenumber", "777-222-3333");
		
		clientResponse = webResource.accept(MediaType.TEXT_HTML).type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formValue);
		
		System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		// *********************** Testing BackendAssignment02A Rest client Post users by list ********************************** //
		
		List<User> users = new ArrayList<User>();
		
		user = new User("Katniss", "Everdeen", "katniss.everdeen@mockinjay.com", "815-222-3465");
		users.add(user);
		
		user = new User("Peeta", "Mellark", "peeta.mellark@mockinjay.com", "815-222-6534");
		users.add(user);
		
		System.out.println("#------------ Rest Client posting users with list -------------------#");
		
		clientResponse = webResource.path("/list").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, users);
		
		System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		
		// *********************** Testing BackendAssignment02A Rest client get users by JSON format **************************** //

		System.out.println("#------------ Rest Client getting users by JSON format --------------#");
		
		clientResponse = webResource.path("/json").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		GenericType<List<User>> genericType = new GenericType<List<User>>() {
        };
        
        List<User> data = new ArrayList<User>();
        data = (clientResponse.getEntity(genericType));
        
        System.out.println("#------------ Available user list -----------#");
        for(User userdetail : data){
        	System.out.println("firstname : " + userdetail.getFirstName());
        	System.out.println("lastname : " + userdetail.getLastName());
        	System.out.println("phonenumber : " + userdetail.getPhoneNumber());
        	System.out.println("email : " + userdetail.getEmail());
        	System.out.println();
        }
        System.out.println("#-----------------------#");
        
        // *********************** Testing BackendAssignment02A Rest client get users by XML format **************************** //
        
        System.out.println("#------------ Rest Client getting users by XML format --------------#");
        
        clientResponse = webResource.path("/xml").accept(MediaType.TEXT_XML).get(ClientResponse.class);
        
        System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		genericType = new GenericType<List<User>>() {
        };
        
        data = new ArrayList<User>();
        data = (clientResponse.getEntity(genericType));
        
        System.out.println("#------------ Available user list -----------#");
        for(User userdetail : data){
        	System.out.println("firstname : " + userdetail.getFirstName());
        	System.out.println("lastname : " + userdetail.getLastName());
        	System.out.println("phonenumber : " + userdetail.getPhoneNumber());
        	System.out.println("email : " + userdetail.getEmail());
        	System.out.println();
        }
        
        // *********************** Testing BackendAssignment02A Rest client get user by given id  **************************** //
        
        String id = "1";
        
        System.out.println("#------------ Rest Client getting user by id " + id + " --------------#");
        
        clientResponse = webResource.path("/" + id).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
        
        GenericType<User> singleObject = new GenericType<User>(){};
        
        user = (User) (clientResponse.getEntity(singleObject));
        
        System.out.println("#------------ Retrieved user detail -----------#");
        System.out.println("firstname : " + user.getFirstName());
    	System.out.println("lastname : " + user.getLastName());
    	System.out.println("phonenumber : " + user.getPhoneNumber());
    	System.out.println("email : " + user.getEmail());
    	System.out.println();
    	
    	// *********************** Testing BackendAssignment02A Rest client get user by given id  ****************************** //
    	
    	Integer pager = 0;
    	Integer limit = 2;
    	
    	System.out.println("#------------ Rest Client getting user with pagination support --------------#");
    	
    	clientResponse = webResource.path("/json").path("/"+pager.toString()).path("/"+limit.toString()).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    	
    	System.out.println(clientResponse.getStatus());
    	System.out.println(clientResponse.getStatusInfo());
    	
    	genericType = new GenericType<List<User>>() {
        };
        
        data = new ArrayList<User>();
        data = (clientResponse.getEntity(genericType));
        
        System.out.println("#------------ Gathered user list from pagination -----------#");
        for(User userdetail : data){
        	System.out.println("firstname : " + userdetail.getFirstName());
        	System.out.println("lastname : " + userdetail.getLastName());
        	System.out.println("phonenumber : " + userdetail.getPhoneNumber());
        	System.out.println("email : " + userdetail.getEmail());
        	System.out.println();
        }
    	
    	// *********************** Testing BackendAssignment02A Rest client update user by given id **************************** //
    	
    	id = "3";
    	
    	clientResponse = webResource.path("/" + id).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
        
        singleObject = new GenericType<User>(){};
        
        user = (User) (clientResponse.getEntity(singleObject));
        
    	user.setLastName("Mellark updated");
    	
    	System.out.println("#------------ Rest Client updating user by id " + user.getId() + " --------------#");
    	
    	clientResponse = webResource.path("/" + user.getId()).type(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML).put(ClientResponse.class, user);
    	
    	System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		GenericType<String> message = new GenericType<String>(){};
		
		System.out.println("#------------- Server response -----------#");
		System.out.println((String)(clientResponse.getEntity(message)));
		
		// *********************** Testing BackendAssignment02A Rest client delete user by given id **************************** //
		
		id = "2";
		
		System.out.println("#------------ Rest Client deleting user by id " + id + " --------------#");
		
		clientResponse = webResource.path("/" + id).delete(ClientResponse.class);
		
		System.out.println(clientResponse.getStatus());
		System.out.println(clientResponse.getStatusInfo());
		
		message = new GenericType<String>(){};
		
		System.out.println("#------------- Server response -----------#");
		System.out.println((String)(clientResponse.getEntity(message)));
        
	}
	
	private static URI getBaseUri(){
		return UriBuilder.fromUri(Constants.URI).build();
	}

}
