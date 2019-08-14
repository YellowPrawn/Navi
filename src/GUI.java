import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GUI extends JFrame{
		
	neuralNetwork NNagent = new neuralNetwork();
	qLearnV2 Qagent = new qLearnV2();
	
	public GUI() {
		
		this.setTitle("Navi");
		this.setSize(main.display[0],main.display[1]);//added number = insets side,top
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		/*Insets insets = this.getInsets();
		System.out.println(insets);*/ //debugging for insets 
		Board board = new Board(); // links 'board' class to the window
		this.setContentPane(board); 
		
	}
	
	public class Board extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.blue); //start point
			g.fillRect(main.start[0]*main.resolution, main.start[1]*main.resolution, main.resolution, main.resolution);
			
			g.setColor(Color.red); //end point
			g.fillRect(main.end[0]*main.resolution, main.end[1]*main.resolution, main.resolution, main.resolution);
			
			g.setColor(Color.black); 
			for(int i = 0; i<main.grid[0]; i++) {//vertical grid line
				g.fillRect(i*main.resolution,0,1,main.display[1]);
			}
			for(int i = 0; i<main.grid[1]; i++) {//horizontal grid line
				g.fillRect(0,i*main.resolution,main.display[0],1);
			}
			
			for(int i = 0; i<main.obstacles.size(); i++) {//drawing obstacles
				g.fillRect((main.obstacles.get(i)[0])*main.resolution, (main.obstacles.get(i)[1])*main.resolution, main.resolution, main.resolution);
			}
			for(int i=0; i<NNagent.history.size();i++) {
				g.setColor(Color.green);
				g.fillRect((NNagent.history.get(0)[0]*main.resolution)+(main.resolution/4), (NNagent.history.get(1)[1]*main.resolution)+(main.resolution/4), main.resolution/2, main.resolution/2);
			}
			for(int i=0; i<Qagent.history.size();i++) {
				g.setColor(Color.magenta);
				g.fillRect((Qagent.history.get(0)[0]*main.resolution)+(main.resolution/4), (Qagent.history.get(1)[1]*main.resolution)+(main.resolution/4), main.resolution/2, main.resolution/2);
			}
		}
	}
}