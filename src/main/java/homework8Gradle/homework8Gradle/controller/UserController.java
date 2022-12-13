package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService service;

    @GetMapping("/showall")
    public ModelAndView showAll() {
        ModelAndView result = new ModelAndView("/users/findall");
        List<UserDto> users = service.findAll()
                                    .stream()
                                    .map(UserDto::fromUser)
                                    .collect(Collectors.toList());
        result.addObject("users", users);
        return result;
    }

    @GetMapping("/findbyid")
    public ModelAndView getById(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        ModelAndView result = new ModelAndView("/users/findbyid");
        result.addObject("users", UserDto.fromUser(service.findById(id)));
        return result;
    }

    @GetMapping("/save")
    public ModelAndView saveForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/users/save");
        result.addObject("msg", msg);
        return result;
    }

    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("userDto") UserDto userDto){
        UserDto.fromUser(service.save(UserDto.toUser(userDto)));
        RedirectView redirect = new RedirectView("/user/save");
        redirect.addStaticAttribute("msg", "User successfully saved!");
        return redirect;
    }

    @GetMapping("/delete")
    public ModelAndView deleteForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/users/delete");
        result.addObject("msg", msg);
        return result;
    }

    @PostMapping("/delete")
    public RedirectView delete(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        RedirectView redirect = new RedirectView("/user/delete");
        try{
            service.deleteById(id);
            redirect.addStaticAttribute("msg", "User successfully deleted!");
        } catch (EmptyResultDataAccessException ex){
            redirect.addStaticAttribute("msg", "User doesn't exist!");
        }
        return redirect;
    }

}
