package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.mapper.CartMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhyingjia on 17-3-6.
 */
@Path("/carts")
public class CartResource {

    @Inject
    private SqlSession session;

    @Inject
    private CartMapper cartMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCarts() {
        Map<String, Object> result = new HashMap<>();

        List<Cart> originCarts = cartMapper.findAll();

        List<Map> carts = originCarts
                .stream()
                .map(cart -> cart.toMap())
                .collect(Collectors.toList());

        result.put("carts", carts);
        result.put("totalCount", carts.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartById(@PathParam("id") Integer id) {

        Cart cart = cartMapper.getCartById(id);
        if (null == cart) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(cart.toMap()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCart(Map data) {
        Cart cart = new Cart();
        cart.setUserId((Integer) data.get("userId"));
        cartMapper.insertCart(cart);
        session.commit();

        Integer cartId = cart.getId();
        if (data.get("items") != null) {
            List<Integer> itemsId = (List<Integer>) data.get("items");
            for (Integer itemId : itemsId) {
                cartMapper.insertItemCart(itemId, cartId);
            }
        }
        Map result = new HashMap();
        result.put("cartUri", "carts/" + cartId);

        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCart(Cart cart) {
        cartMapper.updateCart(cart);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCartById(@PathParam("id") Integer id) {
        cartMapper.deleteCartById(id);
        session.commit();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
