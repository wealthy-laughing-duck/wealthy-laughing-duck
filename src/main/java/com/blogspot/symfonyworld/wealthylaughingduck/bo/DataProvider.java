package com.blogspot.symfonyworld.wealthylaughingduck.bo;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;
import com.blogspot.symfonyworld.wealthylaughingduck.model.CategoryType;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.CategoryDao;

public abstract class DataProvider {

    protected OutcomeDao outcomeDao;

    protected IncomeDao incomeDao;

    protected UserDao userDao;

    protected CategoryDao categoryDao;

    public void setDaos(OutcomeDao outcomeDao, IncomeDao incomeDao, UserDao userDao, CategoryDao categoryDao) {
        this.outcomeDao = outcomeDao;
        this.incomeDao = incomeDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    public abstract List<Outcome> getOutcomesByUserId(int userId);

    public abstract List<Income> getIncomesByUserId(int userId);

    public abstract List<User> getAllUsers();

    public abstract long createCategory(int parent_id, String name, CategoryType type);

    public abstract void moveCategory(int category_id, int new_parent_id);

    public abstract void renameCategory(int category_id, String new_name);

    public abstract void removeCategory(int category_id);

    public abstract List<Category> getIncomeCategoryTree();

    public abstract List<Category> getOutcomeCategoryTree();
}
