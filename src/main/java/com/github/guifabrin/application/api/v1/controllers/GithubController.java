package com.github.guifabrin.application.api.v1.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.github.guifabrin.application.api.v1.entities.GithubContent;
import com.github.guifabrin.application.api.v1.entities.GithubOrg;
import com.github.guifabrin.application.api.v1.entities.GithubRepo;
import com.github.guifabrin.application.api.v1.entities.GithubUser;
import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubController {

	Gson gson = new Gson();

	/**
	 * This method returns a ResponseEntity with, or without GithubUser searched and
	 * user informations
	 *
	 * @param String username
	 * @return ResponseEntity<GithubUser>
	 * @see ResponseEntity
	 */
	@GetMapping("api/v1/github/{username}")
	public ResponseEntity<GithubUser> index(@PathVariable String username) {
		try {
			HttpResponse<String> responseUser = this.getRequestHTTP("https://api.github.com/users/" + username);
			if (responseUser.statusCode() == HttpStatus.OK.value()) {
				GithubUser githubUser = gson.fromJson(responseUser.body(), GithubUser.class);
				HttpResponse<String> responseRepos = this.getRequestHTTP(githubUser.repos_url);
				if (responseRepos.statusCode() == HttpStatus.OK.value()) {
					githubUser.repos = gson.fromJson(responseRepos.body(), GithubRepo[].class);
				}
				HttpResponse<String> responseOrgs = this.getRequestHTTP(githubUser.organizations_url);
				if (responseOrgs.statusCode() == HttpStatus.OK.value()) {
					githubUser.orgs = gson.fromJson(responseOrgs.body(), GithubOrg[].class);
				}
				return new ResponseEntity<>(githubUser, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.valueOf(responseUser.statusCode()));
		} catch (InterruptedException | IOException exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("api/v1/github/{username}/{repo}/readme")
	public ResponseEntity<String> readme(@PathVariable String username, @PathVariable String repo) {
		try {
			HttpResponse<String> responseReadme = this.getRequestHTTP("https://api.github.com/repos/"+username+"/"+repo+"/contents/README.md");
			if (responseReadme.statusCode() == HttpStatus.OK.value()) {
				return new ResponseEntity<>(gson.fromJson(responseReadme.body(), GithubContent.class).getDecodedContent(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.valueOf(responseReadme.statusCode()));
		} catch (InterruptedException | IOException exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method returns a HttpResponse with status and body from a simple HTTP
	 * request with a bearer authorization with github token
	 *
	 * @param String urlRequest
	 * @return HttpResponse<String>
	 * @see HttpResponse
	 */
	private HttpResponse<String> getRequestHTTP(String urlRequest) throws InterruptedException, IOException {
		System.out.println(urlRequest);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlRequest))
				.header("Authorization", "Bearer 1453d4ff648ab71c4da209d52177db0595389dfd").build();
		return client.send(request, BodyHandlers.ofString());
	}
}