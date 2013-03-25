package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.User;

public interface UserDao extends Dao {

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public List findAllUsers();
}
