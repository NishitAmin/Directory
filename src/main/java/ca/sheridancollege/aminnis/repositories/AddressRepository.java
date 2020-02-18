package ca.sheridancollege.aminnis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.aminnis.beans.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
