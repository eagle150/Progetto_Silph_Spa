package it.uniroma3.siw.silphspa.services;

import java.util.List;

import it.uniroma3.siw.silphspa.model.Fotografia;

public interface ShoppingCartService {

    void aggiungiFotografia(Fotografia fotografia);

    void rimuoviFotografia(Fotografia fotografia);

    List<Fotografia> getFotografieNelCarrello();
    
    void azzeraCarrello();
    
}