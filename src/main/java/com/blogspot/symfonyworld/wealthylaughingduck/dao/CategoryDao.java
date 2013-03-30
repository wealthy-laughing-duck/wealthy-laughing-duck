package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;

public interface CategoryDao extends Dao {

    public void save(Category category);

    public void update(Category category);

    public void delete(Category category);

    public List<Category> findAllByType(String type);
}
