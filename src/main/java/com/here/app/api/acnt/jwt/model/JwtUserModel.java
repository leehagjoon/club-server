package com.here.app.api.acnt.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserModel implements UserDetails {

	  	@Id
	    @GeneratedValue(strategy= GenerationType.IDENTITY)
	  	@Column(name = "CSTMR_SNO", unique = true)
	    private Integer cstmrSno;

	    @Column(name = "USER_ID", unique = true)
	    private String userId;

	    @Column(name = "USER_PSWD")
	    private String userPswd;

	    @Column(name = "AUTH_ID")
	    private String auth;
	    
	    @Column(name = "CSTMR_STATUS_CD")
	    private String cstmrStatusCd;

	  
	    // 사용자의 권한을 콜렉션 형태로 반환
	    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        Set<GrantedAuthority> roles = new HashSet<>();
	        for (String role : auth.split(",")) {
	            roles.add(new SimpleGrantedAuthority(role));
	        }
	        return roles;
	    }

	    // 사용자의 id를 반환 (unique한 값)
	    @Override
	    public String getUsername() {
	        return userId;
	    }

	    // 사용자의 password를 반환
	    @Override
	    public String getPassword() {
	        return userPswd;
	    }

	    // 계정 만료 여부 반환
	    @Override
	    public boolean isAccountNonExpired() {
	        // 만료되었는지 확인하는 로직
	        return true; // true -> 만료되지 않았음
	    }

	    // 계정 잠금 여부 반환
	    @Override
	    public boolean isAccountNonLocked() {
	        // 계정 잠금되었는지 확인하는 로직
	    	if(cstmrStatusCd.equals("A"))
	    		return true;
	    	else 
	    		return false;
//	        return true; // true -> 잠금되지 않았음
	    }

	    // 패스워드의 만료 여부 반환
	    @Override
	    public boolean isCredentialsNonExpired() {
	        // 패스워드가 만료되었는지 확인하는 로직
	        return true; // true -> 만료되지 않았음
	    }

	    // 계정 사용 가능 여부 반환
	    @Override
	    public boolean isEnabled() {
	        // 계정이 사용 가능한지 확인하는 로직
	    	return true; // true -> 사용 가능
	    }
	    
}
