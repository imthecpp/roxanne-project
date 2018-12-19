package com.heroku.roxanne.security.model.api;

import com.heroku.roxanne.security.model.UserIdentity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserIdentityApiModel extends UserIdentity {

    private String passwordConfirmation;

}
