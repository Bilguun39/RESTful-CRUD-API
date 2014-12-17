package backendAssignment02A.bilguun.sipree.jaxrs.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import backendAssignment02A.bilguun.sipree.dao.UserDao;
import backendAssignment02A.bilguun.sipree.jaxb.model.User;
import backendAssignment02A.bilguun.sipree.service.utils.Constants;

import com.sun.jersey.api.JResponse;

@Path("/userService")
public class UserResource {
	
	//******************** CREATE *********************//

	/**
	 * Adds new user from given json, xml format into api
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_HTML)
	public Response postUser(User user) {

		StringBuilder sb = new StringBuilder(Constants.URL);

		UserDao.getInstance().saveOrUpdateUser(user);

		return Response.status(Status.CREATED).entity("Your provided user has been created").header("Location", sb.append(user.getId())).build();
	}

	/**
	 * Adds new user from given "form" parameter into api
	 * 
	 * @param firstname
	 * @param lastname
	 * @param phoneNumber
	 * @param email
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response postUser(@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("phonenumber") String phoneNumber,
			@FormParam("email") String email) {

		StringBuilder sb = new StringBuilder(Constants.URL);

		User user = new User(firstname, lastname, email, phoneNumber);

		UserDao.getInstance().saveOrUpdateUser(user);

		return Response.status(Status.CREATED).entity("Your provided user has been created").header("Location", sb.append(user.getId())).build();
	}
	
	/**
	 * Adds new users from given JSON, XML format into the API
	 * 
	 * @param users
	 * @return
	 */
	@POST
	@Path("list")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_HTML)
	public Response postUser(List<User> users){
		StringBuilder sb = new StringBuilder(Constants.URL);
		
		int size = users.size();
		
		for(int i = 0; i < size; i++){
			UserDao.getInstance().saveOrUpdateUser(users.get(i));
		}
		
		return Response.status(Status.CREATED).entity("Your provided user has been created").header("Location", sb.toString()).build();
	}
	
	//******************************* READ ****************************//

	/**
	 * return all of users in the API by JSON format
	 * 
	 * @return
	 */
	@GET
	@Path("json")
	@Produces({ MediaType.APPLICATION_JSON })
	public JResponse<List<User>> getUsersJson() {
		return JResponse.ok(UserDao.getInstance().getUsers()).build();
	}
	
	/**
	 * return all of users in the API by XML format
	 * 
	 * @return
	 */
	@GET
	@Path("xml")
	@Produces({ MediaType.TEXT_XML })
	public JResponse<List<User>> getUsersXml() {
		return JResponse.ok(UserDao.getInstance().getUsers()).build();
	}

	/**
	 * return count of users in the API
	 * 
	 * @return
	 */
	@GET
	@Path("count")
	@Produces({ MediaType.TEXT_PLAIN })
	public JResponse<String> getCount() {
		return JResponse.ok(String.valueOf(UserDao.getInstance().getUsers().size())).build();
	}
	
	/**
	 * This method will supports pagination for list of user objects
	 * return partial users or null
	 * 
	 * @param pager
	 * @param limit
	 * @return
	 */
	@GET
	@Path("/json/{pager}/{limit}")
	@Produces({ MediaType.APPLICATION_JSON })
	public JResponse<List<User>> getPagedUsers(@PathParam("pager") Integer pager, @PathParam("limit") Integer limit){
		return JResponse.ok(UserDao.getInstance().getUsers(pager, limit)).build();
	}

	/**
	 * method will return User object by given id through the path param
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JResponse<User> getUserById(@PathParam("id") String id) {
		return JResponse.ok(UserDao.getInstance().getUserById(id)).build();
	}
	
	//****************************** UPDATE ****************************//

	/**
	 * This method will consumes user by given JSON, XML format then update user by given id through path parameter
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_HTML)
	public Response updateUser(@PathParam("id") String id, User user) {
		if (UserDao.getInstance().contains(id)) {

			StringBuilder sb = new StringBuilder(Constants.URL);

			UserDao.getInstance().updateUser(id, user);

			return Response.status(Status.OK).entity("The User you providing has been updated").header("Location", sb.append(id)).build();

		} else {
			return Response.status(Status.BAD_REQUEST).entity("User id you providing has not found!").build();
		}
	}
	
	//******************************* DELETE **************************//
	
	/**
	 * This method will delete user by given id
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_HTML)
	public Response deleteUser(@PathParam("id") String id){
		if(UserDao.getInstance().contains(id)){
			UserDao.getInstance().deleteUserById(id);
			
			return Response.status(Status.NO_CONTENT).entity("The user has been successfully deleted").build();
		}else{
			return Response.status(Status.BAD_REQUEST).entity("User id that you providing has not found!").build();
		}
	}
	
	/**
	 * This method will delete all users in the system
	 * 
	 * @return
	 */
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	public Response deleteUsers(){
		UserDao.getInstance().deleteUsers();
		
		return Response.status(Status.NO_CONTENT).entity("The users has been successfully deleted").build();
	}
}
