package org.example.vkr.service;

public interface TokenService {
    boolean checkToken(String token);
    String getUsernameByToken(String token);

    Long getIdByToken(String token);
}
