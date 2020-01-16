package cisco.redis.cache.services;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import cisco.redis.cache.entities.User;

public interface IUserService {
	
	@Cacheable(value= "userCache", key= "#userId")
	List<User> getAllUsers();
	
	@Cacheable(value= "allUsersCache", unless= "#result.size() == 0")
	User getUserById(long userId);

	User addUser(User user);

	User updateUser(User user);

	void deleteUser(long userId);
}
