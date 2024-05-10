package com.example.barber.repositories;

import com.example.barber.models.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Integer> {
}
