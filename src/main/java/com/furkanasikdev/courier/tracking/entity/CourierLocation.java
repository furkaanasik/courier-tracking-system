package com.furkanasikdev.courier.tracking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "courier_locations", indexes = {
		@Index(name = "idx_courier_location_courier_id", columnList = "courier_id"),
		@Index(name = "idx_courier_location_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@Builder
public class CourierLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "courier_id", nullable = false)
	private String courierId;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
}