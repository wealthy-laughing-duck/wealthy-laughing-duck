package com.blogspot.symfonyworld.wealthylaughingduck.dao;

import java.util.List;

import com.blogspot.symfonyworld.wealthylaughingduck.model.Category;
import com.blogspot.symfonyworld.wealthylaughingduck.model.CategoryType;

public interface CategoryDao extends Dao {

    public void save(Category category);

    public void update(Category category);

    public void delete(Category category);

    public void delete(long id);
    
    public Category reference(long id);

    public Category get(long id);

    public List<Category> findAllByType(CategoryType type);
}
