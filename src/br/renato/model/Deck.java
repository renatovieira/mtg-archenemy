package br.renato.model;


public class Deck {
	private Long id;
	private String name;
	private String cardsidseparatedbycommas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCards() {
		return cardsidseparatedbycommas;
	}
	public void setCards(String cidsbc) {
		this.cardsidseparatedbycommas = cidsbc;
	}
}
