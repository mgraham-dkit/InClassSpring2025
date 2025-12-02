package web_patterns.inclassspring2025.controllers;

import jakarta.validation.constraints.NotEmpty;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import web_patterns.inclassspring2025.entities.Product;
import web_patterns.inclassspring2025.persistence.Connector;
import web_patterns.inclassspring2025.persistence.MySqlConnector;
import web_patterns.inclassspring2025.persistence.ProductDao;
import web_patterns.inclassspring2025.persistence.ProductDaoImpl;
import web_patterns.inclassspring2025.services.ProductService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products/")
public class ProductController {
    private ProductService productService;

    public ProductController() {
        Connector connector = new MySqlConnector("properties/database.properties");
        ProductDao prodDao = new ProductDaoImpl(connector);
        productService = new ProductService(prodDao);
    }

    @GetMapping(path = "getAll", produces = "application/json")
    public List<Product> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return products;
        } catch (SQLException e) {
            log.error("SQL exception occurred during product list request. Error: {}", e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Database error"
            );
        }
    }
    @GetMapping(path="/{prodCode}", produces="application/json")
    public Product getProductByCode(@PathVariable String prodCode) {
        try {
            Product p = productService.getProductByCode(prodCode);
            if(p == null){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            }
            return p;
        } catch (SQLException e) {
            log.error("SQL exception occurred during product request. Error: {}", e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Database error"
            );
        }
    }
}
