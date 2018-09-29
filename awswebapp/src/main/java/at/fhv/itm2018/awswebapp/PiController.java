package at.fhv.itm2018.awswebapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PiController {

    @RequestMapping("/getPi")
    public String getPi(@RequestParam(value="throws", defaultValue = "10000") int numberOfThrows) {


        return new PiResult(numberOfThrows).toString();
    }
}

