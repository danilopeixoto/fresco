package com.mercadolibre.danilo_peixoto.unit.beans;

import com.mercadolibre.danilo_peixoto.dtos.SampleDTO;
import com.mercadolibre.danilo_peixoto.beans.RandomSampleBean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
