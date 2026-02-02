package com.example.bai4.validation;


import com.example.bai4.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        // Validate image file when adding new product
        if (product.getId() == null &&
                (product.getImageFile() == null || product.getImageFile().isEmpty())) {
            errors.rejectValue("imageFile", "error.product",
                    "Khi thêm sản phẩm: thực hiện validation, upload hình ảnh");
        }
    }
}
