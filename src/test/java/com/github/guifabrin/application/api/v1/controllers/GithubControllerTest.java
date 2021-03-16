package com.github.guifabrin.application.api.v1.controllers;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.github.guifabrin.application.api.v1.controllers.GithubController;
import com.github.guifabrin.application.api.v1.entities.GithubUser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class GithubControllerTest {

    GithubController controller;

    public GithubControllerTest() {
        this.controller = new GithubController();
    }

    @Test
    public void contextLoads() {
        assertThat(this.controller).isNotNull();
    }

    /**
	 * This method test with a guifabrin valid login a HTTP OK answer
	 */
    @Test
    public void testeWithValidUser() {
        ResponseEntity<GithubUser> response = this.controller.index("guifabrin");
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertTrue(response.getBody().getClass() == GithubUser.class);
    }

    /**
	 * This method test with a non_valid_user login a HTTP NOT_FOUND answer
	 */
    @Test
    public void testWithNoValidUser() {
        ResponseEntity<GithubUser> response = this.controller.index("non_valid_user");
        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
        assertTrue(response.getBody() == null);
    }
}
