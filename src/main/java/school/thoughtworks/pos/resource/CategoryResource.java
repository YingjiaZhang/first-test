package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.mapper.CategoryMapper;

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
@Path("/categories")
public class CategoryResource {
    @Inject
    private SqlSession session;

    @Inject
    private CategoryMapper categoryMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        Map<String, Object> result = new HashMap<>();

        List<Category> originCategories = categoryMapper.findAll();

        List<Map> categories = originCategories
                .stream()
                .map(category -> category.toMap())
                .collect(Collectors.toList());

        result.put("categories", categories);
        result.put("totalCount", categories.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("id") Integer id) {

        Category category = categoryMapper.getCategoryById(id);
        if (null == category) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(category.toMap()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCategory(Category category) {
        categoryMapper.insertCategory(category);
        session.commit();
        Map result = new HashMap();
        result.put("uri", "categories/" + category.getId());
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(Category category) {
        categoryMapper.updateCategory(category);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategoryById(@PathParam("id") Integer id) {
        try {
            categoryMapper.deleteCategoryById(id);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            Map message = new HashMap();
            message.put("meaasge", "该分类下还有商品，不能删除！");
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(message).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();

    }

}
