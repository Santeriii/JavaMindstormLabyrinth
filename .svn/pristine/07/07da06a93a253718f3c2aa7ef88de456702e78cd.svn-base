package model;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class GridAccessObject {
	SessionFactory istuntotehdas = null;
	
	public GridAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
			System.out.println("Istuntotehdas valmis");
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void finalize() {
		try {
			if (istuntotehdas != null) {
				istuntotehdas.close();
				System.out.println("Istuntotehdas suljettu.");
			}
		} catch (Exception e) {
			System.err.println("Istuntotehtaan sulkeminen ei onnistunut.");
			e.printStackTrace();
		}
	}
	
	public boolean createGrid(Grid map) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			
			istunto.saveOrUpdate(map);
			
			transaktio.commit();
			
			istunto.close();
			
			return true;
		} catch (Exception e) {
			//if (transaktio != null) transaktio.rollback();
			e.printStackTrace();
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Grid[] readGrids() {
		ArrayList<Grid> lista = new ArrayList<Grid>();
		try (Session istunto = istuntotehdas.openSession()) {
			lista.addAll((ArrayList<Grid>) istunto.createQuery( "from grid_table" ).list());
			
			istunto.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Grid[] palautusLista = new Grid[lista.size()];
		
		return (Grid[]) lista.toArray(palautusLista);
	}
	
}
