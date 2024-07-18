package com.CSIT321.DeliverYey.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblproducts")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_filename")
    private String productFilename;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_quantity")
    private int productQuantity;
}
