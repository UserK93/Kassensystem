package com.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Funktionen{

	
	
	public void ShowWarehouse(String url, String user,String password, Connection conn, Statement stmt,String query, ResultSet rs) throws SQLException {

		
	query = "SELECT Produktname, Preis, Anzahl from Lager ";
	rs = stmt.executeQuery(query);
	
	int columns = rs.getMetaData().getColumnCount();
	for(int i = 1; i<=columns; i++)
		System.out.print(String.format("%-15s", rs.getMetaData().getColumnLabel(i)));
	
	System.out.println();
	System.out.println("----------------------------------------------------------------");
	
	while(rs.next()) {
		for(int i = 1; i<=columns; i++)
			System.out.print(String.format("%-15s", rs.getString(i)));
		System.out.println();
	}
	
	rs.close();
	//stmt.close();
	
	}

	
	public void Buy(String url, String user,String password, Connection conn,
					Statement stmt,String query, ResultSet rs, Produkt produkt,
					Scanner sc,ArrayList<Produkt> produktList,String ja, String nein)
					throws IOException, SQLException {


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd - HH-mm-ss "); //Für Windows, da doppelpunkt nicht möglich
		//SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd"); //F�r DB
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss "); //Für Kassenzettel
		Date currentTime = new Date();
		File file = new File(formatter.format(currentTime)+".txt"); //Datei, in die geschrieben werden soll
		BufferedWriter writer = new BufferedWriter(new FileWriter(file)); //Erzeugen eines Writers für Textdateien
		Statement stmt2 ;
		double summe = 0;
		double preis = 0;

		Produkt produkt1 = new Produkt();

	String antwort = "y";
	System.out.println("Nur Bestandskunden können derzeit Produkte kaufen!");
	System.out.println("Vorname des Kunden: ");
	String KuVorname = sc.next();
	System.out.println("Nachname des Kunden: ");
	String KuNachname = sc.next();
	KuVorname = KuVorname.substring(0, 1).toUpperCase() + KuVorname.substring(1);
	KuNachname = KuNachname.substring(0, 1).toUpperCase() + KuNachname.substring(1);
	writer.write("KUNDE:"+KuVorname+" "+KuNachname+"\t\t\t\t"+formatter3.format(currentTime) );
	writer.newLine();
	writer.newLine();
	writer.write("PRODUKT\t\t PREIS");
	writer.newLine();

	while (antwort.equals("y")) {
		byte pruefen= 0;

		System.out.println("Aktuelle Produkte zur Auswahl: ");

	/*	while(rs.next()) {
			for(int i = 1; i<=columns; i++)
				System.out.print(String.format("%-15s", rs.getString(i)));
			System.out.println();
		}*/

		query = "SELECT * FROM Lager"; //+
		// " WHERE Produktname = "+"'"+pr+"'";
		rs = stmt.executeQuery(query);


		//Hinzufügen der Produkte der Datenbank in einer Liste
		while (rs.next()) {
			produkt = new Produkt(rs.getString("id"), rs.getString("Produktname"), rs.getDouble("preis"), rs.getString("anzahl"));
			produktList.add(produkt);


		}
		System.out.println(" " + produktList+"\n");

		// Prüfe ob ausgewähltes Produkt vorhanden, wenn ja fahre fort, wenn nein, dann wiederhole bis eine korrekte Eingabe gemacht worden ist
		while (pruefen!=1) {
		System.out.println("Produkt: ");
		String pr = sc.next();
		pr = pr.substring(0, 1).toUpperCase() + pr.substring(1);//damit sichergestellt wird, das Produkte automatische Groß geschrieben werden


			for (int i = 0; i < produktList.size(); i++) {


				// Prüfe ob ausgewähltes Produkt vorhanden, wenn ja reduziere die Anzahl in der Datenbank um die gekaufte Anzahl
				if (produktList.get(i).getName().equals(pr)) {

					produkt1 = new Produkt(produktList.get(i).getId(), produktList.get(i).getName(), produktList.get(i).getPreis(), produktList.get(i).getAnzahl());
					pruefen = 1;
				}
			}

			if (pruefen!=1) System.out.println("Produkt existiert nicht, bitte erneut eingeben!");
		}

		System.out.println("Anzahl: ");
		int anz = sc.nextInt();

			Statement stmt3 = conn.createStatement();
			String SQLCommand2 = "SELECT id from Kunden where vorname = '" + KuVorname + "' AND nachname ='"+KuNachname+"'";
			rs = stmt3.executeQuery(SQLCommand2);
			stmt2 = conn.createStatement();
			rs.next();

			// Anzahl nach Kauf verringern
			String SQLCommand = "UPDATE Lager " + "SET ANZAHL =ANZAHL - " + anz + " "
					+ "WHERE Produktname =" + "'" + produkt1.getName() + "';";

			stmt2.execute(SQLCommand);


			int id = rs.getInt(1);

			// Mit der Anzahl der gekauften Produkte multiplizieren
			preis = produkt1.getPreis() * anz;
			DecimalFormat df = new DecimalFormat("###.#");
			//In Tabelle Testeinkauf schreiben mit aktuellem Datum
			query = "INSERT INTO testeinkauf (PersonID, Produktname,preis, anzahl, Datum) VALUES (" + id + ",'" + produkt1.getName() + "'," + preis + "," +
					anz + ",DATE_FORMAT(Now(),'%Y-%m-%d'))";
			stmt2.execute(query);

			writer.newLine();
			writer.write(produkt1.getName() + " x " + df.format(anz) + " " + preis + " Euro \t\t\t");
			writer.newLine();

			System.out.println("Weiteres Produkt hinzufügen?: \n\n");
			System.out.println("Ja: y Nein: n \n");
			antwort = sc.next();
			summe = summe + preis;
		}

// Wenn keine weiteres Produkt mehr gekauft werden soll, dann den Rest des Kassenbons vervollständigen
		writer.newLine();
		writer.newLine();
		writer.write("______________________________________________");
		writer.newLine();
		writer.newLine();
		writer.write("Gesamtpreis " + summe + " Euro");

		writer.close();
		System.out.println("Kassenbon erfolgreich erstellt!");
		rs.close();
	}

		

	public void AddProduct(Statement stmt,String query, ResultSet rs, Scanner sc,String antwort, String nein) throws SQLException {
	
	while (true) {

		System.out.println("Welches Produkt möchten Sie hinzufügen?");
		
		String ProduktnamenEingabe = sc.next();
		System.out.println("Preis:\n");
		Double PreisEingabe = sc.nextDouble();
		System.out.println("Anzahl: \n");
		int AnzahlEingabe = sc.nextInt();

		query = " INSERT INTO Lager (Produktname,preis,anzahl)  VALUES ('"+ProduktnamenEingabe+"',"+PreisEingabe+","+AnzahlEingabe+")";

		System.out.println("Weiteres Produkt hinzufügen?: \n\n");
		System.out.println("Ja: y Nein: n \n");
		antwort = sc.next();


		try {
			stmt.execute(query);
		} catch (NoSuchElementException ioe) {
			System.out.println("schon vorhanden");
			ioe.printStackTrace();
		}

		if (antwort.equals(nein)) {break;}
				
				
				
			}

	}


public void DeleteProduct(Statement stmt,String query, ResultSet rs, Scanner sc,String antwort, String nein) throws SQLException {
	
	while (true) {
		
		query = "SELECT Produktname, Preis, Anzahl from Lager ";

    	rs = stmt.executeQuery(query);
    	
    	int columns = rs.getMetaData().getColumnCount();
    	for(int i = 1; i<=columns; i++)
    		System.out.print(String.format("%-15s", rs.getMetaData().getColumnLabel(i)));
    	
    	System.out.println();
    	System.out.println("----------------------------------------------------------------");
    	
    	while(rs.next()) {
    		for(int i = 1; i<=columns; i++)
    			System.out.print(String.format("%-15s", rs.getString(i)));
    		System.out.println();
    	}
    	
    	rs.close();
    	
    	System.out.println();
    	System.out.println("Welches Produkt soll gelöscht werden?");
    	
    	String ProduktnamenEingabe = sc.next();
    	
	    
		query = "DELETE from Lager WHERE Produktname = '"+ProduktnamenEingabe+"';";
		
		

		try {
		
		stmt.execute(query);
		
		
		
		}
		catch (NoSuchElementException ioe){
		    System.out.println("nicht vorhanden");
		    ioe.printStackTrace();
		} 
	        	
		System.out.println("Weiteres Produkt löschen?: \n\n");
		System.out.println("Ja: y Nein: n \n");
		antwort = sc.next();
		if (antwort.equals(nein)) {break;}

	
}

	}




public void ChangeProduct(Statement stmt,String query, ResultSet rs, Scanner sc,String antwort, String nein) throws SQLException {
	while ( true){
		System.out.println("Welches Produkt möchten Sie ändern");
		String ProduktnamenEingabe = sc.next();
		System.out.println("neuer Name:");
		String ProduktnamenAendern = sc.next();

		System.out.println("Preis:\n");
		String PreisAendern = sc.next();

		System.out.println("Anzahl: \n");
		int AnzahlAendern = sc.nextInt();
		
	    
		query = "UPDATE Lager SET Produktname ='"+ProduktnamenAendern+"', PREIS="+PreisAendern+", ANZAHL = "+AnzahlAendern
        	    + " WHERE Produktname = '"+ProduktnamenEingabe+"';";

		stmt.execute(query);
		System.out.println("Weiteres Produkt Ändern?: \n\n");
		System.out.println("Ja: y Nein: n \n");
		antwort = sc.next();
		if (antwort.equals(nein)) {break;}
	}
	
	
}

	
	public void SearchProduct(Statement stmt,String query, ResultSet rs, Scanner sc,String antwort, String nein) throws SQLException {
	
while ( true){
		
	System.out.println("Nach welchem Produkt möchten Sie suchen?");
	
	String ProduktnamenSuche = sc.next();
	
	query = "SELECT Produktname, Preis, Anzahl from Lager where Produktname = '"+ProduktnamenSuche+"' ;";
	rs = stmt.executeQuery(query);
	
	int columns = rs.getMetaData().getColumnCount();
	for(int i = 1; i<=columns; i++)
		System.out.print(String.format("%-15s", rs.getMetaData().getColumnLabel(i)));
	
	System.out.println();
	System.out.println("----------------------------------------------------------------");
	
	while(rs.next()) {
		for(int i = 1; i<=columns; i++)
			System.out.print(String.format("%-15s", rs.getString(i)));
		System.out.println();
	}
	
	rs.close();
	//stmt.close();

	System.out.println("Weiteres Produkt suchen?: \n\n");
	System.out.println("Ja: y Nein: n \n");
	antwort = sc.next();
	if (antwort.equals(nein)) {break;}
	
}
}
	
	
	
	
	
	public void Search(Statement stmt,String query, ResultSet rs, Scanner sc,String antwort, String nein) throws SQLException {
		while(true) {
		
		int SuchEin;
		System.out.println("Nach was möchten Sie suchen?\n");
		System.out.println("1:Datum \n\n2:Vorname \n\n3:Nachname \n\n4:Produkt");	
		
		String Suchkriterium = sc.next();
		SuchEin =Integer.parseInt(Suchkriterium);
		//String DatumSuche="Datum",VornameSuche="Vorname",NachnameSuche="Nachname",ProduktSuche="Produkt";
		//String D2="datum",V2="vorname",N2="nachname",P2="produkt";
	    
	    if (SuchEin == 1) {	    
	    
	    System.out.println("Geben Sie nun das gewünschte Datum ein");
		System.out.println("Beispielformat: 2021-09-03");

	    String Suche = sc.next();
	    
		query = "SELECT testeinkauf.Produktname,testeinkauf.Preis,testeinkauf.Anzahl,Kunden.Vorname,Kunden.Nachname,Kunden.Strasse,Kunden.PLZ,Kunden.Wohnort,testeinkauf.Datum "
				+ "from testeinkauf inner join Kunden on testeinkauf.personid=Kunden.id where Datum ='"+Suche+"'";
	    }
	    
	    if (SuchEin == 2){
		    
		    System.out.println("Geben Sie nun den gewünschten Vornamen ein");
		    String Suche = sc.next();
		    
		    query = "SELECT testeinkauf.Produktname,testeinkauf.Preis,testeinkauf.Anzahl,Kunden.vorname,Kunden.nachname,Kunden.Strasse,Kunden.PLZ,Kunden.Wohnort,testeinkauf.Datum "
					+ "from testeinkauf inner join Kunden on testeinkauf.personid=Kunden.id where vorname ='"+Suche+"'";
		    }
	    
	    if (SuchEin == 3) {
		    
		    System.out.println("Geben Sie nun den gewünschten Nachnamen ein");
		    String Suche = sc.next();
		    
		    query = "SELECT testeinkauf.Produktname,testeinkauf.Preis,testeinkauf.Anzahl,testeinkauf.datum,Kunden.vorname,Kunden.nachname,Kunden.Strasse,Kunden.PLZ,Kunden.Wohnort "
					+ "from testeinkauf inner join Kunden on testeinkauf.personid=Kunden.id where nachname ='"+Suche+"'";
		    }
	    
	    if (SuchEin == 4) {
		    
		    System.out.println("Geben Sie nun das gewünschte Produkt ein");
		    String Suche = sc.next();
		    
			query = "SELECT testeinkauf.Produktname,testeinkauf.Preis,testeinkauf.Anzahl,testeinkauf.Datum,Kunden.Vorname,Kunden.Nachname,Kunden.Strasse,Kunden.PLZ,Kunden.Wohnort "
					+ "from testeinkauf inner join Kunden on testeinkauf.personid=Kunden.id where produktname ='"+Suche+"'";
		    }

		
		
		rs = stmt.executeQuery(query);
		
		int columns = rs.getMetaData().getColumnCount();
    	for(int i = 1; i<=columns; i++)
    		System.out.print(String.format("%-15s", rs.getMetaData().getColumnLabel(i)));
    	
    	System.out.println();
    	System.out.println("----------------------------------------------------------------------"
    			+ "-------------------------------------------------------------------------------");
    	
    	while(rs.next()) {
    		for(int i = 1; i<=columns; i++)
    			System.out.print(String.format("%-15s", rs.getString(i)));
    		System.out.println();
    	}
    	
		System.out.println("Weitere Rechnung suchen?: \n\n");
		System.out.println("Ja: y Nein: n \n");
		antwort = sc.next();
		if (antwort.equals(nein)) {break;}
			
	
	
	
	}
	}

}
