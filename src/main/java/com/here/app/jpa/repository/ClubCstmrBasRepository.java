package com.here.app.jpa.repository;

import com.here.app.api.acnt.jwt.model.JwtUserModel;
import com.here.app.jpa.entity.ClubCstmrBas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubCstmrBasRepository extends JpaRepository<ClubCstmrBas,Integer> {

    Optional<JwtUserModel> findByUserId(String userId);

    @Query("select c from ClubCstmrBas c " + "where c.cstmrSno = :cstmrSno")
    ClubCstmrBas findByUserID(@Param("cstmrSno") int cstmrSno);
}
