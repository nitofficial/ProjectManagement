package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.PendingRequest;

public interface PendingRequestRepository extends MongoRepository<PendingRequest, String>{
	boolean existsByRequestid(String requestid);
}
