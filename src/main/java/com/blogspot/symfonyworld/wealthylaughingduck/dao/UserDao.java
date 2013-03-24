package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.User;

public interface UserDao {

    void save(User user);

    void update(User user);

    void delete(User user);

    List findAllUsers();
}
