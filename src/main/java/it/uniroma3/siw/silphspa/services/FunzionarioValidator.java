package it.uniroma3.siw.silphspa.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.silphspa.model.Funzionario;

@Component
public class FunzionarioValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Funzionario.class.equals(arg0);
	}

	@Override
	public void validate(Object o, Errors error) {
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "password", "required");
	}

}
