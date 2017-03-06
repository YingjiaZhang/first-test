package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface ItemMapper {
    List<Item> findAll();

    Item getItemById(Integer id);

    Integer insertItem(Item item);

    Integer updateItem(Item item);

    Integer deleteItemById(Integer id);
}