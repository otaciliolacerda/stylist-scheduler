package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.repository.StylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StylistService {

    private StylistRepository repository;

    public StylistService(StylistRepository repository) {
        this.repository = repository;
    }

    public Stylist find(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Stylist> findAll() {
        return repository.findAll();
    }

    public Stylist create(Stylist stylist) {
        Stylist new_stylist = repository.save(stylist);
        return new_stylist;
    }
}
