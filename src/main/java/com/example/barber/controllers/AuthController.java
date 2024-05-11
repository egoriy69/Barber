package com.example.barber.controllers;

import com.example.barber.DTOs.JwtRequest;
import com.example.barber.DTOs.JwtResponse;
import com.example.barber.DTOs.RefreshTokenRequest;
import com.example.barber.DTOs.RegistrationEmployeeDTO;
import com.example.barber.Exception.PasswordConfirmationException;
import com.example.barber.models.RefreshToken;
import com.example.barber.security.JwtUtil;
import com.example.barber.services.EmployeeService;
import com.example.barber.services.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

//    private final AuthService authService;

    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody JwtRequest jwtRequest) {
        try {

            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken
                    (jwtRequest.getPhone(),
                            jwtRequest.getPassword()));

        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Неправильный логин или пароль"));

        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getPhone());

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwtUtil.generateToken(userDetails))
                .refreshToken(refreshTokenService.createRefreshToken(jwtRequest.getPhone()).getToken())
//                .user(userWithRoleDTO)
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid RegistrationEmployeeDTO employeeDTO) {
        employeeService.createUser(employeeDTO);
        return new ResponseEntity<>("пользователь создан", HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        employeeService.logout(principal);
        return new ResponseEntity<>("пользователь вышел", HttpStatus.OK);
    }


    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmployee)
                .map(user -> {

                    String token = jwtUtil.generateToken
                            (userDetailsService.loadUserByUsername(user.getPhone()));

                    return JwtResponse.builder()
                            .token(token)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new PasswordConfirmationException("Нужна повторная авторизация"));
    }

}
