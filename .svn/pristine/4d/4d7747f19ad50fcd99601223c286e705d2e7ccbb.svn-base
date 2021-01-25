package model;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class Cell extends StackPane implements Serializable {	

	private static final long serialVersionUID = 2756775801514031640L;
	
	@Column(name="cell_column")
    int column;
	@Column(name="cell_row")
    int row;
	
	@Column(name="cell_property")
	String property;

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;

        getStyleClass().add("cell");

        Label label = new Label(this.toString());

        getChildren().add(label);

        setOpacity(0.9);
    }
    
    public Cell() {
    	super();
    }

    public int getX() {
		return column;
	}

	public int getY() {
		return row;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getProperty() {
		return this.property;
	}

	public void highlight(String valittu) {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-highlight-" + this.property);
        this.property = null;

        // add style
        getStyleClass().add("cell-highlight-" + valittu);
        this.property = valittu;
    }

    public void unhighlight() {
        getStyleClass().remove("cell-highlight-" + this.property);
        this.property = null;
    }

    public void hoverHighlight(String valittu) {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-hover-highlight-" + this.property);

        // add style
        getStyleClass().add("cell-hover-highlight-" + valittu);
    }

    public void hoverUnhighlight(String valittu) {
        getStyleClass().remove("cell-hover-highlight-" + valittu);
    }
    
    public Cell getCellByStyle(String type) {
    	ObservableList<String> style = getStyleClass();
    	if (style.toString().contains(type)) {
    		return this;
    	}
    	return null;
    }
    
    public String toString() {
        return this.column + "/" + this.row;
    }
    
}
