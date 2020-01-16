package cisco.redis.cache.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cisco.redis.cache.entities.User;
import cisco.redis.cache.repository.UserRepository;
import cisco.redis.cache.services.IUserService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Override
	public List<User> getAllUsers() {
		System.out.println("--- Inside getAllUsers() ---");
		LOG.info("--- Inside getAllUsers() ---");
		List<User> list = new ArrayList<>();
		userRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public User getUserById(long userId) {
		System.out.println("--- Inside getUserById() ---");
		LOG.info("--- Inside getUserById() ---");
		return userRepository.findById(userId).get();
	}

	@Override
	@Caching(put = { @CachePut(value = "userCache", key = "#user.userId") }, evict = {
			@CacheEvict(value = "allUsersCache", allEntries = true) })
	public User addUser(User user) {
		System.out.println("--- Inside addUser() ---");
		LOG.info("--- Inside addUser() ---");
		return userRepository.save(user);
	}

	@Override
	@Caching(put = { @CachePut(value = "userCache", key = "#user.userId") }, evict = {
			@CacheEvict(value = "allUsersCache", allEntries = true) })
	public User updateUser(User user) {
		System.out.println("--- Inside updateUser() ---");
		LOG.info("--- Inside updateUser() ---");
		return userRepository.save(user);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "userCache", key = "#userId"),
			@CacheEvict(value = "allUsersCache", allEntries = true) })
	public void deleteUser(long userId) {
		System.out.println("--- Inside deleteUser() ---");
		LOG.info("--- Inside deleteUser() ---");
		userRepository.delete(userRepository.findById(userId).get());
	}

}
