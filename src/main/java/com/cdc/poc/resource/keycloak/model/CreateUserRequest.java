package com.cdc.poc.resource.keycloak.model;

public record CreateUserRequest(String username, String password, String fullName) {
}
