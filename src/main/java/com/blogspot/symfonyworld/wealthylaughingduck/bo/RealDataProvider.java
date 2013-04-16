package com.blogspot.symfonyworld.wealthylaughingduck.bo;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;
import com.blogspot.symfonyworld.wealthylaughingduck.model.CategoryType;

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
        return this.categoryDao.findAllByType(CategoryType.income);
    }

    @Override
    public List<Category> getOutcomeCategoryTree() {
        return this.categoryDao.findAllByType(CategoryType.outcome);
    }

    @Override
    public long createCategory(int parent_id, String name, CategoryType type) {
        Category category = new Category();
        category.setName(name);
        category.setType(type);
        if (parent_id > 0) {
            category.setParent(this.categoryDao.reference(parent_id));
        } else {
            category.setParent(null);
        }
        this.categoryDao.save(category);
        return category.getId();
    }

    @Override
    public void moveCategory(int category_id, int new_parent_id) {
        Category category = this.categoryDao.get(category_id);
        category.setParent(this.categoryDao.reference(new_parent_id));
        this.categoryDao.update(category);
    }

    @Override
    public void renameCategory(int category_id, String new_name) {
        Category category = this.categoryDao.get(category_id);
        category.setName(new_name);
        this.categoryDao.update(category);
    }

    @Override
    public void removeCategory(int category_id) {
        this.categoryDao.delete(category_id);
    }
}
