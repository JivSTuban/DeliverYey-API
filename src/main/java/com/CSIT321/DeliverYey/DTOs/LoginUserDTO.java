package com.CSIT321.DeliverYey.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {
    @JsonProperty("idNumber")
    @NotBlank(message = "ID number is required")
    private String idNumber;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    private String password;
}
