package com.here.app.api.acnt.cstmr.controller;

import com.here.app.api.acnt.cstmr.model.AcntCstmrRqModel;
import com.here.app.api.acnt.cstmr.model.AcntCstmrRsModel;
import com.here.app.api.acnt.cstmr.service.AcntCstmrService;
import com.here.app.api.comn.response.BasicResponse;
import com.here.app.api.comn.response.ErrorResponse;
import com.here.app.api.comn.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/acnt/cstmr", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AcntCstmrController {

    @Autowired
    private final AcntCstmrService service;

    @PostMapping(value = "/signup")
    public ResponseEntity<? extends BasicResponse> signup (@RequestBody AcntCstmrRqModel rq){

        AcntCstmrRsModel result;
        log.info("rq >>>>>>",rq.getUserId());
        try{
            result = service.signup(rq);
        }catch (Exception e){
            log.error("IGNORE : {} ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("SERVER ERROR", "-1"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<AcntCstmrRsModel>(result));
    }
}
