package com.here.app.jpa.repository;

import com.here.app.jpa.entity.ClubCstmrConectHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubCstmrConectHistRepository extends JpaRepository<ClubCstmrConectHist,Integer> {
}
