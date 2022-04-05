package com.romanyuta.TalkToUserService;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/request")
public class MainController {


    private static final String USER = "Admin";
    private static final String PASSWORD = "admin";
    static final String URL_USERS = "http://localhost:8080/api/user";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public String getUsers(){
        HttpHeaders headers = new HttpHeaders();
        String plainCreds = "Admin:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.set("Authorization","Basic " + base64Creds);

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(URL_USERS, HttpMethod.GET,entity,String.class);

        return response.getBody();
    }
}
