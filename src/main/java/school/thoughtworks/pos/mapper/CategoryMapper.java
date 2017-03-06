package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> findAll();

    Category getCategoryById(Integer id);

    Integer insertCategory(Category category);

    Integer updateCategory(Category category);

    Integer deleteCategoryById(Integer id);
}