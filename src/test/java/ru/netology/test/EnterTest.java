package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;

public class EnterTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterSuccessfully() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(".heading").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldNotLetInactiveUserIn() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка" + " Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLetInWithInvalidLogin() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue("invalid");
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка" + " " + "Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLetInWithInvalidPassword() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue("invalid");
        $(".button").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка" + " " + "Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLetInWithUnknownUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(DataGenerator.getLogin());
        $("[data-test-id=password] input").setValue(DataGenerator.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка" + " " + "Ошибка! Неверно указан логин или пароль"));
    }

}
