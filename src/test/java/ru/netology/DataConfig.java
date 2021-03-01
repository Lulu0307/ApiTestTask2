package ru.netology;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataConfig {

    private DataConfig() {
    }

    public static String getLogin() {
        Faker faker = new Faker(new Locale("eng"));
        return faker.name().username();
    }

    public static String getPassword() {
        Faker faker = new Faker(new Locale("eng"));
        return faker.lorem().characters(8, 11);
    }

    public static String activeAccount(boolean isActive) {
        if (isActive) {
            return "active";
        } else return "blocked";
    }


    public static UserData generateData(boolean isActive) {
        return new UserData(getLogin(), getPassword(), activeAccount(isActive));
    }


}
