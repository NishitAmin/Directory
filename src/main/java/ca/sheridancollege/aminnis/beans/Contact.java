package ca.sheridancollege.aminnis.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Length(min=3, max=20)
	private String fname;
	@NotNull
	@Length(min=3, max=20)
	private String lname;
	@Pattern(regexp="(^$|[0-9]{10})", message="Enter a valid phone number i.e. XXX XXX XXXX")
	private String phoneno;
	private String birthday;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Address address;
}