
package com.saurav.journalApp.DTO;

public class UserResponseDTO {
    private String userName;

    public UserResponseDTO() {}

    public UserResponseDTO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
