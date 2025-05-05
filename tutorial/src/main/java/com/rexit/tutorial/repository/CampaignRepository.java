package com.rexit.tutorial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rexit.tutorial.model.Campaign;

import jakarta.persistence.LockModeType;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    boolean existsByCampaignCode(String campaignCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Campaign c WHERE c.id = :id")
    Optional<Campaign> findByIdWithLock(@Param("id") Long id);
}
