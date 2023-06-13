package com.ioBuilders.bank.domain.account.model;

import com.ioBuilders.bank.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author i.fernandez@nchain.com
 *
 * An Account is linked to one Client of the Bank. It contains a Balance
 * This class is immutable
 * // TODO: Add ref to TRansactions too
 */
@Data
@AllArgsConstructor
public class Account {
    private User user;
    private String accountId;
    private float balance;

}
