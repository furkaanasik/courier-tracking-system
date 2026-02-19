package com.furkanasikdev.courier.tracking.domain.courier;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "couriers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {

	@Id
	@Column(name = "courier_id", nullable = false)
	private String courierId;

	@Embedded
	@AttributeOverride(name = "km", column = @Column(name = "total_distance", nullable = false))
	@Builder.Default
	private Distance totalDistance = Distance.of(0.0);

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public void addDistance(Distance distance) {
		this.totalDistance = Distance.of(this.totalDistance.km() + distance.km());
	}

	public static Courier create(String courierId) {
		return Courier.builder()
				.courierId(courierId)
				.build();
	}
}
