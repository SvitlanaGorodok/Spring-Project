package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.FindProductParam;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;

import homework8Gradle.homework8Gradle.service.ManufacturerService;
import homework8Gradle.homework8Gradle.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
@Slf4j
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
        log.info("Handling create product: " + productDto);
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
        log.info("Handling update product: " + productDto);
        service.save(productDto);
        return new RedirectView("/products");
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id){
        log.info("Handling delete product with id: " + id);
        service.deleteById(id);
        return new RedirectView("/products");
    }

    @GetMapping("/find")
    public ModelAndView findForm(){
        ModelAndView model = new ModelAndView("products/find");
        model.addObject("manufacturers", manufacturerService.findAll());
        return model;
    }

    @PostMapping("/find")
    public ModelAndView find(@Validated @ModelAttribute("findProductParam") FindProductParam findProductParam){
        ModelAndView model = new ModelAndView("products/find");
        List<ProductDto> products = service.findByParameters(findProductParam);
        model.addObject("products", products);
        model.addObject("manufacturers", manufacturerService.findAll());
        return model;
    }

}
