package scm.nishant2.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import scm.nishant2.entities.Providers;
import scm.nishant2.entities.user;
import scm.nishant2.helpers.AppConstants;
import scm.nishant2.repositories.UserRepo;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                logger.info("OAuthAuthenicationSuccessHandler");

                // data in database save
                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

                // logger.info(user.getName());

                // user.getAttributes().forEach((key, value) -> {
                //     logger.info("{}-> {}", key , value);
                // });

                // logger.info(user.getAttributes().toString());

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();


                // create user and save to it in database 
                user user1 = new user();
                user1.setName(name);
                user1.setEmail(email);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setProviderUserId(user.getName());
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("This is created by google... ");
                

               user user2  = userRepo.findByEmail(email).orElse(null);
               if(user2 == null){
                userRepo.save(user1);
               }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

                // response.sendRedirect("/user/profile");


       
    }

}
