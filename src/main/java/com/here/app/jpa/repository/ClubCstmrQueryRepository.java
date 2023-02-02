package com.here.app.jpa.repository;

import com.here.app.api.acnt.jwt.model.JwtUserModel;
import com.here.app.jpa.entity.ClubCstmrBas;
import com.here.app.jpa.entity.QClubCstmrBas;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public JwtUserModel findRefreshtoken(int cstmrSno , String refreshToken) {
        QClubCstmrBas basEntity = QClubCstmrBas.clubCstmrBas;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(basEntity.cstmrSno.eq(cstmrSno));
        builder.and(basEntity.rfrshToken.eq(refreshToken));

        ClubCstmrBas entity = query.select(basEntity)
                .from(basEntity)
                .where(builder)
                .fetchFirst();
        if(entity != null) {
            JwtUserModel model = new JwtUserModel();
            model.setAuth(entity.getAuthId());
            model.setUserId(entity.getUserId());
            model.setCstmrSno(entity.getCstmrSno());
            model.setCstmrStatusCd(entity.getCstmrStatusCd());
            model.setUserPswd(entity.getUserPswd());

            return model;

        }else {
            return null;
        }
    }

    public JwtUserModel findUserPassword(String userId){
        QClubCstmrBas basEntity = QClubCstmrBas.clubCstmrBas;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(basEntity.userId.eq(userId));

        ClubCstmrBas entity = query.select(basEntity)
                .from(basEntity)
                .where(builder)
                .fetchFirst();

        if(entity != null){
            JwtUserModel model = new JwtUserModel();
            model.setAuth(entity.getAuthId());
            model.setUserId(entity.getUserId());
            model.setCstmrSno(entity.getCstmrSno());
            model.setCstmrStatusCd(entity.getCstmrStatusCd());
            model.setUserPswd(entity.getUserPswd());

            return model;
        }
        else {
            return null;
        }
    }

}
