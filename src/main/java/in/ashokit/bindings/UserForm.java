package in.ashokit.bindings;

import java.time.LocalDate;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
public class UserForm {

	private String fName;

	private String lName;

	private String email;

	private Long phno;

	private LocalDate DOB;

	private String gender;

	private Integer cityId;

	private Integer stateId;

	private Integer countryId;

}
