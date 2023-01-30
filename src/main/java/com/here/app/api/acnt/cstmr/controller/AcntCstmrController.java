package com.here.app.api.acnt.cstmr.controller;

import com.here.app.api.acnt.cstmr.model.AcntCstmrRqModel;
import com.here.app.api.comn.response.BasicResponse;
import com.here.app.api.comn.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

        AcntCstmrRqModel result;

        try{
            result = service.signup(rq);
        }catch (Exception e){

        }
        return ResponseEntity.ok().body(new SuccessResponse<AcntCstmrRqModel>(result));
    }
}
