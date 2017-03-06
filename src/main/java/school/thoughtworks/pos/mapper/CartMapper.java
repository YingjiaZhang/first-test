package school.thoughtworks.pos.mapper;

import school.thoughtworks.pos.bean.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> findAll();

    Cart getCartById(Integer id);

    Integer insertCart(Cart cart);

    Integer updateCart(Cart cart);

    Integer deleteCartById(Integer id);

    Integer insertItemCart(Integer itemId, Integer cartId);
}