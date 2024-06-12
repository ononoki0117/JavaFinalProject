package com.javaprograming.finalproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaprograming.finalproject.model.UserModel;
import com.javaprograming.finalproject.model.UserRepository_def;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository_def userRepository;

    public boolean findUser(String id) {
        return userRepository.findByLoginId(id) != null;
    }

    public String selectUser(String id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (findUser(id)) {
                log.info("[Service] user name : {} not exist!!", id);
                return String.format("user name : %s not exist!!", id);
            } else {
                return mapper.writeValueAsString(userRepository.findByLoginId(id));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public boolean authUser(String id, String password) {
        UserModel user = userRepository.findByLoginId(id);
        return user.getPassword().equals(password);
    }

    public void saveUser(String loginId, String password, String username, String phone, String address) {
        UserModel userModel = UserModel.builder()
                .loginId(loginId)
                .password(password)
                .username(username)
                .phone(phone)
                .address(address)
                .build();

        if (userRepository.findByLoginId(loginId) != null) {
            log.info("[Service] user name : {} already exist!!", loginId);
            userModel.setId(userRepository.findByLoginId(loginId).getId());
        } else {
            log.info("[Service][insert] New name received!!");
        }

        userRepository.save(userModel);
    }
}
