package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserData {

    private final String login;
    private final String password;
    private final String status;

}
