module com.ioBuilders.bank.infrastructure {
    requires transitive com.ioBuilders.bank.domain;

    exports com.ioBuilders.bank.infrastructure.rest.user;
    exports com.ioBuilders.bank.infrastructure.rest.account;
    exports com.ioBuilders.bank.infrastructure.rest.transaction;

    exports com.ioBuilders.bank.infrastructure.storage.user;
    exports com.ioBuilders.bank.infrastructure.storage.account;
    exports com.ioBuilders.bank.infrastructure.storage.transaction;

    requires lombok;
    requires spring.beans;
    requires spring.web;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires spring.hateoas;
}