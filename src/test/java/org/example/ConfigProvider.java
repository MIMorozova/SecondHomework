package org.example;

import org.aeonbits.owner.ConfigFactory;

// создаём готовый config
public class ConfigProvider {
    public static TestConfig config = ConfigFactory.create(TestConfig.class);
}
