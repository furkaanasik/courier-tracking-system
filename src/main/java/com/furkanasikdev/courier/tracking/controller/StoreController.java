package com.furkanasikdev.courier.tracking.controller;

import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import com.furkanasikdev.courier.tracking.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

	private final StoreService storeService;

	@GetMapping("/{storeName}/entries")
	public ResponseEntity<List<StoreEntry>> getStoreEntries(@PathVariable String storeName) {
		return ResponseEntity.ok(this.storeService.getStoreEntries(storeName));
	}
}