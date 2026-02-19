package com.furkanasikdev.courier.tracking.domain.store.repository;

import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;

import java.util.List;

public interface StoreEntryRepository {

	StoreEntry save(StoreEntry entry);

	List<StoreEntry> findByCourierIdOrderByEntryTimeDesc(String courierId);

	List<StoreEntry> findByStoreNameOrderByEntryTimeDesc(String storeName);
}
