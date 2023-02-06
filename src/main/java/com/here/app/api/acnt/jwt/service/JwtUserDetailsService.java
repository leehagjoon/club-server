package com.here.app.api.acnt.jwt.service;


import com.here.app.api.acnt.jwt.model.JwtUserModel;
import com.here.app.jpa.repository.ClubCstmrQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ClubCstmrQueryRepository query;

    @Override
    public JwtUserModel loadUserByUsername(String username) {

        JwtUserModel model = query.findUserPassword(username);
        if(model == null) {
            return null;
        }else {
            return model;
        }
    }

}
