package com.here.app.jpa.repository;

import com.here.app.jpa.entity.ClubCstmrDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubCstmrDtlRepository extends JpaRepository<ClubCstmrDtl, Integer>{
	
}
