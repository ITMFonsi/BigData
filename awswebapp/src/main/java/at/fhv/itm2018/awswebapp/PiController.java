package at.fhv.itm2018.awswebapp;

import org.springframework.web.bind.annotation.*;

@RestController
public class PiController {

    @RequestMapping("/getpi")
    public @ResponseBody PiResult getPi(@RequestParam(value="throws", defaultValue = "10000") int numberOfThrows) {
        PiResult result = new PiResult(numberOfThrows);
        return result;
    }
}

