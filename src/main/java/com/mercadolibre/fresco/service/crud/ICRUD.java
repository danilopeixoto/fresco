package com.mercadolibre.fresco.service.crud;

import java.util.List;

public interface ICRUD<DTO> {
    //DTO create(Object dto);

    //DTO update(Object dto);

    void delete(Long id);

    DTO findById(Long id);

    List<DTO> findAll();
}
