package scm.nishant2.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import scm.nishant2.entities.user;
import scm.nishant2.helpers.Helper;
import scm.nishant2.services.UserService;

@ControllerAdvice
public class RootController {
      
      @Autowired
      private UserService userService ;

      private  Logger logger = LoggerFactory.getLogger(UserController.class);
    //  =================================this is very importnat method we can use it any wherever we want ========================================================================================
     @ModelAttribute
     public void addLoggedInUserInformation(Model model , Authentication authentication){
        if(authentication == null){
            return ;
        }

      String name =  Helper.getEmailOfLoggedInUser(authentication);
      logger.info("User logged in : {}", name);
     user user =  userService.getUserByEmail(name);
         System.out.println( user);
         System.out.println(user.getName());
         System.out.println(user.getPhoneNumber());
         model.addAttribute("loggedInUser",user);

     }

    //  =================================================================================================================================================================

}
