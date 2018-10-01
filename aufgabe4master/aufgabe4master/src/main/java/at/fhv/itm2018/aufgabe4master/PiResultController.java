package at.fhv.itm2018.aufgabe4master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;

@Controller
public class PiResultController {
    @RequestMapping(value = {"/getAllPiResults"}, method = RequestMethod.GET)
    public ModelAndView listAllPiResults() {
        LinkedList<String> list = new LinkedList<>();
        list.add("HELLO WORLD!");
        list.add("WELCOME");

        ModelAndView map = new ModelAndView("index");
        map.addObject("lists", list);

        return map;
    }
}
