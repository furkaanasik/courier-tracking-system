package com.furkanasikdev.courier.tracking.application;

import com.furkanasikdev.courier.tracking.domain.courier.Courier;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.infrastructure.persistence.JpaCourierLocationRepository;
import com.furkanasikdev.courier.tracking.shared.event.location.LocationReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingApplicationServiceTest {

	@Mock
	private CourierApplicationService courierApplicationService;

	@Mock
	private JpaCourierLocationRepository courierLocationRepository;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private TrackingApplicationService trackingApplicationService;

	private Courier testCourier;
	private LocalDateTime testTimestamp;

	@BeforeEach
	void init() {
		this.testCourier = Courier.builder()
				.courierId("CRR-123")
				.build();

		this.testTimestamp = LocalDateTime.of(2024, 2, 8, 14, 30, 0);
	}

	@Test
	void processLocationCreateCourierSaveLocationPublishEvent() {
		when(this.courierApplicationService.getOrCreate("CRR-123")).thenReturn(this.testCourier);
		when(this.courierLocationRepository.save(any(CourierLocation.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		this.trackingApplicationService.processLocation("CRR-123", 40.9923307, 29.1244229, this.testTimestamp);

		verify(this.courierApplicationService).getOrCreate("CRR-123");
		verify(this.courierLocationRepository).save(any(CourierLocation.class));
		verify(this.eventPublisher).publishEvent(any(LocationReceivedEvent.class));
	}

	@Test
	void processLocationCreateCourierSaveLocation() {
		when(this.courierApplicationService.getOrCreate("CRR-123")).thenReturn(this.testCourier);
		when(this.courierLocationRepository.save(any(CourierLocation.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		this.trackingApplicationService.processLocation("CRR-123", 40.9923307, 29.1244229, this.testTimestamp);

		ArgumentCaptor<CourierLocation> captor = ArgumentCaptor.forClass(CourierLocation.class);
		verify(this.courierLocationRepository).save(captor.capture());

		CourierLocation saved = captor.getValue();
		assertThat(saved.getCourierId()).isEqualTo("CRR-123");
		assertThat(saved.getLocation().lat()).isEqualTo(40.9923307);
		assertThat(saved.getLocation().lng()).isEqualTo(29.1244229);
		assertThat(saved.getTimestamp()).isEqualTo(this.testTimestamp);
	}

	@Test
	void processLocationCreateCourierPublishEvent() {
		when(this.courierApplicationService.getOrCreate("CRR-123")).thenReturn(this.testCourier);
		when(this.courierLocationRepository.save(any(CourierLocation.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		this.trackingApplicationService.processLocation("CRR-123", 40.9923307, 29.1244229, this.testTimestamp);

		ArgumentCaptor<LocationReceivedEvent> captor = ArgumentCaptor.forClass(LocationReceivedEvent.class);
		verify(this.eventPublisher).publishEvent(captor.capture());

		LocationReceivedEvent event = captor.getValue();
		assertThat(event.getCourierId()).isEqualTo("CRR-123");
		assertThat(event.getLocation().lat()).isEqualTo(40.9923307);
		assertThat(event.getLocation().lng()).isEqualTo(29.1244229);
		assertThat(event.getEntryTime()).isEqualTo(this.testTimestamp);
	}
}
