package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import model.Cell;

public class PC extends Thread{
	
	HashMap<String, ArrayList<Cell>> häsmäp = new HashMap<>();
	private Naytto naytto;
	Socket s = null;
	DataOutputStream out = null;
	DataInputStream in = null;
	
	public PC(Naytto naytto, Socket s, DataOutputStream out, DataInputStream in, HashMap häsmäp) {
		this.naytto = naytto;
		this.in = in;
		this.out = out;
		this.s = s;
		this.häsmäp = häsmäp;
	}
	
	public void run() {
		
		
	try {
		
		 for (Entry<String, ArrayList<Cell>> h : häsmäp.entrySet()) {
	        	System.out.println(h.getKey());
	        	for (Cell p : h.getValue()) {
	        		System.out.println(p.toString());
	        	}
	     }
		 
		 ArrayList<Cell> solu = häsmäp.get("Esteet");
		
		Scanner lukija = new Scanner(System.in);
		
		System.out.println("Määritetään rata");
		System.out.println("Kuinka monta estettä radalla on?");
		int esteet = Integer.parseInt(lukija.nextLine());
		
		int r = 0;
		Line[] janat = new Line[(4 + (häsmäp.get("Esteet").size() * 4))];
		
		System.out.println("Anna rectanglen pienin x");
		float x = -20;
		System.out.println("Anna rectanglen pienin y");
		float y = -20;
		System.out.println("Anna rectanglen x:n pituus");
		float w = 300;
		System.out.println("Anna rectanglen y:n pituus");
		float h = 300;
		
		naytto.piirräSeinät((int)x, (int)y, (int)w, (int)h);
		
		naytto.piirräPallo();
		
		Rectangle suorakulmio = new Rectangle(x, y, w, h);
		
		janat[0] = new Line(x, y, w, x);
		janat[1] = new Line(w, y, w, h);
		janat[2] = new Line(x, h, w, h);
		janat[3] = new Line(x, h, x, y);
		
		/*while (r <= 3) {
			System.out.print("Anna kartan sivun eka x");
			int ekax = Integer.parseInt(lukija.nextLine());
			System.out.print("Anna kartan sivun eka y");
			int ekay = Integer.parseInt(lukija.nextLine());
			System.out.print("Anna kartan sivun toka x");
			int tokax = Integer.parseInt(lukija.nextLine());
			System.out.print("Anna kartan sivun toka y");
			int tokay = Integer.parseInt(lukija.nextLine());
			janat[r] = new Line(ekax,ekay,tokax,tokay);
		}*/
		
		for (int a = 0, i = 4; i < janat.length; a++) {
			
			Cell cell = solu.get(a);
			
			Rectangle este = new Rectangle(cell.getX() * 30, cell.getY() * 30, 30, 30);
			Line[] lineNeliöt = teeNeliö(este);
			System.out.println("janat pituus " + janat.length);
			for (int ö = 0; ö < lineNeliöt.length; i++, ö++) {
				System.out.println("i " + i + " ö " + ö);
				janat[i] = lineNeliöt[ö];
			}
			
			//janat[i] = new Line(este.x, este.y, este, este.getMaxY());
		}
		
		
		System.out.println(janat);
		System.out.println(suorakulmio);
		
		LineMap kartta = new LineMap(janat, suorakulmio);
		
		System.out.println(kartta);
		System.out.println(out);
		
		kartta.dumpObject(out);
		out.flush();
		
		
		System.out.println("Anna pisteiden määrä");
		int pisteet = Integer.parseInt(lukija.nextLine());
		
		int määrä = 0;
		int i = 0;
		
		System.out.println(kartta.getLines());
		
		out.writeInt(pisteet);
		out.flush();
		
		ArrayList<Cell> alkupiste = häsmäp.get("Alkupiste");
		ArrayList<Cell> loppupiste = häsmäp.get("Loppupiste");
		
		Cell alkup = alkupiste.get(0);
		Cell loppup = loppupiste.get(0);
		
		Waypoint[] alkujaloppu = new Waypoint[2];
		
		alkujaloppu[0] = new Waypoint(alkup.getX() * 30, alkup.getY() * 30);
		alkujaloppu[1] = new Waypoint(loppup.getX() * 30, loppup.getY() * 30);
		
		System.out.println(alkup.getX()+ " " +alkup.getY());
		System.out.println(loppup.getX()+ " " +loppup.getY());
		
		
		while (i < pisteet) {
			Waypoint havaintopiste = alkujaloppu[i];
			havaintopiste.dumpObject(out);
			out.flush();
		    naytto.piirräPisteet((int)alkujaloppu[i].getX(), (int)alkujaloppu[i].getY());
			i++;
		}
		
		String k;
		Path polku;
		
		/*while (true) {
			
			k = in.readUTF();
			System.out.println(k);
			polku = new Path();
			polku.loadObject(in);
		}*/
		
		while (true) {
			Pose paikka = new Pose();
			
			paikka.loadObject(in);
			
			System.out.println(paikka.getX() + " " + paikka.getY());
			
			naytto.siirräPallo((int)paikka.getX(), (int)paikka.getY());
			
			/*polku = new Path();
			polku.loadObject(in);*/
			
			/*polku.forEach(wp -> {
				System.out.println(wp.x + "," + wp.y);
				mathias.siirräPallo((int) wp.x, (int) wp.y);
			});*/
		}
		
		
		/*Waypoint wp1 = new Waypoint(0,0);
		Waypoint wp2 = new Waypoint(140,80);
		Waypoint wp3 = new Waypoint(140,0);
		Waypoint wp4 = new Waypoint(0,0);
		
		System.out.println(määrä);
		
		wp1.dumpObject(out); // huom! Kyseessä olion metodi!
		out.flush();
		wp2.dumpObject(out);
		out.flush();
		wp3.dumpObject(out);
		out.flush();
		wp4.dumpObject(out);
		
		System.out.println(määrä);
		
		out.flush();
		
		System.out.println(määrä);
		
		while (määrä < 4) {
			String i = in.readUTF();
			System.out.println(i);
			
			
			määrä++;
		}*/
		//s.close();
		
		//for (int i = 1; i <= 5; i++){ // Kirjoitetaan output-virtaan luvut 1-5.
		//	out.writeInt(i);
		//	out.flush(); // aina kirjoittamisen jälkeen
		//	}
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public Line[] teeNeliö (Rectangle rectangle){
		
		int w = (int)rectangle.getMaxX();
		int h = (int)rectangle.getMaxY();
		int x = (int)rectangle.getMinX();
		int y = (int)rectangle.getMinY();
		
		System.out.println(w + " " + h + " " +  x + " " + y);
		
		Line[] neliö = new Line[4];
		
		neliö[0] = new Line(x, y, w, x);
		neliö[1] = new Line(w, y, w, h);
		neliö[2] = new Line(x, h, w, h);
		neliö[3] = new Line(x, h, x, y);
		
		naytto.piirräEste(3, x, y, w, h);
		
		return neliö;
	}
}
