package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Outcome;

public interface OutcomeDao extends Dao {

    public void save(Outcome outcome);

    public void update(Outcome outcome);

    public void delete(Outcome outcome);

    public List findAllOutcomes();

    public Outcome findByOutcomeId(long id);

    public List<Outcome> findByUserId(long id);
}
