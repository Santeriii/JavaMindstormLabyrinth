package view;



import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lejos.robotics.geometry.Line;

public class Naytto extends Canvas {

	private GraphicsContext gc;
	
	private int x = 0;
	private int y = 0;
	
	public Naytto(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
	}

	public void piirräSeinät(int x, int y, int korkeus, int leveys){
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, korkeus, leveys);
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, korkeus, leveys);
	}
	
	public void piirräEste(double leveys, int x1, int y1, int x2, int  y2) {
		gc.setFill(Color.BLACK);
		gc.setLineWidth(leveys);
		
		//gc.strokeLine(x1, y1, x2, y2);
		
		gc.fillRect(x1, y1, 30, 30);
		
	}
	
	public void piirräPisteet(int x, int y) {
		gc.setFill(Color.BLACK);
		gc.fillOval(x, y, 3, 3);
		
	}
	
	public void piirräPallo() {
		// tyhjennä edelliset pallot
		gc.setFill(Color.BLUE);
		gc.fillOval(x, y, 6, 6);
	}

	public void siirräPallo(int dx, int dy) {
		x = dx;
		y = dy;
		piirräPallo();
	}
}


