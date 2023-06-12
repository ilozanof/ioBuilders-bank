module com.ioBuilders.bank.domain {
    exports com.ioBuilders.bank.domain.user.ports.in;
    exports com.ioBuilders.bank.domain.user.ports.out;
    exports com.ioBuilders.bank.domain.user.model;

    exports com.ioBuilders.bank.domain.account.ports.in;
    exports com.ioBuilders.bank.domain.account.ports.out;
    exports com.ioBuilders.bank.domain.account.model;

    exports com.ioBuilders.bank.domain.transaction.ports.in;
    exports com.ioBuilders.bank.domain.transaction.ports.out;
    exports com.ioBuilders.bank.domain.transaction.model;

    requires lombok;
    requires spring.tx;
    requires java.desktop;
}