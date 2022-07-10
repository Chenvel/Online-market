package ru.pasha.yandexTask;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pasha.yandexTask.exception.ValidationFailedException;
import ru.pasha.yandexTask.service.ProductServiceImpl;

import java.text.ParseException;

@SpringBootTest(classes = YandexTaskApplication.class)
@AllArgsConstructor
@RunWith(SpringRunner.class)
class YandexTaskApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@Test
	void successUpdate() throws ParseException, ValidationFailedException {

	}
}
