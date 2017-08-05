package com.bnelson.triton.client.service;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@SuppressWarnings("VoidMethodAnnotatedWithGET")
@Path("/rest/security")
public interface LoginRestService extends RestService {

    @GET
    @Path("login/?username={username}&password={password}")
    void login(@PathParam("username") String username,
               @PathParam("password") String password,
               MethodCallback<Boolean> callback);
}
