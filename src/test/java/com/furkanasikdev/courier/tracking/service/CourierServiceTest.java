package com.furkanasikdev.courier.tracking.service;

import com.furkanasikdev.courier.tracking.entity.Courier;
import com.furkanasikdev.courier.tracking.entity.CourierLocation;
import com.furkanasikdev.courier.tracking.exception.CourierNotFoundException;
import com.furkanasikdev.courier.tracking.repository.CourierLocationRepository;
import com.furkanasikdev.courier.tracking.repository.CourierRepository;
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
class CourierServiceTest {

	@Mock
	private CourierRepository courierRepository;

	@Mock
	private CourierLocationRepository courierLocationRepository;

	@InjectMocks
	private CourierService courierService;

	private Courier testCourier;
	private CourierLocation testLocation;

	@BeforeEach
	void init() {
		this.testCourier = Courier.builder()
				.courierId("CRR-123")
				.totalDistance(10.0)
				.build();

		this.testLocation = CourierLocation.builder()
				.courierId("CRR-123")
				.latitude(40.9923307)
				.longitude(29.1244229)
				.timestamp(LocalDateTime.of(2024, 2, 8, 14, 30, 0))
				.build();
	}

	@Test
	void getOrCreateExistCourier() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));

		Courier result = this.courierService.getOrCreate("CRR-123");

		assertThat(result.getCourierId()).isEqualTo("CRR-123");
		assertThat(result.getTotalDistance()).isEqualTo(10.0);
		verify(this.courierRepository, never()).save(any());
	}

	@Test
	void getOrCreateSaveCourier() {
		when(this.courierRepository.findById("CRR-NEW")).thenReturn(Optional.empty());
		when(this.courierRepository.save(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Courier result = this.courierService.getOrCreate("CRR-NEW");

		assertThat(result.getCourierId()).isEqualTo("CRR-NEW");
		assertThat(result.getTotalDistance()).isEqualTo(0.0);
		verify(this.courierRepository).save(any(Courier.class));
	}

	@Test
	void addDistance() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));
		when(this.courierRepository.save(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

		this.courierService.addDistance("CRR-123", 3.5);

		assertThat(this.testCourier.getTotalDistance()).isEqualTo(13.5);
		verify(this.courierRepository).save(this.testCourier);
	}

	@Test
	void getTotalDistance() {
		when(this.courierRepository.findById("CRR-123")).thenReturn(Optional.of(this.testCourier));

		double result = this.courierService.getTotalDistance("CRR-123");

		assertThat(result).isEqualTo(10.0);
	}

	@Test
	void getTotalDistanceCourierNotFound() {
		when(this.courierRepository.findById("INVALID")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> this.courierService.getTotalDistance("INVALID"))
				.isInstanceOf(CourierNotFoundException.class)
				.hasMessageContaining("INVALID");
	}

	@Test
	void getLocations() {
		when(this.courierRepository.existsById("CRR-123")).thenReturn(true);
		when(this.courierLocationRepository.findByCourierIdOrderByTimestampAsc("CRR-123"))
				.thenReturn(List.of(this.testLocation));

		List<CourierLocation> result = this.courierService.getLocations("CRR-123");

		assertThat(result).hasSize(1);
		assertThat(result.getFirst().getLatitude()).isEqualTo(40.9923307);
	}

	@Test
	void getLocationsCourierNotFound() {
		when(this.courierRepository.existsById("INVALID")).thenReturn(false);

		assertThatThrownBy(() -> this.courierService.getLocations("INVALID"))
				.isInstanceOf(CourierNotFoundException.class);
	}
}