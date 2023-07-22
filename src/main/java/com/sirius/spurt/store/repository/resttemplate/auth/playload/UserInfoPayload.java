package com.sirius.spurt.store.repository.resttemplate.auth.playload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoPayload {
    @JsonProperty("id")
    private String userId;

    private String email;

    @JsonProperty("verified_email")
    private Boolean verifiedEmail;

    private String name;

    @JsonProperty("given_name")
    private String givenName;

    private String picture;
    private String locale;
    private String hd;
}
