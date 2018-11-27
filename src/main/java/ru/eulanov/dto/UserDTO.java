package ru.eulanov.dto;

import ru.eulanov.models.User;

public class UserDTO {

    private long id;
    private String login;

    private UserDTO() {
    }

    public static UserDTO createUserDTOFromUser(User user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setLogin(user.getLogin());
        } else {
            userDTO.setLogin("$undefined");
        }
        return userDTO;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
