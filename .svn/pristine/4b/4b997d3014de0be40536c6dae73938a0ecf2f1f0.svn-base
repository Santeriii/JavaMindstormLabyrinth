package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cell;
import model.Grid;
import model.GridAccessObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;

public class Main extends Application {
	
	GridAccessObject gridDAO;
	MouseGestures mg;

    int rows = 10;
    int columns = 10;
    double width = 600;
    double height = 600;
    
    String[] tyypit = {"Alkupiste", "Loppupiste", "Este"}; // 0: Alkupiste, 1: Loppupiste, 2: Este. Jos CSS muuttaa niin tarvii vain tätä taulukkoa muokata
    String valittu = tyypit[0];
    
	@Override
	public void init(){
		gridDAO = new GridAccessObject();
		mg = new MouseGestures(this);
	}
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Naytto näyttö = new Naytto(300,300);
        	
        	System.out.println("Luodaan yhteys");
			Socket s = new Socket("10.0.1.1", 1111);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());
			System.out.println("Yhteys luotu");
        	
            StackPane root = new StackPane();

            // create grid
            Grid grid = new Grid(columns, rows, width, height, tyypit);
            
            Button alkuPisteBtn = new Button(tyypit[0]);
            
            alkuPisteBtn.setOnAction((event) -> {
            	valittu = tyypit[0];
            });
            
            Button loppuPisteBtn = new Button(tyypit[1]);
            
            loppuPisteBtn.setOnAction((event) -> {
            	valittu = tyypit[1];
            });
            
            Button esteetBtn = new Button(tyypit[2]);
            
            esteetBtn.setOnAction((event) -> {
            	valittu = tyypit[2];
            });
            
            Button tyhjennäBtn = new Button("Tyhjennä");
            
            tyhjennäBtn.setOnAction((event) -> {
            	grid.unhighlight();
            });
            
            Button valmisBtn = new Button("Valmis");
            
            valmisBtn.setOnAction((event) -> {
            	grid.finishedMap();
            	gridDAO.createGrid(grid);
            	PC pc = new PC(näyttö, s, out, in, grid.finishedMap());
            	pc.start();
            });
            

            // fill grid
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {

                    Cell cell = new Cell(column, row);

                    mg.makePaintable(cell);

                    grid.add(cell, column, row);
                }
            }

            root.getChildren().addAll(grid);
            root.getChildren().add(alkuPisteBtn);
            StackPane.setAlignment(alkuPisteBtn, Pos.BOTTOM_LEFT);
            root.getChildren().add(loppuPisteBtn);
            StackPane.setAlignment(loppuPisteBtn, Pos.BOTTOM_CENTER);
            root.getChildren().add(esteetBtn);
            StackPane.setAlignment(esteetBtn, Pos.BOTTOM_RIGHT);
            root.getChildren().add(valmisBtn);
            StackPane.setAlignment(valmisBtn, Pos.TOP_RIGHT);
            root.getChildren().add(tyhjennäBtn);
            StackPane.setAlignment(tyhjennäBtn, Pos.CENTER_RIGHT);
            
            /*GridPane grid2 = new GridPane();
			
			root.setBottom(grid2);*/

            // create scene and stage
            
            root.setId("pane");
            Scene scene = new Scene(root, width + 150, height + 100);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            
            BorderPane root2 = new BorderPane();
            Scene scene2 = new Scene(root2,400,400);
            
            Stage secondStage = new Stage();
            root2.setCenter(näyttö);
            secondStage.setScene(scene2);
            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getValittu() {
    	return this.valittu;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
