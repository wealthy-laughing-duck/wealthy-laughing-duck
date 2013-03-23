package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;

public interface OutcomeDao {

    void save(Outcome outcome);

    void update(Outcome outcome);

    void delete(Outcome outcome);

    List findAllOutcomes();

    Outcome findByOutcomeId(Long id);
}
