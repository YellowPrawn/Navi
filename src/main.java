import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;

public class main{
	public static int[] grid = {10,10};
	public static int resolution = 100;
	public static int[] display = {main.grid[0]*resolution+8, main.grid[1]*resolution+31};
	public static int[] start = {0,0};
	public static int[] end = {8,8};
	
	static Integer[][] obstacle = {{1,2},{6,4},{5,6},{3,8},{2,7},{3,3}};
	static Integer[][] obstacle2 ={{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()},{rand(),rand()}};//post training obstacles
	
	public static ArrayList<Integer[]> obstacles = new ArrayList<Integer[]>();
	public static ArrayList<Integer[]> obstacles2 = new ArrayList<Integer[]>();
	static long startTime;
	static long endTime;
	
	public static void main(String[] args) {
	
		System.out.println(Arrays.deepToString(obstacle2));
		obstacles.addAll(Arrays.asList(obstacle));
		obstacles.addAll(Arrays.asList(obstacle2));
		
		for(int i=0;i<grid[0];i++) {//placing walls of window as obstacles
			Integer[] wall = {i,-1};
			obstacles.add(wall);
			obstacles2.add(wall);
			Integer[] wall2 = {i,grid[1]};
			obstacles.add(wall2);
			obstacles2.add(wall);
		}
		for(int i=0;i<grid[0];i++) {//placing walls of window as obstacles
			Integer[] wall = {-1,i};
			obstacles.add(wall);
			obstacles2.add(wall);
			Integer[] wall2 = {grid[0],i};
			obstacles.add(wall2);
			obstacles2.add(wall);
		}
		for(int i = 0; i < 50; i++) {//creating 50 neural network agents
			System.out.println(i + "init");
			startTime = System.nanoTime();
			neuralNetwork NNagent = new neuralNetwork();
			endTime = System.nanoTime();
			System.out.println(i + " end: " + (endTime-startTime));
		}
		for(int i = 0; i < 50; i++) {//creating 50 qLearning agents
			System.out.println(i + "init");
			startTime = System.nanoTime();
			qLearn QAgent = new qLearn();
			endTime = System.nanoTime();
			System.out.println(i + " end: " + (endTime-startTime));
		}

	}
	static int rand() {
		int rand = ThreadLocalRandom.current().nextInt(0,main.grid[0]);
		return rand;
	}
}