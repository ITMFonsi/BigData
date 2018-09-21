package at.fhv.itm2018.awswebapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static String template = "Welcome, %s";

    @RequestMapping("/welcome")
    public User welcome(@RequestParam(value="name", defaultValue = "!") String name, @RequestParam(value="lname", defaultValue = "!") String lname) {
        return new User(String.format(template, name), lname);
    }

}
