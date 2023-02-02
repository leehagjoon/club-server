package com.here.app.api.acnt.jwt.service;

import com.here.app.api.acnt.jwt.model.JwtUserModel;
import com.here.app.comn.utils.JsonUtils;
import com.here.app.jpa.repository.ClubCstmrBasRepository;
import com.here.app.jpa.repository.ClubCstmrQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private ClubCstmrQueryRepository query ;

	@Autowired
	private ClubCstmrBasRepository repository;



	@Override
	public JwtUserModel loadUserByUsername(String username){


		JwtUserModel model = query.findUserPassword(username);
//		log.debug("jwtUser>>>>" + JsonUtils.toJson(model));
		if(model == null) {
			return null;
		}else {
			return model;
		}

	}
}
