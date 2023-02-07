package com.here.app.api.acnt.jwt.service;

import com.here.app.api.acnt.jwt.model.JwtRqModel;
import com.here.app.api.acnt.jwt.model.JwtRsModel;
import com.here.app.api.acnt.jwt.model.JwtUserModel;
import com.here.app.api.acnt.jwt.utils.JwtTokenUtil;
import com.here.app.comn.code.ErrorCode;
import com.here.app.comn.exception.CustomException;
import com.here.app.comn.utils.DateUtils;
import com.here.app.comn.utils.EncryptUtils;
import com.here.app.comn.utils.HttpUtils;
import com.here.app.jpa.entity.ClubCstmrBas;
import com.here.app.jpa.entity.ClubCstmrConectHist;
import com.here.app.jpa.repository.ClubCstmrBasRepository;
import com.here.app.jpa.repository.ClubCstmrConectHistRepository;
import com.here.app.jpa.repository.ClubCstmrQueryRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class JwtService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private ClubCstmrBasRepository clubCstmrBasRepository;

    @Autowired
    private ClubCstmrConectHistRepository clubCstmrConectHistRepository;

    @Autowired
    private ClubCstmrQueryRepository query;


    public Map<String, Object> loginProc(JwtRqModel rq) throws Exception {

        int loginError = 1; // -100 : 아이디/비밀번호가 없습니다 , -101 : 계정정보를 찾을수 없습니다 , -102 : 비밀번호가 잘못 되었습니다 , -103 : 계정을 사용할수 없습니다.

        Map<String , Object> resultMap = new HashMap<String , Object>();
        //입력값 검증 처리
        if(StringUtils.isEmpty(rq.getUserId()) || StringUtils.isEmpty(rq.getUserPswd())) {
            loginError = -100;
        }

        JwtUserModel userDetails = (JwtUserModel)userDetailsService
                .loadUserByUsername(rq.getUserId());


        //계정이 없는경우
        if(userDetails == null) {
            loginError = -101;
        }else{
            String password = EncryptUtils.sha256Encrypt(rq.getUserPswd());

            //비밀번호 검증 처리
            if (!userDetails.getPassword().equals(password)) {
                loginError = -102;

            }

            //계정 검증로직
            if(!userDetails.isAccountNonLocked() || !userDetails.isAccountNonExpired() || !userDetails.isEnabled() || !userDetails.isCredentialsNonExpired()) {
                loginError = -103;

            }
        }


        if(loginError < 0) {

            String errorMessage = "";
            if(loginError == -100) {
                errorMessage = "Please parameter Check";
            }else if(loginError == -101) {
                errorMessage = "Account not found";

            }else if(loginError == -102) {
                errorMessage = "Password does not match";
            }else if(loginError == -103) {
                errorMessage = "Account is unavailable";
            }
            //실패 이력 저장
            //cstmrSno , String loginYn , String errorCode
            if(userDetails != null) {
                this.historySave(userDetails.getCstmrSno(), "N", loginError+"");
            }

            resultMap.put("loginError", loginError);
            resultMap.put("errorMessage", errorMessage);

            return resultMap;

        }else {

            String accessToken = jwtTokenUtil.generateToken(userDetails);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

            JwtRsModel result = new JwtRsModel();
            result.setAccessToken(accessToken);
            result.setRefreshToken(refreshToken);
            result.setAuth(userDetails.getAuth());
            result.setUserId(userDetails.getUserId());
            result.setCstmrSno(userDetails.getCstmrSno());
            //토큰 저장 처리
            log.debug("========= refresh>>>>"  + refreshToken);
            this.refreshTokenSave(userDetails.getCstmrSno(), refreshToken);

            //성공이력 저장
            this.historySave(userDetails.getCstmrSno(), "Y", loginError+"");

            resultMap.put("loginError", loginError);
            resultMap.put("errorMessage", "");
            resultMap.put("result", result);

            return resultMap;
        }
    }
    /**
     * 로그인 이력 저장
     * @param cstmrSno
     * @param loginYn
     * @param errorCode
     * @return
     * @throws Exception
     */
    public ClubCstmrConectHist historySave(int cstmrSno , String loginYn , String errorCode) throws Exception{
        String conectIp = HttpUtils.getRequestIp();

        ClubCstmrConectHist entity = new ClubCstmrConectHist();
        entity.setCstmrSno(cstmrSno);
        entity.setConectSucesYn(loginYn);
        entity.setConectErrorCd(errorCode);
        entity.setConectIp(conectIp);
        entity.setConectDt(DateUtils.nowDate());
        return clubCstmrConectHistRepository.save(entity);
    }
    /**
     * refresh Token 저장 처리
     * @param cstmrSno
     * @param refreshToken
     * @return
     * @throws Exception
     */
    public ClubCstmrBas refreshTokenSave(int cstmrSno , String refreshToken) throws Exception{

        Optional<ClubCstmrBas> optional = clubCstmrBasRepository.findById(cstmrSno);

        if (!optional.isPresent()) {
            throw new CustomException(ErrorCode.DATA_NOTFIND);
        }

        ClubCstmrBas entity = optional.get();
        entity.setRfrshToken(refreshToken);

        return clubCstmrBasRepository.save(entity);
    }
    /**
     * token 만료시 refresh 토큰으로 재검색
     * @param cstmrSno
     * @param refreshToken
     * @return
     */
    public JwtRsModel findRefreshtoken(int cstmrSno , String refreshToken) {
        JwtUserModel userDetails = query.findRefreshtoken(cstmrSno, refreshToken);

        if(userDetails == null) {
            return null;
        }

        String accessToken = jwtTokenUtil.generateToken(userDetails);

        JwtRsModel result = new JwtRsModel();
        result.setAccessToken(accessToken);
        result.setRefreshToken(refreshToken);
        result.setAuth(userDetails.getAuth());
        result.setUserId(userDetails.getUserId());
        result.setCstmrSno(userDetails.getCstmrSno());

        return result;
    }

    public ClubCstmrBas logoutProcess(int cstmrSno) throws Exception{

        Optional<ClubCstmrBas> optional = clubCstmrBasRepository.findById(cstmrSno);
        if (optional.isPresent()) {
            ClubCstmrBas entity = optional.get();
            entity.setRfrshToken("");
            return clubCstmrBasRepository.save(entity);
        }else {
            return null;
        }

    }
}
