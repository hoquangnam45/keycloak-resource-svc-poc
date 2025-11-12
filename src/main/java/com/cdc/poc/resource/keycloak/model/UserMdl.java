package com.cdc.poc.resource.keycloak.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMdl {
    private String id;
    private String username;
    private String fullName;
    private String password;
    private String createdAt;
    private String updatedAt;
}
