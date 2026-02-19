package com.furkanasikdev.courier.tracking.domain.courier;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "courier_id", nullable = false)
	private String courierId;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "lat", column = @Column(name = "latitude", nullable = false)),
			@AttributeOverride(name = "lng", column = @Column(name = "longitude", nullable = false))
	})
	private Location location;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public static CourierLocation create(String courierId, Location location, LocalDateTime timestamp) {
		return CourierLocation.builder()
				.courierId(courierId)
				.location(location)
				.timestamp(timestamp)
				.build();
	}
}
