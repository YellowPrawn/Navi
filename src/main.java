import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;

public class main implements Runnable{
	
	public static int[] grid = {10,10};
	public static int resolution = 100;
	public static int[] display = {main.grid[0]*resolution+8, main.grid[1]*resolution+31};
	
	public static int[] start = {0,0};
	public static int[] end = {8,8};
	
	//static int[][] obstacles = new int[ThreadLocalRandom.current().nextInt(0,(grid[0]*grid[1])/2)][2];//toggle for auto generation
	static Integer[][] obstacle = {{1,2},{6,4},{5,6},{3,8},{2,7},{3,3}};//toggle for manual generation
	
	public static ArrayList<Integer[]> obstacles = new ArrayList<Integer[]>();
	
	GUI gui = new GUI();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new main()).start();
		
		
		/*for(int i = 0; i<obstacle.length;i++) {//generating obstacles (toggle this section for random obstacles)
			int[] pos = new int[2];
			int[] temp = {rand(),rand()};	
			if(temp!=main.start||temp!=main.end){
				pos=temp;
			}
			obstacle[i][0]= pos[0];
			obstacle[i][1]= pos[1];
		}*/
		
	}
	
	@Override
	public void run() {
		while(true) {
			gui.repaint();
		}
		// TODO Auto-generated method stub
		
	}
	
	static int rand() {
		int rand = ThreadLocalRandom.current().nextInt(0,main.grid[0]);
		return rand;
	}
}
