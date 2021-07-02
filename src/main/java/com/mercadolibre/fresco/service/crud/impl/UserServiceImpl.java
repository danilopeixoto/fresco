package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.User;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.crud.IUserService;
import com.mercadolibre.fresco.util.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserServiceImpl implements IUserService {
    private static UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User retrievedUser = this.userRepository.findByUsername(username);

        if (retrievedUser == null) {
            throw new UsernameNotFoundException("Wrong username or password");
        }

        return UserDetailsMapper.build(retrievedUser);
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
