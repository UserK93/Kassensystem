package com.db;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main{
	
	Funktionen x = new Funktionen();

	public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/test?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
        String user = "user";
        String password = "root";
        Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		String query = null;
		ResultSet rs = null;
		Funktionen x = new Funktionen();
		String ja= "y";
		String nein= "n";
		int eingabe=1;
		ArrayList<Produkt> produktList = new ArrayList<>();
		Produkt produkt = null;
		Scanner sc = new Scanner(System.in);


		while(true) {
		
		
		System.out.println("\n");
		System.out.println("Menü:\n\n");
		System.out.println("1: Lager anzeigen");
		System.out.println("2. Produkte Kaufen + Kassenzettel erstellen");
		System.out.println("3: Produkt Hinzufügen");
		System.out.println("4: Produkt Löschen");
		System.out.println("5: Produkt Ändern");
		System.out.println("6: Produkt Suchen");
		System.out.println("7. Rechnungen ansehen ");
		System.out.println("8 oder >8: Beenden\n");
		System.out.println("Bitte auswählen und bestätigen");
		
		
		
		eingabe = sc.nextInt();
		

		if (eingabe==1) {
			
			x.ShowWarehouse(url, user, password, conn, stmt, query, rs);
				
		}
		
		
		if (eingabe==2) {
			
			x.Buy(url, user, password, conn, stmt, query, rs, produkt, sc, produktList,ja, nein);
		}
		

		if(eingabe==3){

			x.AddProduct(stmt, query, rs, sc, ja, nein);
			
		}


		if (eingabe==4) {
		
			x.DeleteProduct(stmt, query, rs, sc, ja, nein);
				
		}
			

		if (eingabe==5) {
			
			x.ChangeProduct(stmt, query, rs, sc, ja, nein);
		}
		
		
		if (eingabe==6) {
			
			x.SearchProduct(stmt, query, rs, sc, ja, nein);
		}
		
		
		if(eingabe==7){
				
			x.Search(stmt, query, rs, sc, ja, nein);
			
		}
		
		
		
		else if (eingabe >7 ) System.exit(0);

    }
        
	}
	
	
}
