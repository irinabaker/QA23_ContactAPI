package com.telran.api.tests;

import com.jayway.restassured.RestAssured;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTests {

    @BeforeMethod
    public void ensurePreconditions() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginPositiveTest() {

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("karl+1@gmail.com")
                .password("Ka1234567$")
                .build();

        AuthResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImthcmwrMUBnbWFpbC5jb20ifQ.d-_U_7BdHBuYMeuJbOEeGFA-NnnVTei2eziLkloFv-4";

        String token2 = given().contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token", equalTo(token))
                .extract().path("token");

        System.out.println(token2);
    }

    @Test
    public void loginNegativeTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("karl+1@gmail.com")
                .password("Ka1234567")
                .build();

        String message = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");

        System.out.println(message);
    }
}
