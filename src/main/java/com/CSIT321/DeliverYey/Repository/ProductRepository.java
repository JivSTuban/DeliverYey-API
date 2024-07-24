package com.CSIT321.DeliverYey.Repository;

import com.CSIT321.DeliverYey.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByName(String name);
}
