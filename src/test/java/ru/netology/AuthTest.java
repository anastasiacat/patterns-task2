package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getWrongLogin;
import static ru.netology.DataGenerator.getWrongPassword;

public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorRegisteredBlockedUser() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(
                Condition.text("Ошибка! Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldErrorNotRegisteredUser() {
        var registeredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(
                Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(getWrongLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(
                Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(getWrongPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(
                Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
