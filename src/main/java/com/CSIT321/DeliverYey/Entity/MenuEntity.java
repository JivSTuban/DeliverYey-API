package com.CSIT321.DeliverYey.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblmenu")
public class MenuEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private int menuId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY) // Adjusted fetch type
    @JoinColumn(name = "canteen_id") // Adjusted column name
    private StaffEntity staff;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.MENU;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid")
    private StudentEntity student;
}
