package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;

public interface IncomeDao extends Dao {

    public void save(Income income);

    public void update(Income income);

    public void delete(Income income);

    public List findAllIncomes();

    public Income findByIncomeId(long id);

    public List<Income> findByUserId(long id);
}
