package backendAssignment02A.bilguun.sipree.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import backendAssignment02A.bilguun.sipree.jaxb.model.User;
import backendAssignment02A.bilguun.sipree.service.log.LoggingApi;

// DAO pattern for accessing user object from view with Singleton for instantiate class object once.
public class UserDao {

	private static UserDao instance;
	private Map<String, User> userRepo = new LinkedHashMap<>();
	private Integer idCreator = 0;

	private UserDao() {
	}
	
	/**
	 * Check first time instantiating or not
	 * then method will return its instance
	 * 
	 * @return UserDao
	 */
	public static UserDao getInstance(){
		
		if(instance == null){
			instance = new UserDao();
		}
		
		return instance;
	}
	
	/**
	 * if parameter object id has null will take next id then submit object
	 * 
	 * @param user
	 */
	public void saveOrUpdateUser(User user){
		if(user.getId() == null){
			user.setId(UserDao.getInstance().getNextId());
		}
		
		userRepo.put(user.getId(), user);
		
		LoggingApi.getInstance().getLogger().log(Level.INFO, "User with id : {0} has Successfully deleted", user.getId());
	}
	
	/**
	 * update user by given id
	 * 
	 * @param id
	 * @param user
	 */
	public void updateUser(String id, User user){
		if(id != null){
			user.setId(id);
			userRepo.put(id, user);
			
			LoggingApi.getInstance().getLogger().log(Level.INFO, "User with id : {0} has Successfully updated", id);
		}
	}
	
	/**
	 * delete user object by given object
	 * 
	 * @param user
	 */
	public void deleteUser(User user){
		userRepo.remove(user.getId());
		
		LoggingApi.getInstance().getLogger().log(Level.INFO, "User with id : {0} has Successfully deleted", user.getId());
	}
	
	/**
	 * delete user object by given id
	 * 
	 * @param id
	 */
	public void deleteUserById(String id){
		userRepo.remove(id);
		
		LoggingApi.getInstance().getLogger().log(Level.INFO, "User with id : {0} has Successfully deleted", id);
	}
	
	/**
	 * 
	 * delete all users in the system
	 */
	public void deleteUsers(){
		userRepo.clear();
		
		LoggingApi.getInstance().getLogger().log(Level.INFO, "All users has been successfully deleted");
	}
	
	/**
	 * List of user objects
	 * 
	 * @return List<User>
	 */
	public List<User> getUsers(){
		return new ArrayList<User>(userRepo.values());
	}
	
	/**
	 * method will find User object by given id then return object
	 * 
	 * @param id
	 * @return User
	 */
	public User getUserById(String id){
		return userRepo.get(id);
	}
	
	
	/**
	 * this method will supports pagination of our users in the API
	 * takes pager parameter, it will pass page index
	 * takes max parameter, it will pass maximum number of item per page 
	 * 
	 * @param pager
	 * @param limit
	 * @return
	 */
	public List<User> getUsers(int pager, int limit){
		List<User> users = this.getUsers();
		List<User> pagedUsers = new ArrayList<User>();
		
		pager = (pager > 0) ? pager : 1;
		
		int startIndex = pager * limit - limit;
		int endIndex = pager * limit;
		int size = users.size();
		
		if(size > 0 && startIndex < size){
			startIndex = (startIndex < 0) ? 0 : startIndex;
			endIndex = (endIndex > size) ? size : endIndex;
			
			for(int i = startIndex; i < endIndex; i ++){
				pagedUsers.add(users.get(i));
			}
			
			return pagedUsers;
		}
		
		return null;
		
	}
	
	/**
	 * method will check user is exists in system by given id
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean contains(String id){
		return userRepo.containsKey(id);
	}
	
	/**
	 * method will return generate id for next object
	 * 
	 * @return String
	 */
	public String getNextId(){
		StringBuilder sb = new StringBuilder();
		
		do{
			idCreator++;
			
			sb.setLength(0);
			sb.append(idCreator);
		}while(this.userRepo.containsKey(sb.toString()));
		
		return sb.toString();
	}
}
