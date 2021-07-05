package com.mercadolibre.fresco.util;

import com.mercadolibre.fresco.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDetailsMapper {
  static UserDetails build(User user) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
      .commaSeparatedStringToAuthorityList(user.getRole().getRoleCode());

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
  }
}
