package com.test.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.vo.Account;

@Service
public class AccountService implements UserDetailsService{

	private Map<String,Account> userMaps;
	
	public AccountService() {
		// TODO Auto-generated constructor stub
		userMaps = new HashMap<String, Account>();
		
		Account account = new Account();
		account.setUsername("AdminUser");
		account.setPassword("{noop}adminpass"); //��й�ȣ �Ϻ�ȣȭ�� �����Ҷ��� �տ� {noop}��� ���ڰ� �پ�� �Ѵ�.
		account.addRole("ADMIN");
		account.addRole("MEMBER");
		
		userMaps.put("AdminUser", account);
		
		account = new Account();
		account.setUsername("MemberUser");
		account.setPassword("{noop}memberpass"); //��й�ȣ �Ϻ�ȣȭ�� �����Ҷ��� �տ� {noop}��� ���ڰ� �پ�� �Ѵ�.
		account.addRole("MEMBER");
		
		
		userMaps.put("MemberUser", account);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		//DB�� ���� ����Ǿ��ִ� ������ �������� �ȴ�. �����̶� �ʿ��� �������� �ۼ� 
		Account account = userMaps.get(username);
		
		if(account == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return User.builder()
				.username(account.getUsername())
				.password(account.getPassword())
				.roles(account.getRole().toArray(new String[account.getRole().size()]) )
				.build();
	}
	

}
