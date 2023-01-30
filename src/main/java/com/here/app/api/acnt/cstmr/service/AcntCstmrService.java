package com.here.app.api.acnt.cstmr.service;

import com.here.app.api.acnt.cstmr.model.AcntCstmrRqModel;
import com.here.app.api.acnt.cstmr.model.AcntCstmrRsModel;
import com.here.app.comn.utils.DateUtils;
import com.here.app.comn.utils.EncryptUtils;
import com.here.app.jpa.entity.ClubCstmrBas;
import com.here.app.jpa.entity.ClubCstmrDtl;
import com.here.app.jpa.repository.ClubCstmrBasRepository;
import com.here.app.jpa.repository.ClubCstmrDtlRepository;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
public class AcntCstmrService {
    @Autowired
    private ClubCstmrDtlRepository clubCstmrDtlRepository;
    @Autowired
    private ClubCstmrBasRepository clubCstmrBasRepository;


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public AcntCstmrRqModel signup(AcntCstmrRqModel rq) throws Exception{

        try{
            rq.setHpno(rq.getHpno().replaceAll("-",""));
            rq.setUserPswd(EncryptUtils.sha256Encrypt(rq.getUserPswd()));
            rq.setHpno(EncryptUtils.encrypt(rq.getHpno()));
            rq.setEmail(EncryptUtils.encrypt(rq.getEmail()));
            rq.setBrthdyDate(rq.getBrthdyDate().replaceAll("-",""));
        }catch (Exception e){

        }
    return rq;
    }





    public ClubCstmrBas savePtyCstmrBas(AcntCstmrRqModel rq) throws Exception{


        ClubCstmrBas basEntity = new ClubCstmrBas();
        basEntity.setCstmrDivCd(rq.getCstrmDivCd());
        basEntity.setCstmrStatusCd(rq.getCstrmStatusCd());
        basEntity.setUserId(rq.getUserId());
        basEntity.setAuthId(rq.getAuthId());
        basEntity.setUserPswd(rq.getUserPswd());
        basEntity.setJoinDt(DateUtils.nowDate());

        return clubCstmrBasRepository.save(basEntity);

    }

    public ClubCstmrDtl savePtyCstmrDtl(AcntCstmrRqModel rq , ClubCstmrBas basEntity) throws Exception{
        ClubCstmrDtl dtlEntity = new ClubCstmrDtl();
        dtlEntity.setCstmrSno(basEntity.getCstmrSno());
        dtlEntity.setGenderCd(rq.getGenderCd());
        dtlEntity.setMemberName(rq.getMemberName());
        dtlEntity.setBrthdyDate(DateUtils.stringToDate(rq.getBrthdyDate()));
        dtlEntity.setEmail(rq.getEmail());
        dtlEntity.setHpno(rq.getHpno());
        dtlEntity.setUpdateDt(DateUtils.nowDate());
        dtlEntity.setUpdateUserId(rq.getUserId());

        return clubCstmrDtlRepository.save(dtlEntity);

    }
}
