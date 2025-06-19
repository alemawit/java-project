package com.craft.java.cms.repository;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.craft.java.cms.model.Customer;

@Repository  // add this annotation
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByCustomerId(String customerId);
	



}
