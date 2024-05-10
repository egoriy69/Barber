package com.example.barber.services;

import com.example.barber.DTOs.RegistrationEmployeeDTO;
import com.example.barber.Exception.PasswordConfirmationException;
import com.example.barber.models.Employee;
import com.example.barber.models.EmployeeInfo;
import com.example.barber.repositories.EmployeeRepository;
import com.example.barber.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createUser(RegistrationEmployeeDTO employeeDTO) {

        if (!employeeDTO.getPassword().equals(employeeDTO.getConfirmPassword())) {
            throw new PasswordConfirmationException("Пароли не совпадают");

        }

        Employee employee = new Employee();

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setEmployee(employee);
        employeeInfo.setFIO(employeeDTO.getFIO());

        employee.setPhone(employeeDTO.getPhone());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setEmployeeInfo(employeeInfo);


        employeeRepository.save(employee);
    }

    @Transactional
    public void logout(Principal principal) {
        Employee user = employeeRepository.findByPhone(principal.getName()).get();
        refreshTokenRepository.delete(refreshTokenRepository.findByEmployee(user).get());
    }
}
