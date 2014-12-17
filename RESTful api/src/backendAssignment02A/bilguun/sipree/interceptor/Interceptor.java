package backendAssignment02A.bilguun.sipree.interceptor;

import java.util.StringTokenizer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import backendAssignment02A.bilguun.sipree.service.utils.Constants;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class Interceptor implements ContainerRequestFilter {

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {

		String auth = containerRequest.getHeaderValue("authorization");

		if (auth == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		String usernamePassword = auth.replaceFirst(
				Constants.AUTH_SCHEME + " ", "");

		usernamePassword = new String(Base64.decode(usernamePassword));

		if (usernamePassword == null || usernamePassword.isEmpty()) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		StringTokenizer tokenizer = new StringTokenizer(usernamePassword, ":");

		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		
		boolean isValid = username.equals(Constants.SAMPLE_USER) && password.equals(Constants.SAMPLE_PASSWORD);
		
		if(!isValid){
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		return containerRequest;
	}
}
