package com.example.bai4.Controllers;

import com.example.bai4.Service.ProductService;
import com.example.bai4.model.Product;
import com.example.bai4.validation.ProductValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductValidator productValidator;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getCategories());
        return "product/add";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        // Custom validation
        productValidator.validate(product, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productService.getCategories());
            return "product/add";
        }

        try {
            productService.addProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
            return "redirect:/products";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Lỗi khi upload hình ảnh!");
            model.addAttribute("categories", productService.getCategories());
            return "product/add";
        }
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getCategories());
        return "product/edit";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Integer id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productService.getCategories());
            return "product/edit";
        }

        try {
            product.setId(id);
            productService.updateProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
            return "redirect:/products";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Lỗi khi upload hình ảnh!");
            model.addAttribute("categories", productService.getCategories());
            return "product/edit";
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
}
