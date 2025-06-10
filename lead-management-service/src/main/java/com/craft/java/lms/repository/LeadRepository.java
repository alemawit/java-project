package com.craft.java.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.craft.java.lms.model.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}
