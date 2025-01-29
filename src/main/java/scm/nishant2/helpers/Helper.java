package scm.nishant2.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // agar email is password s login kiya gya h to email kaise nikale sab ka method
        // alg alg hota h
        // ager user username aur passowrd s login kiya h to use else m bhej do
        if (authentication instanceof OAuth2AuthenticationToken) {

            // agr signin google s kiya gya h to aise nikale
            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {

                // sign with google
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {

                // // sign with github
                // System.out.println("Getting email from github");
                // username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                //         : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            // sign with facebook
            return username;


        } else {
            System.out.println("getting data from local database ❤️❤️");
            return authentication.getName();
        }

    }
}
