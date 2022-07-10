package ru.pasha.yandexTask.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Request {

    List<Product> items;
    String updateDate;
}
