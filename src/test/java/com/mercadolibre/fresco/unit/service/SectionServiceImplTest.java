package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.model.Section;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.service.crud.impl.SectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SectionServiceImplTest {

    SectionRepository sectionRepository = Mockito.mock(SectionRepository.class);
    SectionServiceImpl sectionService;

    @BeforeEach
    void setup() {
        this.sectionService = new SectionServiceImpl(sectionRepository);
    }

    @Test
    void shouldGetIdBySectionCode() {
        Section section = new Section(1L, "FS", null, null);
        when(sectionRepository.getIdBySectionCode("FS")).thenReturn(1L);
        Long testId = sectionService.getIdBySectionCode("FS");
        assertEquals(1L, testId);
    }

}
