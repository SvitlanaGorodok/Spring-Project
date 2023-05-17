package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService service;
    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/showall")
    public ModelAndView showAll() {
        ModelAndView model = new ModelAndView("/users/findall");
        model.addObject("users", service.findAll());
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/findbyid")
    public ModelAndView getById(@RequestParam(name = "id", required = false, defaultValue = "") UUID id){
        ModelAndView model = new ModelAndView("/users/findbyid");
        model.addObject("users", service.findById(id));
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/save")
    public ModelAndView saveForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView model = new ModelAndView("/users/save");
        model.addObject("msg", msg);
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("userDto") UserDto userDto){
        service.save(userDto);
        RedirectView redirect = new RedirectView("/user/save");
        redirect.addStaticAttribute("msg", "User successfully saved!");
        return redirect;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/delete")
    public ModelAndView deleteForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView model = new ModelAndView("/users/delete");
        model.addObject("msg", msg);
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/delete")
    public RedirectView delete(@RequestParam(name = "id", required = false, defaultValue = "") UUID id){
        RedirectView redirect = new RedirectView("/user/delete");
        try{
            service.deleteById(id);
            redirect.addStaticAttribute("msg", "User successfully deleted!");
        } catch (EmptyResultDataAccessException ex){
            redirect.addStaticAttribute("msg", "User doesn't exist!");
        }
        return redirect;
    }

    @GetMapping("/registration")
    public ModelAndView registrationForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg) {
        ModelAndView model = new ModelAndView("/users/registration");
        model.addObject("msg", msg);
        return model;
    }

    @PostMapping("/registration")
    public RedirectView registration(@Validated @ModelAttribute("user") UserDto user){
        RedirectView redirect = new RedirectView("/login");
        try {
            service.register(user);
        } catch (UserAlreadyExistException ex) {
            redirect.setUrl("/user/registration");
            redirect.addStaticAttribute("msg", "Account with provided email already exists!");
            return redirect;
        }
        redirect.addStaticAttribute("msg", "User successfully registered! Please login!");
        return redirect;
    }



}
