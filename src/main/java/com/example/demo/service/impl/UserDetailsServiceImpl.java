package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.service.UserService;

/* ユーザー情報の検索 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /* ユーザー情報取得 */
        AppUser signInUser = service.getSigninUser(username);

        /* ユーザーが存在しない場合 */
        if(signInUser == null) {
            throw new UsernameNotFoundException("user not found");
        }

        /* 権限List作成 */
        GrantedAuthority authority = new SimpleGrantedAuthority(signInUser.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        /* UserDetails生成(ユーザーID、パスワード、権限リストを必須で設定) */
        UserDetails userDetails = (UserDetails) new User(signInUser.getMailAddress(), signInUser.getPassword(), authorities);
        
        /* user情報を返す */
        return userDetails;
    }
}
