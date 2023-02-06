package com.here.app.api.acnt.jwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/api/acnt/jwt",produces = {MediaType.APPLICATION_JSON_VALUE})
public class JwtAuthenticationController {

}
