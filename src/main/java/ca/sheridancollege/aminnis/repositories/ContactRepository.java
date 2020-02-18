package ca.sheridancollege.aminnis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.sheridancollege.aminnis.beans.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("select c from Contact c where c.fname = :fname and c.lname = :lname")
	  public List<Contact> findByLnameAndFname(String lname, String fname);
}