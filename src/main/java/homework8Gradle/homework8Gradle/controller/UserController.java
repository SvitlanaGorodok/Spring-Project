package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.service.RoleService;
import homework8Gradle.homework8Gradle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView model = new ModelAndView("/users/findall");
        model.addObject("users", userService.findAll());
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/save")
    public ModelAndView saveForm(){
        ModelAndView model = new ModelAndView("/users/save");
        model.addObject("roles", roleService.findAll());
        model.addObject("emails", userService.findAllEmails());
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("userDto") UserDto userDto){
        userService.save(userDto);
        return new RedirectView("/users");
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") UUID id){
        ModelAndView model = new ModelAndView("users/update");
        UserDto user = userService.findById(id);
        model.addObject("user", user);
        model.addObject("roles", roleService.findAll());
        model.addObject("emails", userService.findAllEmails().stream()
                .filter(u -> !u.equals(user.getEmail()))
                .collect(Collectors.toList()));
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/update")
    public RedirectView update(@Validated @ModelAttribute("userDto") UserDto userDto){
        userService.save(userDto);
        return new RedirectView("/users");
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id){
        userService.deleteById(id);
        return new RedirectView("/users");
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
            userService.register(user);
        } catch (UserAlreadyExistException ex) {
            redirect.setUrl("/users/registration");
            redirect.addStaticAttribute("msg", "Account with provided email already exists!");
            return redirect;
        }
        redirect.addStaticAttribute("msg", "User successfully registered! Please login!");
        return redirect;
    }
}
