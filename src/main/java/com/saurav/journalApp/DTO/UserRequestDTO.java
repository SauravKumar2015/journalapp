package com.saurav.journalApp.DTO;

import com.saurav.journalApp.enums.Sentiment;
import javax.validation.constraints.NotNull;  // ✅ Spring Boot 2.x
import lombok.Data;

@Data
public class UserRequestDTO {
    private String userName;
    private String password;
    private String email;

    @NotNull(message = "sentimentAnalysis is required. Use: HAPPY, SAD, ANGRY, ANXIOUS")
    private Sentiment sentimentAnalysis;
}