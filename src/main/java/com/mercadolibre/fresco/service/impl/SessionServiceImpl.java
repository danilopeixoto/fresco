package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.User;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements ISessionService {
    private final String SECRET = "mySecretKey";

    private final UserRepository userRepository;

    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AccountResponseDTO login(String username, String password) throws ApiException {
        User userAccount = userRepository.findByUsernameAndPassword(username, password);

        if (userAccount != null) {
            String token = getJWTToken(username, userAccount.getRole().getRoleCode());
            AccountResponseDTO user = new AccountResponseDTO();
            user.setUsername(username);
            user.setToken(token);
            return user;
        } else {
            throw new ApiException("404", "Wrong username or password", 404);
        }
    }

    /**
     * Genera un token para un usuario específico, válido por 10'
     *
     * @param username
     * @return
     */
    private String getJWTToken(String username, String roleCode) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList(roleCode);
        String token = Jwts
            .builder()
            .setId("softtekJWT")
            .setSubject(username)
            .claim("authorities",
                grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 600000))
            .signWith(SignatureAlgorithm.HS512,
                SECRET.getBytes()).compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
     *
     * @param token
     * @return
     */
    private static Claims decodeJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey("mySecretKey".getBytes())
            .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * Permite obtener el username según el token indicado
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }
}
