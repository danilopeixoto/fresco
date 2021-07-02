package com.mercadolibre.fresco.service.crud;


import com.mercadolibre.fresco.model.Section;

public interface ISectionService extends ICRUD<Section> {

    Section findById(String id);
}
