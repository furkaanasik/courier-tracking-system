package com.furkanasikdev.courier.tracking.infrastructure.persistence;

import com.furkanasikdev.courier.tracking.domain.courier.Courier;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.domain.courier.repository.CourierRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourierRepository extends JpaRepository<Courier, String>, CourierRepository {

	@Query("SELECT cl FROM CourierLocation cl WHERE cl.courierId = :courierId ORDER BY cl.timestamp ASC")
	List<CourierLocation> findLocations(@Param("courierId") String courierId);
}
