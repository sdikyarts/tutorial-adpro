package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute("product") Product product, Model model) {
        productService.create(product);
        return "redirect:list";
    }
    
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Product product = productService.findAll().stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{id}")
    public String editProductPost(@PathVariable String id, @ModelAttribute("product") Product product) {
        productService.edit(id, product);
        return "redirect:/product/list";
    }
}
