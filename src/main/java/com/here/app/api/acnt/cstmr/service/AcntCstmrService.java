package com.here.app.api.acnt.cstmr.service;

import com.here.app.api.acnt.cstmr.model.AcntCstmrRqModel;
import com.here.app.api.acnt.cstmr.model.AcntCstmrRsModel;
import com.here.app.comn.code.ErrorCode;
import com.here.app.comn.exception.CustomException;
import com.here.app.comn.utils.DateUtils;
import com.here.app.comn.utils.EncryptUtils;
import com.here.app.jpa.entity.ClubCstmrBas;
import com.here.app.jpa.entity.ClubCstmrDtl;
import com.here.app.jpa.repository.ClubCstmrBasRepository;
import com.here.app.jpa.repository.ClubCstmrDtlRepository;
import com.here.app.jpa.repository.ClubCstmrQueryRepository;
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

    @Autowired
    private ClubCstmrQueryRepository query;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public AcntCstmrRsModel signup(AcntCstmrRqModel rq) throws Exception{

        AcntCstmrRsModel rs = new AcntCstmrRsModel();
        boolean isUserIDFind = false;
        try{
            isUserIDFind = query.findCstmrByUserId(rq.getUserId());
            if(isUserIDFind){
                rs.setErrCode(-1);
                return rs;
            }

            rq.setHpno(rq.getHpno().replaceAll("-",""));
            // 처리 1. 민감정보 암호화 처리 (비밀번호, 이름 , 휴대폰번호, 이메일)
            rq.setUserPswd(EncryptUtils.sha256Encrypt(rq.getUserPswd()));
            rq.setHpno(EncryptUtils.encrypt(rq.getHpno()));
            rq.setEmail(EncryptUtils.encrypt(rq.getEmail()));
//            rq.setBrthdyDate(rq.getBrthdyDate().replaceAll("-",""));

            ClubCstmrBas basEntity = this.savePtyCstmrBas(rq);
            if(basEntity == null){
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            this.savePtyCstmrDtl(rq,basEntity);

            if(basEntity != null){
                rs.setErrCode(1);
                rs.setLoginId(basEntity.getUserId());
            }
        }catch (Exception e){
            log.error("IGNORE : {} ", e);
            rs.setErrCode(-2);
            return rs;

        }
    return rs;
    }





    public ClubCstmrBas savePtyCstmrBas(AcntCstmrRqModel rq) throws Exception{


        ClubCstmrBas basEntity = new ClubCstmrBas();
        basEntity.setCstmrStatusCd(rq.getCstrmrStatusCd());
        basEntity.setUserId(rq.getUserId());
        basEntity.setAuthId(rq.getAuthId());
        basEntity.setUserPswd(rq.getUserPswd());
        basEntity.setJoinDt(DateUtils.nowDate());

        return clubCstmrBasRepository.save(basEntity);

    }

    public ClubCstmrDtl savePtyCstmrDtl(AcntCstmrRqModel rq , ClubCstmrBas basEntity) throws Exception{
        ClubCstmrDtl dtlEntity = new ClubCstmrDtl();
        dtlEntity.setPosition(rq.getPosition());
        dtlEntity.setCstmrSno(basEntity.getCstmrSno());
        dtlEntity.setGenderCd(rq.getGenderCd());
        dtlEntity.setMemberName(rq.getMemberName());
//        dtlEntity.setBrthdyDate(DateUtils.stringToDate(rq.getBrthdyDate()));
        dtlEntity.setEmail(rq.getEmail());
        dtlEntity.setHpno(rq.getHpno());
        dtlEntity.setUpdateDt(DateUtils.nowDate());
        dtlEntity.setUpdateUserId(rq.getUserId());
        dtlEntity.setAddr(rq.getAddr());

        return clubCstmrDtlRepository.save(dtlEntity);

    }
}
