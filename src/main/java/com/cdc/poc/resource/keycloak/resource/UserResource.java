package com.cdc.poc.resource.keycloak.resource;

import com.cdc.poc.resource.keycloak.model.CreateUserRequest;
import com.cdc.poc.resource.keycloak.model.UpdateUserRequest;
import com.cdc.poc.resource.keycloak.model.UserMdl;
import com.cdc.poc.resource.keycloak.repository.UserRepository;
import io.quarkus.security.PermissionsAllowed;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Path("/api/users")
@RequiredArgsConstructor
@PermissionsAllowed("user-management-scope")
public class UserResource {
    private final UserRepository userRepository;

    @GET
    public RestResponse<Object> getUserInfo(@QueryParam("id") String id, @QueryParam("username") String username) {
        return userRepository.getUser(id, username).stream().findFirst().map(user -> RestResponse.ok((Object) user)).orElseGet(() -> RestResponse.status(RestResponse.Status.NOT_FOUND, "User not found"));
    }

    @POST
    public RestResponse<UserMdl> createUser(CreateUserRequest request) {
        Instant now = Instant.now();
        UserMdl user = UserMdl.builder()
                .id(UUID.randomUUID().toString())
                .username(request.username())
                .password(Base64.getEncoder().encodeToString(request.password().getBytes()))
                .fullName(request.fullName())
                .createdAt(now.toString())
                .updatedAt(now.toString())
                .build();
        userRepository.createUser(user);
        return RestResponse.ok(user);
    }

    @DELETE
    public RestResponse<String> deleteUser(@QueryParam("id") String id, @QueryParam("username") String username) {
        UserMdl user = userRepository.getUser(id, username).stream().findFirst().orElse(null);
        if (user == null) {
            return RestResponse.status(RestResponse.Status.NOT_FOUND, "User not found");
        }
        userRepository.deleteUser(user.getId());
        return RestResponse.ok("User deleted successfully");
    }

    @PUT
    public RestResponse<Object> updateUser(UpdateUserRequest request) {
        UserMdl user = userRepository.getUser(request.id(), null).stream().findFirst().orElse(null);
        if (user == null) {
            return RestResponse.status(RestResponse.Status.NOT_FOUND, "User not found");
        }
        Instant now = Instant.now();
        userRepository.updateUser(request.id(), request.fullName(), request.password() == null ? null : Base64.getEncoder().encodeToString(request.password().getBytes()), now.toString());
        user.setUpdatedAt(now.toString());
        if (request.fullName() != null) {
            user.setFullName(request.fullName());
        }
        if (request.password() != null) {
            user.setPassword(Base64.getEncoder().encodeToString(request.password().getBytes()));
        }
        return RestResponse.ok(user);
    }
}
