package com.blogspot.symfonyworld.wealthylaughingduck.thrift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import org.apache.thrift.TException;

import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.FinanceService;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.CategoryType;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TCategory;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TIncome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TOutcome;
import com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated.TUser;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.bo.DataProvider;

public class FinanceServiceHandler implements FinanceService.Iface {

    private DataProvider dataProvider;

    private Logger logger;

    public FinanceServiceHandler(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.logger = LoggerFactory.getLogger("FinanceServiceHandler");
    }

    @Override
    public List<TOutcome> getUserOutcomes(int user_id) throws TException {
        logger.info("BEGIN getUserOutcomes");
        List<Outcome> outcomes = this.dataProvider.getOutcomesByUserId(user_id);
        logger.info("found {} records", outcomes.size());
        List<TOutcome> result = new ArrayList<>();
        for (Iterator iterator = outcomes.iterator(); iterator.hasNext();) {
            Outcome outcome = (Outcome) iterator.next();
            TOutcome t_outcome = new TOutcome(
                    (double) (Math.round(outcome.getAmount() * 100)) / 100,
                    outcome.getUser().getName(),
                    String.valueOf(outcome.getCategory().getName()));
            t_outcome.setComment(outcome.getComment());
            result.add(t_outcome);
        }
        logger.info("returned {} results", result.size());
        logger.info("END getUserOutcomes");
        return result;
    }

    @Override
    public List<TIncome> getUserIncomes(int user_id) throws TException {
        logger.info("BEGIN getUserIncomes");
        List<Income> incomes = this.dataProvider.getIncomesByUserId(user_id);
        logger.info("found {} records", incomes.size());
        List<TIncome> result = new ArrayList<>();
        for (Iterator iterator = incomes.iterator(); iterator.hasNext();) {
            Income income = (Income) iterator.next();
            TIncome t_income = new TIncome(
                    (double) (Math.round(income.getAmount() * 100)) / 100,
                    income.getUser().getName(),
                    String.valueOf(income.getCategory().getName()));
            t_income.setComment(income.getComment());
            result.add(t_income);
        }
        logger.info("returned {} results", result.size());
        logger.info("END getUserIncomes");
        return result;
    }

    @Override
    public List<TUser> getAllUsers() throws TException {
        logger.info("BEGIN getAllUsers");
        List<User> users = this.dataProvider.getAllUsers();
        logger.info("found {} records", users.size());
        List<TUser> result = new ArrayList<>();
        for (Iterator iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            TUser t_user = new TUser(user.getUserName(), user.getName());
            result.add(t_user);
        }
        logger.info("returned {} results", result.size());
        logger.info("END getAllUsers");
        return result;
    }

    @Override
    public List<TCategory> getCategoryTree(CategoryType type) throws TException {
        logger.info("BEGIN getCategoryTree");
        logger.info("> ARG type: " + type.toString());
        List<Category> categories = null;
        switch (type.getValue()) {
            case 1:
                categories = this.dataProvider.getIncomeCategoryTree();
                break;
            case 2:
                categories = this.dataProvider.getOutcomeCategoryTree();
                break;
        }
        logger.info("found {} records", categories.size());
        List<TCategory> result = new ArrayList<>();
        for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
            Category category = (Category) iterator.next();
            TCategory t_category = new TCategory((int) category.getId(), category.getName());
            if (category.getParent() != null) {
                t_category.setParent_id((int) category.getParent().getId());
            }
            result.add(t_category);
        }
        logger.info("returned {} results", result.size());
        logger.info("END getCategoryTree");
        return result;
    }

    @Override
    public void createCategoryTreeNode(int parent_id, String name, CategoryType type) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveCategoryTreeNode(int id, int new_parent_id) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void renameCategoryTreeNode(int id, String new_name) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCategoryTreeNode(int id) throws TException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
