package com.db;

public class Produkt {
	
	double preis;
	String name;
	String id;
	String anzahl;
	
	public String getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(String anzahl) {
		this.anzahl = anzahl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	Produkt(){
		
		
	}
	
Produkt(String id,String n,double p,String anz){
		
		this.id=id;
		this.name=n;
		this.preis=p;
		this.anzahl=anz;
		
		
		
	}
	
	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Produkt [id=" + id + ", name=" + name + ", preis=" + preis + "]";
	}



	
	
	

}
