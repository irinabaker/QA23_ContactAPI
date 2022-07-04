package com.telran.api.tests.superHero;

import com.jayway.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import superHeroDto.HeroRequestDto;
import superHeroDto.HeroResponseDto;

import static com.jayway.restassured.RestAssured.given;

public class SuperHeroTests {

    @BeforeMethod
    public void ensurePreconditions() {
        RestAssured.baseURI = "https://superhero.qa-test.csssr.com";
        RestAssured.basePath = "superheroes";
    }

    @Test
    public void createSuperHeroPositiveTest() {

        HeroRequestDto requestDto = HeroRequestDto.builder()
                .birthDate("2019-02-21")
                .city("New York")
                .fullName("Doctor Strange")
                .gender("M")
                .mainSkill("Magic")
                .phone("+74998884433")
                .build();

        HeroResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(HeroResponseDto.class);
        System.out.println(responseDto.getId());
        System.out.println(responseDto.getPhone());
        Assert.assertEquals(responseDto.getPhone(), "+74998884433");
    }

    @Test
    public void createSuperHeroNegativeWithoutBodyTest() {

        HeroResponseDto responseDto = given()
                .contentType("application/json")
                .post()
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(HeroResponseDto.class);
        System.out.println(responseDto.getMessage());
    }

    @Test
    public void createSuperHeroNegativeWithoutBirthDateTest() {

        HeroRequestDto requestDto = HeroRequestDto.builder()
                .city("New York")
                .fullName("Doctor Strange")
                .gender("M")
                .mainSkill("Magic")
                .phone("+74998884433")
                .build();

        HeroResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(403)
                .extract().response().as(HeroResponseDto.class);
        Assert.assertEquals(responseDto.getMessage(), "Forbidden");
    }

    @Test
    public void createSuperHeroNegativeWithoutContentTypeTest() {

        HeroRequestDto requestDto = HeroRequestDto.builder()
                .birthDate("2019-02-21")
                .city("New York")
                .fullName("Doctor Strange")
                .gender("M")
                .mainSkill("Magic")
                .phone("+74998884433")
                .build();

        HeroResponseDto responseDto = given()
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(415)
                .extract().response().as(HeroResponseDto.class);

        Assert.assertEquals(responseDto.getMessage(), "Content type 'text/plain;charset=ISO-8859-1' not supported");
    }

    @Test
    public void getSuperHeroByIDPositiveTest() {

        HeroRequestDto requestDto = HeroRequestDto.builder()
                .birthDate("2019-02-21")
                .city("New York")
                .fullName("Doctor Strange")
                .gender("M")
                .mainSkill("Magic")
                .phone("+74998884433")
                .build();

        HeroResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(HeroResponseDto.class);

        int responseId = responseDto.getId();

        HeroResponseDto responseDto1 = given()
                .contentType("application/json")
                .get("/" + responseId)
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(HeroResponseDto.class);
        System.out.println(responseDto1.getId());

    }

    @Test
    public void deleteSuperHeroByIDPositiveTest() {

        HeroRequestDto requestDto = HeroRequestDto.builder()
                .birthDate("2019-02-21")
                .city("New York")
                .fullName("Doctor Strange")
                .gender("M")
                .mainSkill("Magic")
                .phone("+74998884433")
                .build();

        HeroResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(HeroResponseDto.class);

        int responseId = responseDto.getId();

        given()
                .contentType("application/json")
                .delete("/" + responseId)
                .then()
                .assertThat().statusCode(200);

        HeroResponseDto responseDto1 = given()
                .contentType("application/json")
                .get("/" + responseId)
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(HeroResponseDto.class);
        
        Assert.assertEquals(responseDto1.getMessage(),"Superhero with id " + "'" + responseId + "'" + " was not found");

    }

}
