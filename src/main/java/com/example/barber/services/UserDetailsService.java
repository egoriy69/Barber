package com.example.barber.services;

import com.example.barber.models.Employee;
import com.example.barber.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("пользователь '%s' не найден", username)));
//        String password = (user.getPassword() != null) ? user.getPassword() : "";
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        return new org.springframework.security.core.userdetails.User(
                user.getPhone(),
                user.getPassword(),
                authorities);

    }

    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByPhone(username);
    }

}
