package com.blogspot.symfonyworld.wealthylaughingduck.bo;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;
import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;
import com.blogspot.symfonyworld.wealthylaughingduck.model.User;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.OutcomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.IncomeDao;
import com.blogspot.symfonyworld.wealthylaughingduck.dao.UserDao;

public abstract class DataProvider {

  protected OutcomeDao outcomeDao;

  protected IncomeDao incomeDao;

	protected UserDao userDao;

  public void setDaos(OutcomeDao outcomeDao, IncomeDao incomeDao, UserDao userDao) {
    this.outcomeDao = outcomeDao;
    this.incomeDao = incomeDao;
    this.userDao = userDao;
  }

	public abstract List<Outcome> getOutcomesByUserId(int userId);

	public abstract List<Income> getIncomesByUserId(int userId);

	public abstract List<User> getAllUsers();
}
