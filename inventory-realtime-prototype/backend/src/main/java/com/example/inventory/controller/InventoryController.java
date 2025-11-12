package com.example.inventory.controller;

import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.InventoryEventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryRepository repo;
    private final InventoryEventProducer producer;

    public InventoryController(InventoryRepository repo, InventoryEventProducer producer) {
        this.repo = repo;
        this.producer = producer;
    }

    @GetMapping
    public List<InventoryItem> list() {
        return repo.findAll();
    }

    @PostMapping
    public InventoryItem create(@RequestBody InventoryItem item) {
        InventoryItem saved = repo.save(item);
        producer.publishInventoryUpdate(saved, "CREATE");
        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateQuantity(@PathVariable Long id, @RequestParam int qty) {
        Optional<InventoryItem> oi = repo.findById(id);
        if (oi.isEmpty()) return ResponseEntity.notFound().build();
        InventoryItem it = oi.get();
        it.setQuantity(qty);
        InventoryItem saved = repo.save(it);
        producer.publishInventoryUpdate(saved, "UPDATE");
        return ResponseEntity.ok(saved);
    }
}
