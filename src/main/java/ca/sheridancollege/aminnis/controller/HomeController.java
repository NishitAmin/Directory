package ca.sheridancollege.aminnis.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.aminnis.beans.Address;
import ca.sheridancollege.aminnis.beans.Contact;
import ca.sheridancollege.aminnis.repositories.AddressRepository;
import ca.sheridancollege.aminnis.repositories.ContactRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	private ContactRepository contactRepository;
	private AddressRepository addressRepository;
	
	@GetMapping("/")
	public String index (Model model) {
		model.addAttribute("contactList", contactRepository.findAll());
		model.addAttribute("contact", new Contact());
		return "index";
	}
	
	@GetMapping("/searchContacts")
	public String searchContacts (Model model) {
		model.addAttribute("contactList", contactRepository.findAll());
		model.addAttribute("contact", new Contact());
		return "searchContacts";
	}
	
	@PostMapping("/insertContacts")
	public String insertContacts (Model model, @ModelAttribute Contact contact) {
	
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Contact>> constraintViolations = validator.validate(contact);
		Set<ConstraintViolation<Address>> constraintViolations1 = validator.validate(contact.getAddress());
		
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Contact> violation : constraintViolations) {
				model.addAttribute(violation.getPropertyPath().toString(), violation.getMessage());
				model.addAttribute("contact", contact);
			}
		}
		if (constraintViolations1.size() > 0) {	
			for (ConstraintViolation<Address> violation : constraintViolations1) {
				model.addAttribute(violation.getPropertyPath().toString(), violation.getMessage());
				model.addAttribute("address", contact.getAddress());
			}
		}
		else {
			addressRepository.save(contact.getAddress());
			contactRepository.save(contact);
			model.addAttribute("contact", new Contact());
		}

		model.addAttribute("contactList", contactRepository.findAll());
		return "index";
	}
	
	@GetMapping("/editContact/{id}")
	public String editContact(Model model, @PathVariable Long id) {
		model.addAttribute("contactList", contactRepository.findAll());
		model.addAttribute("contact", contactRepository.findById(id).get());
		return "index";
	}
	
	@GetMapping("/deleteContact/{id}")
	public String deleteContact(Model model, @PathVariable Long id) {
		contactRepository.deleteById(id);
		model.addAttribute("contactList", contactRepository.findAll());
		model.addAttribute("contact", new Contact());
		return "index";
	}
	
	@GetMapping("/findByLastnameAndFirstname")
	public String findByLastnameAndFirstname (Model model, @RequestParam String lname, @RequestParam String fname) {
		List<Contact> contactList = contactRepository.findByLnameAndFname(lname, fname);
		model.addAttribute("contactList",  contactList);
		return "searchContacts";
	}
}
