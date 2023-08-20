package com.restcountries.assignment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.restcountries.assignment.service.CountriesService;

@RestController
@RequestMapping("/api")
public class CountriesController {

	@Autowired
	private CountriesService countryService;

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Map<String, Object>>> fetchByName(@PathVariable("name") String name) {
		String Url = "https://restcountries.com/v3.1/name/" + name;
		return ResponseEntity.ok(countryService.findCountry(Url));
	}

	@GetMapping("/countries")
	public ResponseEntity<List<String>> getCountries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(value = "population>=", required = false) Integer population,
			@RequestParam(value = "area>=", required = false) Double area,
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "sortBy", required = false, defaultValue = "area") String sortBy,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder) {

		// Process the filter parameters and fetch the countries based on the provided
		// filters
		// ...
		return ResponseEntity
				.ok(countryService.fetchByFilters(page, size, language, area, population, sortBy, sortOrder));
	}

}
