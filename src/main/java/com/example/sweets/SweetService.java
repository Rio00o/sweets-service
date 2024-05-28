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

    public Sweet insert(String name, String company, int price, String prefecture) {
        Sweet sweet = new Sweet(null, name, company, price, prefecture);
        sweetMapper.insert(sweet);
        return sweet;
    }

    public void update(Integer id, String name, String company, int price, String prefecture) {
        Optional<Sweet> existingSweet = sweetMapper.findById(id);
        if (!existingSweet.isPresent()) {
            throw new SweetNotFoundException("Sweet not found");
        }
        Optional<Sweet> duplicatedSweet = sweetMapper.findByName(name);
        if (duplicatedSweet.isPresent() && !duplicatedSweet.get().getId().equals(id)) {
            throw new SweetDuplicatedException("Sweet already exists");
        }
        Sweet sweet = existingSweet.get();
        sweet.setName(name);
        sweet.setCompany(company);
        sweet.setPrice(price);
        sweet.setPrefecture(prefecture);
        sweetMapper.update(sweet);
    }

    public void delete(Integer id) {
        Optional<Sweet> sweetOptional = sweetMapper.findById(id);
        if (!sweetOptional.isPresent()) {
            throw new SweetNotFoundException("Sweet not found");
        }
        sweetMapper.delete(id);
    }
}
