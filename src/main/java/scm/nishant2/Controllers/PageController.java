package scm.nishant2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import scm.nishant2.entities.user;
import scm.nishant2.forms.UserForm;
import scm.nishant2.helpers.Message;
import scm.nishant2.helpers.MessageType;
import scm.nishant2.services.UserService;


@Controller
public class PageController {
     
     @Autowired
      private UserService userService;
      
      @RequestMapping("/")
      public String index(){
        return "redirect:/home";
      }
    

    @RequestMapping("/home") 
    public String home(Model model) { 
        System.out.println("Home page handler");
        model.addAttribute("name", "Substring technologies");
        model.addAttribute("ram", "shyam");
        model.addAttribute("github", "https://github.com/nsshukla23");
        return "Home";
    }

    @RequestMapping("/aboutus")
    public String aboutUs(){
      return "us";
    }
  
    // about routes 
    @RequestMapping("/about")
    public String aboutPage(){  
      return "about";
    }
     
    //services 
    @RequestMapping("/services")
    public String servicesPage(){ 
      return "services";
    }

    @RequestMapping("/contact")
    public String contactpage(){
      return "Contact";
    }
    @RequestMapping("/login")
    public String loginpage() {
        return "login";
    } 
    
    // @RequestMapping("/logout")
    // public String logoutpage() {
    //   return "redirect:/register";
    // } 


    @RequestMapping("/register")  
    public String registerpage(Model model){
      UserForm userform = new UserForm();
      model.addAttribute("userForm", userform);
      return "register";
    } 

    // process register 
    @RequestMapping(value = "/do_register", method = RequestMethod.POST) 
    public String processRegister(@Valid @ModelAttribute UserForm userForm, 
                                BindingResult rBindingResult,
                                HttpSession session) {
        
        if(rBindingResult.hasErrors()) {
            return "register";
        }
    
        // Create user with proper initialization
        user user = new user();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic(
                "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");

        user savedUser = userService.saveUser(user);

        System.out.println("user saved :");

        // message = "Registration Successful"

        // add the message:

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);
        return "redirect:/register";
    }
    
}
