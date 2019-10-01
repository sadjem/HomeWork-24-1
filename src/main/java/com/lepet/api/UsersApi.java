package com.lepet.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lepet.dao.UserDao;
import com.lepet.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class UsersApi {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = UserDao.getInstance().getUsers();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(users);
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(@FormParam("name") String name, @FormParam("age") int age) {
        User newUser = new User(name, age, UserDao.getInstance().generateId());
        UserDao.getInstance().addUser(newUser);
        return Response
                .status(Response.Status.OK)
                .entity("User created")
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        String result = null;
        try {
            List<User> users = UserDao.getInstance().getUsers();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.toJson(users.get(id));
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("No such user")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity("User with " + id + result)
                .build();
    }
    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("name") String name){
        UserDao.getInstance().deleteUser(name);
        return Response
                .status(Response.Status.OK)
                .entity("User " + name + " deleted")
                .build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String jsonUser){
        User user = gson.fromJson(jsonUser, User.class);
        if(UserDao.getInstance().updateUser(user)){
            return Response
                    .status(Response.Status.OK)
                    .entity("User updated")
                    .build();
        }else{
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Error")
                    .build();
        }
    }
}
