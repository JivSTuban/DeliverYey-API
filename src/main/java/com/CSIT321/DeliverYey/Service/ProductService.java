package com.CSIT321.DeliverYey.Service;

import com.CSIT321.DeliverYey.Repository.ProductRepository;
import com.CSIT321.DeliverYey.Entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductEntity insertProduct(ProductEntity entity){
        if (getProductByName(entity.getProductName()) != null){
            throw new IllegalArgumentException("Product already exists.");
        }
        return productRepository.save(entity);
    }

    public List<ProductEntity> getProducts() {return productRepository.findAll();}

    public ProductEntity getProductById(int id) {return productRepository.findById(id).get();}

    public ProductEntity getProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    public String getProductFilenameById(int id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return product.getProductFilename();
        } else {
            return null;
        }
    }

    public ProductEntity updateProduct(int id, ProductEntity newProductDetails) throws NoSuchFieldException {
        ProductEntity product = new ProductEntity();
        if (productRepository.findById(id).isPresent()){
            product = productRepository.findById(id).get();

            product.setProductName(newProductDetails.getProductName());
            product.setProductFilename(newProductDetails.getProductFilename());
            product.setProductPrice(newProductDetails.getProductPrice());
            product.setProductCategory(newProductDetails.getProductCategory());
            product.setProductQuantity(newProductDetails.getProductQuantity());

            productRepository.save(product);

        }else{
            throw new NoSuchFieldException("Product with ID: " + id +", cannot be found");
        }


        return productRepository.save(product);
    }

    public String deleteProduct(int id) {
        String msg = "";

        if (productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            msg = "Student " + id +" is successfully deleted!";
        } else
            msg = "Student " + id + " does not exist.";
        return msg;
    }
}
