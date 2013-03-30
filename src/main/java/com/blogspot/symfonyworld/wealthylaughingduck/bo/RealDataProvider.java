package com.blogspot.symfonyworld.wealthylaughingduck.bo;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;

public class RealDataProvider extends DataProvider {

    @Override
    public List<Outcome> getOutcomesByUserId(int userId) {
        return this.outcomeDao.findByUserId(userId);
    }

    @Override
    public List<Income> getIncomesByUserId(int userId) {
        return this.incomeDao.findByUserId(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDao.findAllUsers();
    }

    @Override
    public List<Category> getIncomeCategoryTree() {
        return this.categoryDao.findAllByType("IncomeCategory");
    }

    @Override
    public List<Category> getOutcomeCategoryTree() {
        return this.categoryDao.findAllByType("OutcomeCategory");
    }
}
