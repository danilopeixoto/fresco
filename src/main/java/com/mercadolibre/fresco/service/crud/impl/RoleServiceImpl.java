package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.Role;
import com.mercadolibre.fresco.repository.RoleRepository;
import com.mercadolibre.fresco.service.crud.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

  private RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role create(Role role) {
    return null;
  }

  @Override
  public Role update(Role role) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public Role findById(Long id) {
    return null;
  }

  @Override
  public List<Role> findAll() {
    return null;
  }
}
