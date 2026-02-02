package com.example.bai4.Service;

import com.example.bai4.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private Integer currentId = 1;

    // Lưu vào thư mục static/uploads để Spring Boot có thể serve
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public ProductService() {
        // Tạo thư mục uploads nếu chưa tồn tại
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Integer id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) throws IOException {
        product.setId(currentId++);

        // Handle file upload
        if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
            String fileName = saveImage(product.getImageFile());
            product.setImage(fileName);
        }

        products.add(product);
    }

    public void updateProduct(Product product) throws IOException {
        Product existingProduct = getProductById(product.getId());
        if (existingProduct != null) {
            // Handle file upload if new file is provided
            if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
                String fileName = saveImage(product.getImageFile());
                product.setImage(fileName);
            } else {
                // Keep existing image if no new file uploaded
                product.setImage(existingProduct.getImage());
            }

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImage(product.getImage());
            existingProduct.setImageName(product.getImageName());
            existingProduct.setCategoryName(product.getCategoryName());
        }
    }

    public void deleteProduct(Integer id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    private String saveImage(MultipartFile file) throws IOException {
        // Create upload directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + extension;

        // Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Laptop");
        categories.add("Điện thoại");
        categories.add("Tablet");
        categories.add("Máy tính bảng");
        categories.add("Phụ kiện");
        return categories;
    }
}