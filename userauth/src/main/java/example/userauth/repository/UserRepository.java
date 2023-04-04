package example.userauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.userauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{

	Optional<User> findById(String userId);
}
