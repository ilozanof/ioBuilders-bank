package com.ioBuilders.bank.domain.account.model;

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
    private String iban;
    private float balance;

    // TODO: Put here a Rerference to the User???
}
