package com.example.barber.repositories;

import com.example.barber.models.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer> {
}
