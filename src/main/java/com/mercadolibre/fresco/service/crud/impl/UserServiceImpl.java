package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.model.User;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.crud.IUserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new BadRequestException("User already exists!");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new NotFoundException("User not found!");

        }

        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()) == null){
            throw new UnauthorizedException("Invalid username or password!");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.findById(id).isPresent()){
            throw new NotFoundException("User not found!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        User user = findById(id);
        if (user == null){
            throw  new NotFoundException("User not found!");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new NotFoundException("Users not exist!");
        }
        return users;
    }
}
