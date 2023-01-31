package com.here.app.jpa.repository;

import com.here.app.jpa.entity.ClubCstmrBas;
import com.here.app.jpa.entity.QClubCstmrBas;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ClubCstmrQueryRepository {

    private final JPAQueryFactory query;

    public boolean findCstmrByUserId(String userId){

        boolean result = false;
        QClubCstmrBas basEntity = QClubCstmrBas.clubCstmrBas;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(basEntity.cstmrStatusCd.eq("A"));
        builder.and(basEntity.userId.eq(userId));

        ClubCstmrBas entity = query.select(basEntity)
                .from(basEntity)
                .where(builder)
                .fetchFirst();
        if(entity != null){
            result = true;
        }
        return result;
    }

}
