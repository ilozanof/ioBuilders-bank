package com.ioBuilders.bank.domain.account.model;

import com.ioBuilders.bank.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author i.fernandez@nchain.com
 *
 * An Account is linked to one Client of the Bank.
 * Transaction from/to an Account might be considerable, so Transactions are not stored here and they should be
 * retreived independently in a Paginated way.
 *
 * This class is immutable
 */
@Data
@AllArgsConstructor
public class Account {
    private User user;
    private String accountId;
    private float balance;

}
