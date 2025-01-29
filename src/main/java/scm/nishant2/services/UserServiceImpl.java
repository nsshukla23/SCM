package scm.nishant2.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import scm.nishant2.entities.user;
import scm.nishant2.helpers.AppConstants;
// import scm.nishant2.helpers.Helper;
import scm.nishant2.helpers.ResourceNotFoundException;
import scm.nishant2.repositories.UserRepo;


@Service
public class UserServiceImpl implements UserService {

    @Autowired 
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    // @Autowired
    // private  Helper helper;

    @Override
public user saveUser(user user) {
    // Debug logging
    System.out.println("Before save - Enabled status: " + user.isEnabled());
    System.out.println("Before save - Password: " + user.getPassword());
    
    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);
    
    // Explicitly set enabled to true
    user.setEnabled(true);
    
    // Encode password
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    System.out.println("Encoded password: " + encodedPassword);
    user.setPassword(encodedPassword);
    
    // Set role
    user.setRoleList(List.of(AppConstants.ROLE_USER));
    
    user savedUser = userRepo.save(user);
    
    // Debug logging
    System.out.println("After save - Enabled status: " + savedUser.isEnabled());
    System.out.println("After save - Password: " + savedUser.getPassword());
    
    return savedUser;
}

    @Override
    public Optional<user> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<user> updateUser(user user) {

        user user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // update karenge user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // save the user in database
        user save = userRepo.save(user2);
        return Optional.ofNullable(save);

    }
 
    @Override
    public void deleteUser(String id) {
        user user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExist(String userId) {
        user user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        user user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<user> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public user getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }

  

}