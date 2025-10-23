    package com.ads.surgery.controller;

    import com.ads.surgery.entity.Surgery;
    import com.ads.surgery.service.SurgeryService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/v1/auth/surgeries")
    public class SurgeryController {

        @Autowired
        private SurgeryService service;

        @PostMapping
        public ResponseEntity<Surgery> create(@RequestBody Surgery s) {
            return ResponseEntity.ok(service.create(s));
        }

        @GetMapping
        public ResponseEntity<List<Surgery>> all() {
            return ResponseEntity.ok(service.getAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Surgery> get(@PathVariable String id) {
            return service.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}/status")
        public ResponseEntity<Surgery> updateStatus(@PathVariable String id, @RequestParam String status) {
            return ResponseEntity.ok(service.updateStatus(id, status));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable String id) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
