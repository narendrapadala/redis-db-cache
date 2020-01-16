package cisco.redis.cache.repository;
import org.springframework.data.repository.CrudRepository;

import cisco.redis.cache.entities.User;

public interface UserRepository extends CrudRepository<User, Long>  {

}
