package com.example.barber.services;

import com.example.barber.models.Employee;
import com.example.barber.models.RefreshToken;
import com.example.barber.repositories.EmployeeRepository;
import com.example.barber.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public RefreshToken createRefreshToken(String phone){

        RefreshToken refreshToken = RefreshToken.builder()
                .employee(employeeRepository.findByPhone(phone).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(100000000)).build();
        return  refreshTokenRepository.save(refreshToken);

    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken refreshToken){

        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            return null;
        }

        return refreshToken;
    }
}
