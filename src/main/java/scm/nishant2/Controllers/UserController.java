package scm.nishant2.Controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import scm.nishant2.services.UserService;

import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("/user")
public class UserController {


  private  Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
     private UserService userService ;


    
      
    @RequestMapping( value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    } 
    
    @RequestMapping( value = "/profile") 
    public String userProfile(Model model , Authentication authentication) {
      
     
        return "user/profile";
    } 
}
