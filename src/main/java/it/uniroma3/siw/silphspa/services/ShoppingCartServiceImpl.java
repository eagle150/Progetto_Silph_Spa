package it.uniroma3.siw.silphspa.services;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import it.uniroma3.siw.silphspa.model.Fotografia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private List<Fotografia> fotografie = new ArrayList<>();

	@Override
	public void aggiungiFotografia(Fotografia fotografia) {
		fotografie.add(fotografia);
	}

	
	@Override
	public void rimuoviFotografia(Fotografia fotografia) {
		if (fotografie.contains(fotografia)) {
			
			this.fotografie.remove(fotografia);
			

		} else {
			
			System.out.println("Fotografia non trovata!");
			
		}
	}


	@Override
	public List<Fotografia> getFotografieNelCarrello() {
		return Collections.unmodifiableList(fotografie);
	}
	
	@Override
	public void azzeraCarrello() {
		this.fotografie.clear();
	}
	
}