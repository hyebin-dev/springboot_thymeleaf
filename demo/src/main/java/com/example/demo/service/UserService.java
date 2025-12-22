package com.example.demo.service;

import com.example.demo.domain.UserVO;

import java.util.List;

public interface UserService {

    void insert(UserVO userVO);

    void modifyNoPwd(UserVO userVO);

    void modify(UserVO userVO);

    void remove(String name);

    List<UserVO> getList();

}
