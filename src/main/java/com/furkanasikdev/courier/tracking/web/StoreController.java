package com.furkanasikdev.courier.tracking.web;

import com.furkanasikdev.courier.tracking.application.StoreApplicationService;
import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;
import com.furkanasikdev.courier.tracking.web.doc.StoreControllerDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController implements StoreControllerDoc {

	private final StoreApplicationService storeApplicationService;

	@Override
	@GetMapping("/{storeName}/entries")
	public ResponseEntity<List<StoreEntry>> getStoreEntries(@PathVariable String storeName) {
		return ResponseEntity.ok(this.storeApplicationService.getStoreEntries(storeName));
	}
}
