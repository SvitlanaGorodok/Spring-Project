package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/manufacturers")
@RestController
public class ManufacturerController {

    private final ManufacturerService service;
    @GetMapping("")
    public ModelAndView showAll(){
        ModelAndView result = new ModelAndView("/manufacturers/findall");
        result.addObject("manufacturers", service.findAll());
        return result;
    }

    @GetMapping("/save")
    public ModelAndView saveForm(){
        ModelAndView model = new ModelAndView("/manufacturers/save");
        model.addObject("names", service.findAllNames());
        return model;
    }

    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("manufacturerDto") ManufacturerDto manufacturerDto){
        service.save(manufacturerDto);
        return new RedirectView("/manufacturers");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") UUID id){
        ModelAndView model = new ModelAndView("/manufacturers/update");
        ManufacturerDto manufacturer = service.findById(id);
        model.addObject("manufacturer", manufacturer);
        model.addObject("names", service.findAllNames().stream()
                .filter(n -> !n.equals(manufacturer.getName()))
                .collect(Collectors.toList()));
        return model;
    }

    @PostMapping("/update")
    public RedirectView update(@Validated @ModelAttribute("manufacturerDto") ManufacturerDto manufacturerDto){
        service.save(manufacturerDto);
        return new RedirectView("/manufacturers");
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id){
        service.deleteById(id);
        return new RedirectView("/manufacturers");
    }

}
