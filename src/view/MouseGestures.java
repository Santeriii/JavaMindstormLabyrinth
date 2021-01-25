package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import model.Cell;

public class MouseGestures {
	Main ui;
	String valittu;
    boolean showHoverCursor = true;
    
    public MouseGestures(Main ui) {
    	this.ui = ui;
    	
    	valittu = ui.getValittu();
    }
    
	public void makePaintable(Node node) {
        // that's all there is needed for hovering, the other code is just for painting
        if(showHoverCursor) {
            node.hoverProperty().addListener(new ChangeListener<Boolean>(){

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                	valittu = ui.getValittu();

                    //System.out.println( observable + ": " + newValue);

                    if(newValue) {
                        ((Cell) node).hoverHighlight(valittu);
                    } else {
                        ((Cell) node).hoverUnhighlight(valittu);
                    }

                    //for( String s: node.getStyleClass()) {
                    //    System.out.println( node + ": " + s);
                    //}
                }

            });
        }

        node.setOnMousePressed( onMousePressedEventHandler);
        node.setOnDragDetected( onDragDetectedEventHandler);
        node.setOnMouseDragEntered(onMouseDragEnteredEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

    	valittu = ui.getValittu();
        Cell cell = (Cell) event.getSource();

        if( event.isPrimaryButtonDown()) {
            cell.highlight(valittu);
        } else if( event.isSecondaryButtonDown()) {
            cell.unhighlight();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

        PickResult pickResult = event.getPickResult();
        Node node = pickResult.getIntersectedNode();

        if( node instanceof Cell) {

        	valittu = ui.getValittu();
            Cell cell = (Cell) node;

            if( event.isPrimaryButtonDown()) {
                cell.highlight(valittu);
            } else if( event.isSecondaryButtonDown()) {
                cell.unhighlight();
            }

        }

    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
    };

    EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {

        Cell cell = (Cell) event.getSource();
        cell.startFullDrag();

    };

    EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {

    	valittu = ui.getValittu();
        Cell cell = (Cell) event.getSource();

        if( event.isPrimaryButtonDown()) {
            cell.highlight(valittu);
        } else if( event.isSecondaryButtonDown()) {
            cell.unhighlight();
        }

    };

}
