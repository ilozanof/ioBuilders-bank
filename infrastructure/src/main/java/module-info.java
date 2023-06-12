module com.ioBuilders.bank.infrastructure {
    requires transitive com.ioBuilders.bank.domain;

    exports com.ioBuilders.bank.infrastructure.user.rest;
    exports com.ioBuilders.bank.infrastructure.user.storage;

    exports com.ioBuilders.bank.infrastructure.account.rest;
    exports com.ioBuilders.bank.infrastructure.account.storage;

    exports com.ioBuilders.bank.infrastructure.transaction.rest;
    exports com.ioBuilders.bank.infrastructure.transaction.storage;

    requires lombok;
    requires spring.beans;
    requires spring.web;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires jakarta.persistence;
}