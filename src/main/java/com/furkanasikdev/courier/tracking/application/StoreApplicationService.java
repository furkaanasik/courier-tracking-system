package com.furkanasikdev.courier.tracking.application;

import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;
import com.furkanasikdev.courier.tracking.domain.store.repository.StoreEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreApplicationService {

	private final StoreEntryRepository storeEntryRepository;

	public List<StoreEntry> getEntriesByStoreName(String storeName) {
		return this.storeEntryRepository.findByStoreNameOrderByEntryTimeDesc(storeName);
	}

	public List<StoreEntry> getEntriesByCourierId(String courierId) {
		return this.storeEntryRepository.findByCourierIdOrderByEntryTimeDesc(courierId);
	}

	public List<StoreEntry> getStoreEntries(String storeName) {
		return this.storeEntryRepository.findByStoreNameOrderByEntryTimeDesc(storeName);
	}
}
