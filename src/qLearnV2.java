import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class qLearnV2 extends Agent{
	
	qLearnV2(){
		ArrayList<double[][]> qTable = new ArrayList<double[][]>();//[0][1-7]=state,[1][0-7]=action and value
		
		int moves = 0;
		int[] origin = pos.clone();
		while(true) {//creating QTable;
			double[][] SA = new double[2][8];//[0][1-7]=state,[1][0-7]=action and value
			boolean addQValue = true;
			int storedIndex = 0;
			
			vision();
			for(int i = 0; i<input.length;i++) {
				SA[0][i]=input[i];
			}
			
			for(int i = 0; i<qTable.size();i++) {//checking if Q-value is already in Q-table
				if((SA[0][0]==qTable.get(i)[0][0])&&(SA[0][1]==qTable.get(i)[0][1])&&(SA[0][2]==qTable.get(i)[0][2])&&(SA[0][3]==qTable.get(i)[0][3])&&(SA[0][4]==qTable.get(i)[0][4])&&(SA[0][5]==qTable.get(i)[0][5])&&(SA[0][6]==qTable.get(i)[0][6])&&(SA[0][7]==qTable.get(i)[0][7])) {				
					storedIndex = i;
					addQValue=false;
				}else {
					addQValue=true;
				}
			}
			
			if(addQValue==true) {//add new state
				for(int i = 0;i<input.length;i++) {
					SA[1][i]=0;//set Q-value to 0(initial)
				}
				qTable.add(SA);
				
			}else{//update existing state
				int[] tempOrigin = pos.clone();
				double[] QValue = QFunction(SA, moves, tempOrigin);
				qTable.get(storedIndex)[1][(int) QValue[0]] = QValue[1];
			}
			if(main.end[0]==pos[0]&&main.end[1]==pos[1]) {
				System.out.println("training complete");
				pos = origin;
				break;
			}
			moves++;
			System.out.println(moves);
		}
		
		for(int i = 0; i<qTable.size();i++) {//printing q-table
			System.out.println(Arrays.toString(qTable.get(i)[1]));
		}
		
		System.out.println("initializing pathfinding...");
		while(true) {
			double[][] SA = new double[2][8];//[0][1-7]=state,[1][0-7]=action and value
			boolean newState = true;
			int rand = ThreadLocalRandom.current().nextInt(0,7);
			int optimalAction = 0;
			
			vision();
			for(int i = 0; i<input.length;i++) {//adding values into SA
				SA[0][i]=input[i];
			}
			
			for(int i = 0; i<qTable.size();i++) {//checking for matching state
				if((SA[0][0]==qTable.get(i)[0][0])&&(SA[0][1]==qTable.get(i)[0][1])&&(SA[0][2]==qTable.get(i)[0][2])&&(SA[0][3]==qTable.get(i)[0][3])&&(SA[0][4]==qTable.get(i)[0][4])&&(SA[0][5]==qTable.get(i)[0][5])&&(SA[0][6]==qTable.get(i)[0][6])&&(SA[0][7]==qTable.get(i)[0][7])) {				
					
					System.out.println(Arrays.toString(qTable.get(i)[1]));
					for(int j = 0; j<input.length;j++) {
						if(qTable.get(i)[1][j]>qTable.get(i)[1][optimalAction]) {//finding best action to take at this state
							optimalAction = j;
						}
					}
					
					newState = false;
				}
			}
			if(newState==true) {
				optimalAction = rand;
			}
			
			fire(optimalAction);
			
			//System.out.println(Arrays.toString(pos));
			if(main.end[0]==pos[0]&&main.end[1]==pos[1]) {
				System.out.println(history.size());
				pos = origin;
				break;
			}
			history.add(pos);
			//System.out.println(Arrays.toString(pos));
		}
	}
	
	double[] QFunction(double[][] SA, int i, int[] origin) {
		
		double reward = 0;
		double epsilon = 1/(i * 0.9 + 1);
		double[] QValue = new double[2];//[0]=action taken, [1]=QValue
		int action = randomAction();
		
		vision();
		reward = reward(pos,input,i);
		fire(action);
		/*if(origin[0]==pos[0]&&origin[1]==pos[1]) {
			action = randomAction();
			fire(action);
		}
		System.out.println(Arrays.toString(pos));*/
		QValue[1] = reward + (epsilon*maxQValue(i));
		QValue[0] = action;
		return QValue;
	}
	
	double maxQValue(int i) {
		double maxQValue = 0;
		int[] origin = pos.clone();
		for(int j = 0; j<input.length;j++) {
			fire(j);
			vision();
			if(reward(pos,input,i)>maxQValue) {
				maxQValue=reward(pos,input,i+1);
			}
			pos = origin;
		}
		
		return maxQValue;
	}
	
	double reward(int[] pos, double[] input,int j){//generates reward based on distance from end point
		double[] weight = {5.0,3.0,3.0,1.0,1.0,0.9,0.9,0.5};
		double[] sigma = new double[8];
		
		for(int i = 0; i<input.length;i++) { //find sigma value of each input
			sigma[i]=weight[i]*input[i];//sigma value = priority of using input/bias	
		}
		
		double maxSigma = 0;
		for(int i = 0; i<sigma.length;i++) {
			if(sigma[i]>maxSigma) {
				maxSigma=sigma[i];
			}
		}
		
		double reward = 0;
		try {
			reward = (100/((((Math.abs(pos[0]-main.end[0]))^2)+((Math.abs(pos[1]-main.end[1]))^2))^(1/2)))-0.001*(maxSigma+j);
		} catch (Exception e) {
			reward = 100-0.001*(maxSigma+j);//will output an error if pos = end since displacement is 0 (cannot divide by 0)
		}
		
		if(main.obstacles.contains(pos)) {
			reward =- 100;//punishment for entering obstacle location
		}
		return reward;
		
	}
	
	int randomAction() {
		int randAction = ThreadLocalRandom.current().nextInt(0,7);
		return randAction;
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
