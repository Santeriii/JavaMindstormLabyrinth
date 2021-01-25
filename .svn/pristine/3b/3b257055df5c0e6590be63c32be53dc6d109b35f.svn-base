package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.scene.layout.Pane;

import javax.persistence.*;

@Entity
@Table(name="Grid_Table") 
public class Grid extends Pane {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="grid_name")
	String name;
	
	@Column(name="grid_rows")
    int rows;
	@Column(name="grid_columns")
    int columns;

	@Column(name="grid_width")
    double width;
	@Column(name="grid_height")
    double height;
	
	@ElementCollection
    Collection<Cell> cellsList = new ArrayList<Cell>();

	@Transient
    Cell[][] cells;
	
	@Transient
	String[] tyypit;
      
    public <T> List<T> twoDArrayToList(T[][] twoDArray) {
        List<T> list = new ArrayList<T>();
        for (T[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }

    public Grid(int columns, int rows, double width, double height, String[] tyypit) {
        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;
        this.tyypit = tyypit;

        cells = new Cell[rows][columns];
    }

    /**
     * Add cell to array and to the UI.
     */
    public void add(Cell cell, int column, int row) {

        cells[row][column] = cell;

        double w = width / columns;
        double h = height / rows;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        getChildren().add(cell);

    }

    public Cell getCell(int column, int row) {
        return cells[row][column];
    }

    /**
     * Unhighlight all cells
     */
    public void unhighlight() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col].unhighlight();
            }
        }
    }
    
    public HashMap<String, ArrayList<Cell>> finishedMap() {
    	HashMap<String, ArrayList<Cell>> map = new HashMap<>();
    	ArrayList<Cell> alkuPisteLista = new ArrayList<>();
    	ArrayList<Cell> loppuPisteLista = new ArrayList<>();
    	ArrayList<Cell> esteLista = new ArrayList<>();
    	Cell cell;
    	String tyyppi;
    	
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
            	for (String vaihtoehto : tyypit) {
            		cell = cells[row][col].getCellByStyle(vaihtoehto);
            		if (cell != null) {
            			tyyppi = cell.getProperty();
            			if (tyyppi == tyypit[0]) {
            				alkuPisteLista.add(cell);
            			} else if (tyyppi == tyypit[1]) {
            				loppuPisteLista.add(cell);
            			} else if (tyyppi == tyypit[2]) {
            				esteLista.add(cell);
            			}
            		}
            	}	
            }
        }
        
        map.put("Alkupiste", alkuPisteLista);
        map.put("Loppupiste", loppuPisteLista);
        map.put("Esteet", esteLista);
        
        for (Entry<String, ArrayList<Cell>> h : map.entrySet()) {
        	System.out.println(h.getKey());
        	for (Cell p : h.getValue()) {
        		System.out.println(p.toString());
        	}
        }
        cellsList = twoDArrayToList(cells);
        
        return map;
    }
}
