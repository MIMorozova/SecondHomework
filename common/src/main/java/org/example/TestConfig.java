package org.example;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")//где лежать конфиги
public interface TestConfig extends Config {
    @Key("base.url")
    String testBaseUrl();

    @Key("api.url")
    String testApiUrl();

    @Key("login.admin")
    String testLoginAdmin();

    @Key("password.admin")
    String testPasswordAdmin();

    @Key("timeout")
    Integer testTimeout();

    @Key("logging.mode")
    String testLoggingMode();

    @Key("start.product.name")
    String testProductName();

    @Key("start.product.price")
    Integer testProductPrice();

    @Key("second.product.name")
    String testSecondProductName();

    @Key("second.product.price")
    Integer testSecondProductPrice();
}
