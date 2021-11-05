package com.db;

public class Kunde {
	
	

	String vorname,nachname, strasse, wohnort;
	int plz; int id;
	
	 Kunde() {
	}
	
	public Kunde(int id, String vorname, String nachname, String strasse, String wohnort, int plz) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.wohnort = wohnort;
		this.plz = plz;
		this.id= id;
	} 
	
	public int getId() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getWohnort() {
		return wohnort;
	}
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	@Override
	public String toString() {
		return "Person [vorname=" + vorname + ", nachname=" + nachname + ", strasse=" + strasse + ", wohnort=" + wohnort
				+ ", plz=" + plz + "]";
	}

	
	 
	
}
