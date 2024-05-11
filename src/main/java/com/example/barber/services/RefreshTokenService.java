package com.example.barber.services;

import com.example.barber.models.Employee;
import com.example.barber.models.RefreshToken;
import com.example.barber.repositories.EmployeeRepository;
import com.example.barber.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Employee employee = employeeRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким телефоном не найден: " + phone));

        // Пытаемся найти существующий токен
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByEmployee(employee);

        RefreshToken refreshToken = existingToken.orElse(new RefreshToken());

        // Устанавливаем или обновляем необходимые значения
        refreshToken.setEmployee(employee);
        refreshToken.setToken(UUID.randomUUID().toString()); // Генерация нового токена
        refreshToken.setExpiryDate(Instant.now().plusMillis(100000000)); // Установка нового времени истечения

        // Сохраняем токен в базе данных
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalStateException("Срок действия токена истек и был удален.");
        }
        return refreshToken;
    }
}
