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
    @JsonProperty("issued_to")
    private String issuedTo;
    private String audience;
    @JsonProperty("user_id")
    private String userId;
    private String scope;
    @JsonProperty("expires_in")
    private Long expiresIn;
    private String email;
    @JsonProperty("verified_email")
    private boolean verifiedEmail;
    @JsonProperty("access_type")
    private String accessType;
}
