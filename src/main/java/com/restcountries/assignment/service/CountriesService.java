package com.restcountries.assignment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.restcountries.assignment.exception.CountryNotFoundException;
import com.restcountries.assignment.exception.PageNotFoundException;

@Service
public class CountriesService {

	private final RestTemplate restTemplate;

	@Autowired
	public CountriesService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<Map<String, Object>> findCountry(String url) {

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<List<Map<String, Object>>>() {
					});

			return response.getBody();
		} catch (HttpClientErrorException.NotFound exception) {
			// Handle the "Not Found" exception
			throw new CountryNotFoundException("Not found");
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> fetchByFilters(int page, int size, String language, Double area, Integer population,
			String sortBy, String sortOrder) {

		String Url = "https://restcountries.com/v3.1/all";
		// Fetching the list of all countries
		List<Map<String, Object>> countriesData = findCountry(Url);
		// Filtered countries would add in this list
		List<Map<String, Object>> countries = new ArrayList<Map<String, Object>>();
		// Storing the pair of area and country name to sort them by area
		TreeMap<Double, String> areaMap = new TreeMap<Double, String>();
		// Storing the pair of population and country name to sort them by population
		TreeMap<Integer, String> populationMap = new TreeMap<Integer, String>();

		try {
			/* Processing countries data according to filters */
			for (Map<String, Object> country : countriesData) {

				Map<String, String> languageMap = (Map<String, String>) country.get("languages");
				Double Area = (Double) country.get("area");
				Integer Population = (Integer) country.get("population");

				// Applying filters on each country if it passes filter then it would add in
				// countries List
				if ((area == null || Area >= area) && (population == null || Population >= population)) {
					if (language != null && languageMap != null) {
						for (Map.Entry<String, String> entry : languageMap.entrySet()) {
							String value = entry.getValue();
							// Check if the value matches the desired value
							if (value != null && value.equalsIgnoreCase(language)) {
								countries.add(country);
								break; // break the loop if you only need to find the first occurrence
							}
						}
					} else if (language == null) { // if language filter is not provided by user then all countries
													// would add after passing above if condition
						countries.add(country);
					}
				}
			}

			/*
			 * As according to project requirement we have to fetch only the names of
			 * countries which passes filter, so here we are extracting common name of each
			 * country with area or population
			 */

			for (Map<String, Object> country : countries) {

				Map<String, Object> nameMap = (Map<String, Object>) country.get("name");
				String CommonName = (String) nameMap.get("common");

				if (sortBy.equalsIgnoreCase("population")) {
					Integer Population = (Integer) country.get("population");
					// Adding population as key in TreeSet to sort them by population
					populationMap.put(Population, CommonName);

				}

				else if (sortBy.equalsIgnoreCase("area")) {
					Double Area = (Double) country.get("area");
					// Adding area as key in TreeSet to sort them by area
					areaMap.put(Area, CommonName);
				}

			}

			// Applying sorting
			ArrayList<String> sortedNames = new ArrayList<String>();
			// Sorting on the basis of area
			if (sortBy.equalsIgnoreCase("area")) {
				if (sortOrder.equalsIgnoreCase("asc")) {
					// Adding the values which contain the name of countries in a ArrayList sorted
					// names
					sortedNames.addAll(areaMap.values());
				} else {
					sortedNames.addAll(areaMap.descendingMap().values());
				}
			}
			// Sorting on the basis of population
			else {
				if (sortOrder.equalsIgnoreCase("asc")) {
					sortedNames.addAll(populationMap.values());
				} else {
					sortedNames.addAll(populationMap.descendingMap().values());
				}
			}

			// Applying pagination by calling pagination method
			List<String> paginatedCountries = pagination(page, size, countries, sortedNames);

			return paginatedCountries;

		} catch (HttpClientErrorException.NotFound exception) {
			// Handle the "Not Found" exception
			throw new CountryNotFoundException("Not found");

		}
	}

	// Pagination method definition
	private List<String> pagination(int page, int size, List<Map<String, Object>> countries,
			Collection<String> sortedNames) {

		// It will throw exception if requested page exceeds total number of pages
		try {
			int startIndex = page * size;
			int endIndex = Math.min(startIndex + size, countries.size());

			return (((ArrayList<String>) sortedNames).subList(startIndex, endIndex));
		} catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
			// Handle the "Not Found" exception

			throw new PageNotFoundException("Your page number exceeds number of total pages");

		}

	}

}