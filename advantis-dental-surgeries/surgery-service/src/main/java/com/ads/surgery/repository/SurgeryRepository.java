package com.ads.surgery.repository;

import com.ads.surgery.entity.Surgery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurgeryRepository extends MongoRepository<Surgery, String> {
}

