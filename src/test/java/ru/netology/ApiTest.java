package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class ApiTest {

    public UserData user = DataConfig.generateData(true);
    public UserData blockedUser = DataConfig.generateData(false);

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    void shouldCreateAccount(UserData someUser){
                 given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldOpenAccount(){
        shouldCreateAccount(user);
        open("http://localhost:9999");
        SelenideElement form = $("[id = root]");
        form.$("[data-test-id=login] input").setValue(user.getLogin());
        form.$("[data-test-id=password] input").setValue(user.getPassword());
        form.$(".button").click();
        $(withText("Личный кабинет")).waitUntil(Condition.visible, 15000);

    }

    @Test
    void shouldNotOpenBlockedAccount(){
        shouldCreateAccount(blockedUser);
        open("http://localhost:9999");
        SelenideElement form = $("[id = root]");
        form.$("[data-test-id=login] input").setValue(blockedUser.getLogin());
        form.$("[data-test-id=password] input").setValue(blockedUser.getPassword());
        form.$(".button").click();
        $(withText("Ошибка!")).waitUntil(Condition.visible, 15000);

    }

    @Test
    void shouldNotOpenIfInvalidLogin(){
        shouldCreateAccount(user);
        open("http://localhost:9999");
        SelenideElement form = $("[id = root]");
        form.$("[data-test-id=login] input").setValue(blockedUser.getLogin());
        form.$("[data-test-id=password] input").setValue(user.getPassword());
        form.$(".button").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);

    }

    @Test
    void shouldNotOpenIfInvalidPassword(){
        shouldCreateAccount(user);
        open("http://localhost:9999");
        SelenideElement form = $("[id = root]");
        form.$("[data-test-id=login] input").setValue(user.getLogin());
        form.$("[data-test-id=password] input").setValue(blockedUser.getPassword());
        form.$(".button").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);

    }

}
