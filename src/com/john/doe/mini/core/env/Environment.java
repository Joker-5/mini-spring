package com.john.doe.mini.core.env;

/**
 * Created by JOHN_DOE on 2023/5/10.
 */
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... args);
}
