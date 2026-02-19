package com.furkanasikdev.courier.tracking.domain.store;

import com.furkanasikdev.courier.tracking.domain.courier.Distance;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_entries", indexes = {
		@Index(name = "idx_store_entry_courier_id", columnList = "courier_id"),
		@Index(name = "idx_store_entry_store_name", columnList = "store_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "courier_id", nullable = false)
	private String courierId;

	@Column(name = "store_name", nullable = false)
	private String storeName;

	@Column(name = "entry_time", nullable = false)
	private LocalDateTime entryTime;

	@Column(name = "distance_to_store")
	private Double distanceToStore;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "lat", column = @Column(name = "courier_latitude")),
			@AttributeOverride(name = "lng", column = @Column(name = "courier_longitude"))
	})
	private Location courierLocation;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public static StoreEntry create(String courierId, String storeName, Location courierLocation,
									Distance distance, LocalDateTime entryTime) {
		return StoreEntry.builder()
				.courierId(courierId)
				.storeName(storeName)
				.courierLocation(courierLocation)
				.distanceToStore(distance.km())
				.entryTime(entryTime)
				.build();
	}
}
