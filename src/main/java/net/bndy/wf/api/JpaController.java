package net.bndy.wf.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.domain.*;
import net.bndy.wf.repository.*;

@RestController
@RequestMapping("/api/jpa")
public class JpaController {
	@Autowired
	private CityRepository cityRep;
	
	//e.g. /api/jpa/getcities
	@RequestMapping("/getcities")
	public List<City> getCities() {
		return cityRep.findAll();
	}
	
	//e.g. /api/jpa/getcity/45
	@RequestMapping("/getcity/{id}")
	public City getCity(@PathVariable("id") long id) {
		return cityRep.findOne(id);
	}
	
	//e.g. /api/jpa/getcitiesbycountry?id=6
	@RequestMapping("/getcitiesbycountry")
	public List<City> getCitiesByCountry(@RequestParam("id") int id) {
		return cityRep.findByCountryId(id);
	}
	
	//e.g. /api/jpa/error
	@RequestMapping("/error")
	public Object getError() throws Exception{
		throw new Exception("App Exception");
	}
}
