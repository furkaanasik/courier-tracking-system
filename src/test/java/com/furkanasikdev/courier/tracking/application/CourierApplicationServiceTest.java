package com.furkanasikdev.courier.tracking.application;

import com.furkanasikdev.courier.tracking.domain.courier.Courier;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.domain.courier.repository.CourierRepository;
import com.furkanasikdev.courier.tracking.domain.courier.Distance;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
import com.furkanasikdev.courier.tracking.shared.exception.CourierNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierApplicationServiceTest {

	@Mock
	private CourierRepository courierRepository;

	@InjectMocks
	private CourierApplicationService courierApplicationService;

	private Courier testCourier;
	private CourierLocation testLocation;

	@BeforeEach
	void init() {
		this.testCourier = Courier.builder()
				.courierId("CRR-123")
				.totalDistance(Distance.of(10.0))
				.build();

		this.testLocation = CourierLocation.builder()
				.courierId("CRR-123")
				.location(new Location(40.9923307, 29.1244229))
				.timestamp(LocalDateTime.of(2024, 2, 8, 14, 30, 0))
				.build();
	}

	@Test
	void getOrCreateExistCourier() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));

		Courier result = this.courierApplicationService.getOrCreate("CRR-123");

		assertThat(result.getCourierId()).isEqualTo("CRR-123");
		assertThat(result.getTotalDistance()).isEqualTo(Distance.of(10.0));
		verify(this.courierRepository, never()).save(any());
	}

	@Test
	void getOrCreateSaveCourier() {
		when(this.courierRepository.findById("CRR-NEW")).thenReturn(Optional.empty());
		when(this.courierRepository.save(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Courier result = this.courierApplicationService.getOrCreate("CRR-NEW");

		assertThat(result.getCourierId()).isEqualTo("CRR-NEW");
		assertThat(result.getTotalDistance()).isEqualTo(Distance.of(0.0));
		verify(this.courierRepository).save(any(Courier.class));
	}

	@Test
	void addDistance() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));
		when(this.courierRepository.save(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

		this.courierApplicationService.addDistance("CRR-123", 3.5);

		assertThat(this.testCourier.getTotalDistance()).isEqualTo(Distance.of(13.5));
		verify(this.courierRepository).save(this.testCourier);
	}

	@Test
	void getTotalDistance() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));

		double result = this.courierApplicationService.getTotalDistance("CRR-123");

		assertThat(result).isEqualTo(10.0);
	}

	@Test
	void getTotalDistanceCourierNotFound() {
		when(this.courierRepository.findById("INVALID")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> this.courierApplicationService.getTotalDistance("INVALID"))
				.isInstanceOf(CourierNotFoundException.class)
				.hasMessageContaining("INVALID");
	}

	@Test
	void getLocations() {
		when(this.courierRepository.existsById("CRR-123")).thenReturn(true);
		when(this.courierRepository.findLocations("CRR-123")).thenReturn(List.of(this.testLocation));

		List<CourierLocation> result = this.courierApplicationService.getLocations("CRR-123");

		assertThat(result).hasSize(1);
		assertThat(result.getFirst().getLocation().lat()).isEqualTo(40.9923307);
	}

	@Test
	void getLocationsCourierNotFound() {
		when(this.courierRepository.existsById("INVALID")).thenReturn(false);

		assertThatThrownBy(() -> this.courierApplicationService.getLocations("INVALID"))
				.isInstanceOf(CourierNotFoundException.class);
	}
}
