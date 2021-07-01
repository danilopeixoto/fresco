package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.AccountResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.Account;
import com.mercadolibre.fresco.repository.AccountRepository;
import com.mercadolibre.fresco.service.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements ISessionService {
    private final AccountRepository accountRepository;

    public SessionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param username
     * @param password
     * @return
     * @throws NotFoundException
     */
    @Override
    public AccountResponseDTO login(String username, String password) throws ApiException {
        Account account = accountRepository.findByUsernameAndPassword(username, password);

        if (account != null) {
            String token = getJWTToken(username);
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
     * @param username
     * @return
     */
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
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
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
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
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }
}
