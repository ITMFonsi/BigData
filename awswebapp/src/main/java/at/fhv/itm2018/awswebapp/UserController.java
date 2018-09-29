package at.fhv.itm2018.awswebapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static String template = "Welcome, %s";

    @RequestMapping("/welcome")
    public String welcome(@RequestParam(value="name", defaultValue = "!") String name, @RequestParam(value="lname", defaultValue = "!") String lname) {
        return new String("<html>\n" +
                "   <head>\n" +
                "      <title>Hello from our AWS Web-APP</title>\n" +
        "   </head>\n" +
                "\n" +
                "   <body>\n" +
                "      <h2>Looks like our Web-app is running!</h2>\n" +
                "   </body>\n" +
                "</html>");
    }

}
