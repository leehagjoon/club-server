package com.here.app.jpa.repository;

import com.here.app.jpa.entity.ClubCstmrBas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubCstmrBasRepository extends JpaRepository<ClubCstmrBas,Integer> {
}
