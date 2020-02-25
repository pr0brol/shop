package springmarket.shop.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springmarket.shop.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByPhone(String phone);
    boolean existsByPhone(String phone);

    @Query("select u from User u where u.token = ?1")
    User findByToken(String token);

}
