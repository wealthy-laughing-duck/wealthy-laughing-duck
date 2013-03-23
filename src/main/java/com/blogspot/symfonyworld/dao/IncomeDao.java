package com.blogspot.symfonyworld.dao;

import java.util.List;

import com.blogspot.symfonyworld.model.Income;

public interface IncomeDao {

    void save(Income income);

    void update(Income income);

    void delete(Income income);

    List findAllIncomes();

    Income findByIncomeId(Long id);
}
