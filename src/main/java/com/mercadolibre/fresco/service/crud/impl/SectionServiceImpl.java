package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Section;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.service.crud.ISectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements ISectionService {

  private final SectionRepository sectionRepository;

  public SectionServiceImpl(SectionRepository sectionRepository) {
    this.sectionRepository = sectionRepository;
  }

  @Override
  public Section create(Section section) {
    return this.sectionRepository.save(section);
  }

  @Override
  public Section update(Section section) {
    return null;
  }

  @Override
  public void delete(Long id) {
    this.sectionRepository.deleteById(id);
  }

  @Override
  public Long getIdBySectionCode(String sectionCode) {
    sectionCode = sectionCode.toUpperCase();
    Long sectionId = this.sectionRepository.getIdBySectionCode(sectionCode);
    if (sectionId == null) {
    }
    return sectionId;
  }

  @Override
  public Section findById(Long id) {
    Section section = this.sectionRepository.findById(id).get();
    if (section == null) {
      throw new NotFoundException("Section with " + id + " not found!");
    }
    return section;
  }

  @Override
  public List<Section> findAll() {
    List<Section> sections = this.sectionRepository.findAll();
    if (sections.isEmpty()) {
      throw new NotFoundException("Sections not found!");
    }
    return sections;
  }
}
