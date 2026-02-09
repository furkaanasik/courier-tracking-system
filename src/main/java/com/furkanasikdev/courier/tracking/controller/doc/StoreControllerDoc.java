package com.furkanasikdev.courier.tracking.controller.doc;

import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Store", description = "Store entry management")
public interface StoreControllerDoc {

	@Operation(summary = "Get store entries", description = "Get all courier entry logs for a specific store")
	ResponseEntity<List<StoreEntry>> getStoreEntries(
			@Parameter(description = "Store name", example = "Ataşehir MMM Migros") @PathVariable String storeName);
}