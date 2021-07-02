package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.Section;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.service.crud.ISectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

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
        return this.sectionRepository.getIdBySectionCode(sectionCode);
    }

    @Override
    public Section findById(Long id) {
        return this.sectionRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Section> findAll() {
        return this.sectionRepository.findAll();
    }
}
