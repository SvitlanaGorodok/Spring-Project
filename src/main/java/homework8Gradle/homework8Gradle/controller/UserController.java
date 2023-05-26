package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.security.MyUserDetailsService;
import homework8Gradle.homework8Gradle.security.UserPrincipal;
import homework8Gradle.homework8Gradle.service.RoleService;
import homework8Gradle.homework8Gradle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
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

    @GetMapping("/changepassword")
    public ModelAndView changePasswordForm(){
        ModelAndView model = new ModelAndView("/users/changepassword");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDto user = userService.findByEmail(userPrincipal.getUsername());
        model.addObject("user", user);
        return model;
    }

    @PostMapping("/changepassword")
    public RedirectView changePassword(@Validated @ModelAttribute("userDto") UserDto userDto){
        userService.changePassword(userDto);
        return new RedirectView("/");
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/find")
    public ModelAndView findForm(){
        ModelAndView model = new ModelAndView("users/find");
        model.addObject("roles", roleService.findAll());
        return model;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/find")
    public ModelAndView find(@Validated @ModelAttribute("userDto") UserDto userDto){
        ModelAndView model = new ModelAndView("users/find");
        List<UserDto> users = userService.findByParameters(userDto);
        model.addObject("users", users);
        model.addObject("roles", roleService.findAll());
        return model;
    }
}
