package com.CSIT321.DeliverYey.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblstudent")
public class StudentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private int sid;

    @NotEmpty(message = "ID number is required.")
    @Column(name = "id_number")
    @JsonProperty
    private String idNumber;

    @NotEmpty(message = "Email is required.")
    @JsonProperty
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password is required.")
    @JsonProperty
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.STUDENT;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private StaffEntity staff;

    @OneToOne(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private OrderEntity order;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private MenuEntity menu;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private DeliveryEntity delivery;
}
