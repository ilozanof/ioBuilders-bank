package com.ioBuilders.bank.infrastructure.rest.user;

import com.ioBuilders.bank.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@NoArgsConstructor
@AllArgsConstructor
public class UserView extends RepresentationModel<UserView> {
    private User user;

    public String getDni() { return this.user.getDni();}
    public String getName() { return this.user.getName();}
}
