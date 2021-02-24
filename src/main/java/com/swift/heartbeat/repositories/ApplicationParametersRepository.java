package com.swift.heartbeat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swift.heartbeat.entities.ApplicationParametersEntity;

@Repository
public interface ApplicationParametersRepository extends JpaRepository<ApplicationParametersEntity, Integer> {

}
