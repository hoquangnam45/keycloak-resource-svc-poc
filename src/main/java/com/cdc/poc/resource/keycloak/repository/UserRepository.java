package com.cdc.poc.resource.keycloak.repository;

import com.cdc.poc.resource.keycloak.model.UserMdl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepository {
    List<UserMdl> getUser(@Param("id") String id, @Param("username") String username);
    void createUser(UserMdl user);
    void deleteUser(@Param("id") String id);
    void updateUser(@Param("id") String id, @Param("fullName") String fullName, @Param("password") String password, @Param("updatedAt") String updatedAt);
}
