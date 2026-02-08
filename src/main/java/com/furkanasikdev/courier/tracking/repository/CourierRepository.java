package com.furkanasikdev.courier.tracking.repository;

import com.furkanasikdev.courier.tracking.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {

}