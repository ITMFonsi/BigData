package at.fhv.itm2018.aufgabe4master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;

@Controller
public class PiResultController {


    public LinkedList<String>  listAllPiResults(String numberOfThrows) {
        PiResultService s1 = new PiResultService(numberOfThrows);
        return s1.getResultsFromInstances();
    }

    @RequestMapping(value="/getpiresult", method=RequestMethod.POST)
    public ModelAndView getPiResult(@RequestParam("throws") String numberOfthrows) {
        ModelAndView map = new ModelAndView("index");
        map.addObject("lists", listAllPiResults(numberOfthrows));
        return map;
    }
}
