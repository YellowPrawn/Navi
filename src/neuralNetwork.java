import java.util.concurrent.ThreadLocalRandom;

public class neuralNetwork extends Agent{
	double threshhold= 10;//max sigma value before activation
	double[] weight = {5.0,3.0,3.0,1.0,1.0,0.9,0.9,0.5};//priority/bias of input value
	neuralNetwork(){
		while(true) {
			int[] origin = pos.clone();
			vision2();
			for(int i = 0; i<input.length;i++) { //find sigma value of each input
				double sigma=weight[i]*input[i];//sigma value = priority of using input/bias
				if (sigma >= threshhold) {
					fire(i);//activates specific output
				}
			}
			if(origin[0]==pos[0]&&origin[1]==pos[1]) {//if agent doesn't move and is stuck, it takes a random direction

				fire(ThreadLocalRandom.current().nextInt(0,7));
			}
			if(main.end[0]==pos[0]&&main.end[1]==pos[1]) {//ends the process when agent reaches end point
				vision2();
				System.out.println("NN agent moves: " + history.size());//prints number of steps taken to achieve goal

				break;
			}
			history.add(pos);
		}
	}
	void fire(int i) { //possible movements by agent (action,direction)
		int[] a = pos.clone();
		int x = a[0];
		int y = a[1];
		int xWalls = main.grid[0];
		int yWalls = main.grid[1];
		switch(i) { //if statements prevent bot from exiting the grid
		case 0:
			if(x+1 != xWalls) {//move forward
				a[0]++;
			}
			break;
		case 1:
			if(x+1 != xWalls && y+1 != yWalls) {//move forward right
				a[0]++;
				a[1]++;
			}
			break;
		case 2:
			if(x+1 != xWalls && y-1!= -1) {//move forward back
				a[0]++;
				a[1]--;
			}
			break;
		case 3:
			if(y+1 != yWalls) {//move right
				a[1]++;
			}
			break;
		case 4:
			if(y-1 != -1) {//move left
				a[1]--;
			}
			break;

		case 5:
			if(x-1 != -1 && y+1 != yWalls) {//move back right
				a[0]--;
				a[1]++;
			}
			break;
		case 6:
			if(x-1 != -1 && y-1 != -1) {//move back left
				a[0]--;
				a[1]--;
			}
			break;
		case 7:
			if(x-1 != -1) {//move back
				a[0]--;
			}
			break;
		default:
			System.out.println("error");
			break;

		}
		pos = a.clone();
	}
}