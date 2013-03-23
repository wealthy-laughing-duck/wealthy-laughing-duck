package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Income;

public interface IncomeDao {

    void save(Income income);

    void update(Income income);

    void delete(Income income);

    List findAllIncomes();

    Income findByIncomeId(long id);

    List<Income> findByUserId(long id);
}
