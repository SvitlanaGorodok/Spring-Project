package homework8Gradle.homework8Gradle.controller;

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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService service;
    private final ManufacturerService manufacturerService;

    @GetMapping ("/showall")
    public ModelAndView showAll(){
        ModelAndView result = new ModelAndView("/products/findall");
        List<ProductDto> products = service.findAll()
                .stream()
                .map(ProductDto::fromProduct)
                .collect(Collectors.toList());
        for (ProductDto product : products) {
            product.setManufacturerId(manufacturerService.findById(product.getManufacturerId()).getName());
        }
        result.addObject("products", products);
        return result;
    }

    @GetMapping("/findbyid")
    public ModelAndView getById(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        ModelAndView result = new ModelAndView("/products/findbyid");
        if (!id.isEmpty()){
            ProductDto product = ProductDto.fromProduct(service.findById(id));
            product.setManufacturerId(manufacturerService.findById(product.getManufacturerId()).getName());
            result.addObject("products", product);
        }
        return result;
    }

    @GetMapping("/save")
    public ModelAndView saveForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/products/save");
        result.addObject("msg", msg);
        result.addObject("manufacturers", manufacturerService.findAll());
        return result;
    }

    @PostMapping("/save")
    public RedirectView save(@Validated @ModelAttribute("productDto") ProductDto productDto){
        ProductDto.fromProduct(service.save(ProductDto.toProduct(productDto,
                        ManufacturerDto.fromManufacturer(
                        manufacturerService.findById(productDto.getManufacturerId())))));
        RedirectView redirect = new RedirectView("/product/save");
        redirect.addStaticAttribute("msg", "Product successfully saved!");
        return redirect;
    }

    @GetMapping("/delete")
    public ModelAndView deleteForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg){
        ModelAndView result = new ModelAndView("/products/delete");
        result.addObject("msg", msg);
        return result;
    }

    @PostMapping("/delete")
    public RedirectView delete(@RequestParam(name = "id", required = false, defaultValue = "") String id){
        RedirectView redirect = new RedirectView("/product/delete");
        try{
            service.deleteById(id);
            redirect.addStaticAttribute("msg", "Product successfully deleted!");
        } catch (EmptyResultDataAccessException ex){
            redirect.addStaticAttribute("msg", "Product doesn't exist!");
        }
        return redirect;
    }

}
