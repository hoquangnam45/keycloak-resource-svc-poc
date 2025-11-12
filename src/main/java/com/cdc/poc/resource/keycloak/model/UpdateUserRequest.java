package com.cdc.poc.resource.keycloak.model;

public record UpdateUserRequest(String id, String fullName, String password) {
}
