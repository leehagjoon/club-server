package com.here.app.api.acnt.jwt.controller;

import com.here.app.api.acnt.jwt.model.JwtRqModel;
import com.here.app.api.acnt.jwt.model.JwtRsModel;
import com.here.app.api.acnt.jwt.service.JwtService;
import com.here.app.api.comn.response.BasicResponse;
import com.here.app.api.comn.response.ErrorResponse;
import com.here.app.api.comn.response.SuccessResponse;
import com.here.app.comn.code.RSErrorCode;
import com.here.app.jpa.entity.ClubCstmrBas;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/api/acnt/jwt",produces = {MediaType.APPLICATION_JSON_VALUE})
public class JwtAuthenticationController {

    @Autowired
    private JwtService service;

    @PostMapping(value = "/login")
    public ResponseEntity<? extends BasicResponse> loginToken(@RequestBody JwtRqModel rq) throws Exception{
        Map<String, Object> resultMap = service.loginProc(rq);

        int loginError = (int) resultMap.get("loginError");

        if(loginError < 0) {

            String errorMessage = (String) resultMap.get("errorMessage");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(errorMessage, loginError + ""));
        }else {
            JwtRsModel result = (JwtRsModel) resultMap.get("result");
            return ResponseEntity.ok().body(new SuccessResponse<JwtRsModel>(result));
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<? extends BasicResponse> refresh(@RequestBody Map body) throws Exception{


        //입력값 검증
        if(body.get("cstmrSno") == null || body.get("refreshToken") == null || !(body.get("cstmrSno") instanceof Integer)) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(RSErrorCode.ER_PARAM));
        }

        int cstmrSno = (int)body.get("cstmrSno");
        String refreshToken = (String)body.get("refreshToken");



        JwtRsModel result = service.findRefreshtoken(cstmrSno, refreshToken);
//		JwtRsModel result = null;

        if(result ==null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(RSErrorCode.DATA_NOTFOUNT));
        }
        return ResponseEntity.ok().body(new SuccessResponse<JwtRsModel>(result));
    }

    @GetMapping(value = "/logout/{cstmrSno}")
    public ResponseEntity<? extends BasicResponse> logout(@PathVariable Integer cstmrSno) throws Exception{

        ClubCstmrBas bas = service.logoutProcess(cstmrSno);

        if(bas == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(RSErrorCode.DATA_NOTFOUNT));
        }

        return ResponseEntity.ok().body(new SuccessResponse<RSErrorCode>(RSErrorCode.SUCCESS));
    }

}
