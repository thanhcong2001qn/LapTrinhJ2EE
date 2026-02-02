package com.example.bai4.model;


import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class Product {

    private Integer id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm không được để trống và cho phép nhập từ 1 - 9999999")
    @Max(value = 9999999, message = "Giá sản phẩm không được để trống và cho phép nhập từ 1 - 9999999")
    private Integer price;

    private String image;

    @NotBlank(message = "Tên hình ảnh không quá 200 kí tự")
    @Size(max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    private String imageName;

    @NotBlank(message = "Category không được để trống")
    private String categoryName;

    // Transient field for file upload
    private MultipartFile imageFile;

    public Product() {
    }

    public Product(Integer id, String name, Integer price, String image, String imageName, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.imageName = imageName;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}