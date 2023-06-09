package com.example.ioBuilders.bank.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author i.fernandez@nchain.com
 *
 * A User represents a physical person, a Client of the Bank.
 * This class is immutable
 */
@AllArgsConstructor
@Data
public final class User {
    private final String dni;
    private final String name;
}
