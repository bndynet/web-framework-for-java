package net.bndy.wf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="city_id")
    private Long cityId;

    @Column(name="city", nullable = false)
    private String city;

    public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name="country_id", nullable = false)
    private Integer countryId;
    
    @Column(name="last_update", nullable = false)
    private java.util.Date lastUpdate;
}