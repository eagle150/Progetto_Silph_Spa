package it.uniroma3.siw.silphspa.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.silphspa.model.SearchQuery;

@Component
public class SearchQueryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchQuery.class.equals(clazz.getClass());
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "query", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "required");
	}
	
}
