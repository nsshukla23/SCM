package scm.nishant2.services;

import java.util.List;
import java.util.Optional;

import scm.nishant2.entities.user;

public interface UserService {

    user saveUser(user user);

    Optional<user> getUserById(String id);

    Optional<user> updateUser(user user);
    
    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<user> getAllUsers();

    user getUserByEmail(String email);
    

    // add more methods here related user service[logic]

}