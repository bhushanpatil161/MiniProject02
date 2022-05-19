package in.ashokit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "COUNTRY_MASTER")
@Data
public class CountryMasterEntity {

	@Id
	@Column(name = "COUNTRY_ID")
	private Integer countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;
	
	
}
