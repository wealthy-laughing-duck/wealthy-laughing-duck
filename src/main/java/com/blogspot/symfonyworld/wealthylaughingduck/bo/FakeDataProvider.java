package com.blogspot.symfonyworld.wealthylaughingduck.bo;

import java.util.ArrayList;
import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;

public class FakeDataProvider extends DataProvider {

    @Override
    public List<Outcome> getOutcomesByUserId(int userId) {
        List<Outcome> result = new ArrayList<>();
//        throw new UnsupportedOperationException("Not supported yet.");
        return result;
    }

    @Override
    public List<Income> getIncomesByUserId(int userId) {
        List<Income> result = new ArrayList<>();
//        throw new UnsupportedOperationException("Not supported yet.");
        return result;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        User u1 = new User();
        u1.setId(1);
        u1.setEmailAddress("john.doe@example.com");
        u1.setFirstName("John");
        u1.setLastName("Doe");
        u1.setUserName("jd");
        result.add(u1);
        return result;
    }

    @Override
    public List<Category> getIncomeCategoryTree() {
        List <Category> result = new ArrayList<>();
//        throw new UnsupportedOperationException("Not supported yet.");
        return result;
    }

    @Override
    public List<Category> getOutcomeCategoryTree() {
        List <Category> result = new ArrayList<>();
//        throw new UnsupportedOperationException("Not supported yet.");
        return result;
    }
}
