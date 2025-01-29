package scm.nishant2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scm.nishant2.entities.user;

@Repository
public interface UserRepo extends JpaRepository<user , String > {
  
    Optional<user>  findByEmail(String email);
}
