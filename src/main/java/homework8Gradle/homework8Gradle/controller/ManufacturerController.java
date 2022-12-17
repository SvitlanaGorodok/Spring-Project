package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/manufacturer")
@RestController
public class ManufacturerController {

    private final ManufacturerService service;
    @GetMapping("/showall")
    public ModelAndView showAll(){
        ModelAndView result = new ModelAndView("/manufacturers/findall");
        List<ManufacturerDto> manufacturers = service.findAll()
                .stream()
                .map(ManufacturerDto::fromManufacturer)
                .collect(Collectors.toList());
        result.addObject("manufacturers", manufacturers);
        return result;
    }

    @GetMapping("/findbyid")
    public ModelAndView getById(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        ModelAndView result = new ModelAndView("/manufacturers/findbyid");
        if (!id.isEmpty()) {
            result.addObject("manufacturers", ManufacturerDto.fromManufacturer(service.findById(id)));
        }
        return result;
    }

    @GetMapping("/save")
    public ModelAndView saveForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/manufacturers/save");
        result.addObject("msg", msg);
        return result;
    }

    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("manufacturerDto") ManufacturerDto manufacturerDto){
        ManufacturerDto.fromManufacturer(service.save(ManufacturerDto.toManufacturer(manufacturerDto)));
        RedirectView redirect = new RedirectView("/manufacturer/save");
        redirect.addStaticAttribute("msg", "Manufacturer successfully saved!");
        return redirect;
    }

    @GetMapping("/delete")
    public ModelAndView deleteForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/manufacturers/delete");
        result.addObject("msg", msg);
        return result;
    }

    @PostMapping("/delete")
    public RedirectView delete(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        RedirectView redirect = new RedirectView("/manufacturer/delete");
        try{
            service.deleteById(id);
            redirect.addStaticAttribute("msg", "Manufacturer successfully deleted!");
        } catch (EmptyResultDataAccessException ex){
            redirect.addStaticAttribute("msg", "Manufacturer doesn't exist!");
        }
        return redirect;
    }

}
