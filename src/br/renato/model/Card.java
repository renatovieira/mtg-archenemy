package br.renato.model;

public class Card {
	private Long id;
	private String name;
	private String imagefilename;
	private int ongoing;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOngoing() {
		return ongoing;
	}
	public void setOngoing(int ongoing) {
		this.ongoing = ongoing;
	}
	public String getImageFileName() {
		return imagefilename;
	}
	public void setImageFileName(String filename) {
		this.imagefilename = filename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id + "-" + imagefilename + " " + ongoing;
	}
}
