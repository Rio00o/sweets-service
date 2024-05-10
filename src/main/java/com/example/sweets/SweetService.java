package com.example.sweets;

import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SweetService {
    private final SweetMapper sweetMapper;
    public SweetService(SweetMapper sweetMapper) {
        this.sweetMapper = sweetMapper;
    }

    public Sweet findSweet(int id) {
        Optional<Sweet> sweet = this.sweetMapper.findById(id);
        if (sweet.isPresent()) {
            return sweet.get();
        } else {
            throw new SweetNotFoundException("sweets not found");
        }
    }
}
