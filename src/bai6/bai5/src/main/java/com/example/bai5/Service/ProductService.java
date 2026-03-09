package com.example.bai5.Service;

import com.example.bai5.Repository.CategoryRepository;
import com.example.bai5.Repository.ProductRepository;
import com.example.bai5.model.Product;
import com.example.bai5.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    // Thêm sản phẩm mới
    public Product addProduct(Product product) throws IOException {
        // Handle file upload
        if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
            String fileName = saveImage(product.getImageFile());
            product.setImage(fileName);
            product.setImageName(product.getImageFile().getOriginalFilename());
        }

        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product updateProduct(Product product) throws IOException {
        Optional<Product> existingProductOpt = productRepository.findById(product.getId());

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Handle file upload if new file is provided
            if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
                String fileName = saveImage(product.getImageFile());
                existingProduct.setImage(fileName);
                existingProduct.setImageName(product.getImageFile().getOriginalFilename());
            }

            // Update other fields
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategoryName(product.getCategoryName());

            return productRepository.save(existingProduct);
        }

        return null;
    }

    // Xóa sản phẩm
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // Lưu file ảnh
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

    // Lấy danh sách categories từ database
    public List<String> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }



}