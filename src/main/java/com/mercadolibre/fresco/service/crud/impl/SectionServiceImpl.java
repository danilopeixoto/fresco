package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.Section;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.service.crud.ISectionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SectionServiceImpl implements ISectionService {

    private SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional
    public Section create(Section section) {
        if (sectionRepository.findById(section.getId()).isPresent()){
            throw new BadRequestException("Section already exists!");
        }
        return sectionRepository.save(section);
    }

    @Override
    public Section update(Section section) {
        if (sectionRepository.findBySectionCode(section.getSectionCode()) == null){
            throw new NotFoundException("Section not found!");
        }
        return sectionRepository.save(section);
    }

    @Override
    public void delete(Long id) {
        if (sectionRepository.findById(id) == null){
            throw new NotFoundException("Section not found!");
        }
        sectionRepository.deleteById(id);
    }

    @Override
    public Section findById(Long id) {
        return null;
    }

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public Section findById(String id) {
        return sectionRepository.findById(id);
    }
}
