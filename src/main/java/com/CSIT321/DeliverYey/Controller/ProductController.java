package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Entity.ProductEntity;
import com.CSIT321.DeliverYey.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController  {
    @Autowired
    private ProductService productService;

    @PostMapping("/insertProduct")
    public ProductEntity insertProduct(@RequestBody ProductEntity product) {
        return productService.insertProduct(product);
    }

    @GetMapping
    public Iterable<ProductEntity> getAllProduct() {
        return productService.getProducts();
    }

    @GetMapping(params = "id")
    public ProductEntity getProduct(@RequestParam int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/productImage")
    public String getAllProduct(@RequestParam int id) {
        return productService.getProductFilenameById(id);
    }

    @PutMapping("/updateProduct")
    public ProductEntity updateProduct(@RequestBody ProductEntity product, @RequestParam int id) throws NoSuchFieldException {
        return productService.updateProduct(id, product);
    }
    @DeleteMapping("/deleteProduct")
    @CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.DELETE})
    public String deleteOrderItem(@RequestParam int id) {
        return productService.deleteProduct(id);
    }
}