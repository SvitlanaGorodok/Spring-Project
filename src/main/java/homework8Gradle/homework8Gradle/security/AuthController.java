package homework8Gradle.homework8Gradle.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    @GetMapping("/")
    public String homepage() {
        return "index";
    }
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "msg", required = false, defaultValue = "") String msg,
                              String logout, String error) {
        ModelAndView model = new ModelAndView("login");
        model.addObject("msg", msg);
        if (logout != null) {
            model.addObject("msg", "You have been logged out!");
        }
        if (error != null) {
            model.addObject("msg", "Your username or password is invalid!");
        }
        return model;
    }

    @GetMapping("/accessdenied")
    public String deniedPage() {
        return "forbidden";
    }


}
