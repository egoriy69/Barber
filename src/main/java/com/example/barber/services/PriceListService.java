package com.example.barber.services;

import com.example.barber.models.PriceList;
import com.example.barber.repositories.PriceListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PriceListService {

    private final PriceListRepository priceListRepository;

    public List<PriceList> getPriceList() {
        return priceListRepository.findAll();
    }
}
