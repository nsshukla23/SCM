package scm.nishant2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import scm.nishant2.repositories.UserRepo;

@Service
public class SecurityCoustomUserDetailService implements UserDetailsService {

    @Autowired 
    private UserRepo userRepo ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
        // apne user ko load kerna h
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found with this username" + username ));
    }

}
 