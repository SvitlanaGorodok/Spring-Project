package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.service.ManufacturerService;
import homework8Gradle.homework8Gradle.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService service;
    private final ManufacturerService manufacturerService;

    @GetMapping ("")
    public ModelAndView showAll(){
        ModelAndView result = new ModelAndView("/products/findall");
        result.addObject("products", service.findAll());
        return result;
    }

    @GetMapping("/save")
    public ModelAndView saveForm(){
        ModelAndView result = new ModelAndView("/products/save");
        result.addObject("manufacturers", manufacturerService.findAll());
        return result;
    }

    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("productDto") ProductDto productDto){
        service.save(productDto);
        return new RedirectView("/products");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") UUID id){
        ModelAndView model = new ModelAndView("products/update");
        ProductDto product = service.findById(id);
        model.addObject("product", product);
        model.addObject("manufacturers", manufacturerService.findAll());
        return model;
    }

    @PostMapping("/update")
    public RedirectView update(@Validated @ModelAttribute("productDto") ProductDto productDto){
        service.save(productDto);
        return new RedirectView("/products");
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id){
        service.deleteById(id);
        return new RedirectView("/products");
    }

}
