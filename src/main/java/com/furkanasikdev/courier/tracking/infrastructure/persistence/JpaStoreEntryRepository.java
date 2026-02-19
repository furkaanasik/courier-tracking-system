package com.furkanasikdev.courier.tracking.infrastructure.persistence;

import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;
import com.furkanasikdev.courier.tracking.domain.store.repository.StoreEntryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreEntryRepository extends JpaRepository<StoreEntry, Long>, StoreEntryRepository {
}
