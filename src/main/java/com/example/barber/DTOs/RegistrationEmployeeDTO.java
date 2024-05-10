package com.example.barber.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationEmployeeDTO {

//    @UniquePhoneReg
    @NotBlank(message = "Поле не может быть пустым")
    private String phone;

    @NotBlank(message = "Поле не может быть пустым")
//    @Size(min = 6, message = "пароль должен содержать минимум 6 символов")
    private String password;

    private String confirmPassword;

    private String FIO;
}
