package com.blogspot.symfonyworld.dao;

import java.util.List;

import com.blogspot.symfonyworld.model.Outcome;

public interface OutcomeDao {

    void save(Outcome outcome);

    void update(Outcome outcome);

    void delete(Outcome outcome);

    List findAllOutcomes();

    Outcome findByOutcomeId(Long id);
}
