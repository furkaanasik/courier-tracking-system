package com.furkanasikdev.courier.tracking.infrastructure.persistence;

import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCourierLocationRepository extends JpaRepository<CourierLocation, Long> {
}
