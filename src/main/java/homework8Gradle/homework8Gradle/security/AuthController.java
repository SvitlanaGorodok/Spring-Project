package homework8Gradle.homework8Gradle.security;

import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "msg", required = false, defaultValue = "") String msg,
                              String logout, String error) {
        ModelAndView model = new ModelAndView("login");
        model.addObject("msg", msg);
        if (error != null) {
            model.addObject("msg", "Your username or password is invalid!");
        }
        if (logout != null) {
            model.addObject("msg", "You have been successfully logout!");
        }
        return model;
    }

    @GetMapping("/registration")
    public ModelAndView registrationForm() {
        ModelAndView model = new ModelAndView("/users/registration");
        model.addObject("emails", userService.findAllEmails());
        return model;
    }

    @PostMapping("/registration")
    public RedirectView registration(@Validated @ModelAttribute("user") UserDto user, HttpServletRequest request){
        String password = user.getPassword();
        String username = user.getEmail();
        userService.register(user);
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/");
    }

    @GetMapping("/accessdenied")
    public String deniedPage() {
        return "forbidden";
    }

}
