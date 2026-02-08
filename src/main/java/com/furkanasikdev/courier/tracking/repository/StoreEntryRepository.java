package com.furkanasikdev.courier.tracking.repository;

import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreEntryRepository extends JpaRepository<StoreEntry, Long> {

	List<StoreEntry> findByCourierIdOrderByEntryTimeDesc(String courierId);
	List<StoreEntry> findByStoreNameOrderByEntryTimeDesc(String storeName);

}