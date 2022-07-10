package ru.pasha.yandexTask.response_patterns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainPattern {

    private short code;
    private String message;
}
