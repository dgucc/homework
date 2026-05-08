package minfin.ovcogr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(path="hello", method=RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("message", "Hello World from Spring Boot and JSP!");
        return "index"; // src/main/webapp/WEB-INF/jsp/home.jsp
    }
}
