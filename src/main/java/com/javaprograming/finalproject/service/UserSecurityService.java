package com.javaprograming.finalproject.service;

import com.javaprograming.finalproject.model.UserModel;
import com.javaprograming.finalproject.model.UserRepository;
import com.javaprograming.finalproject.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> _user = this.userRepository.findByUsername(username);
        if(_user.isEmpty()){
            throw new UsernameNotFoundException(username + " not found");
        }
        UserModel user = _user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(user.getUsername())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
