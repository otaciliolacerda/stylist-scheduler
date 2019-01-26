package com.otacilio.challange.stylistscheduler.repository;

import com.otacilio.challange.stylistscheduler.model.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StylistRepository extends JpaRepository<Stylist, Long> {

}