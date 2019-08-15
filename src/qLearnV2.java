import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class qLearnV2 extends Agent{
	
	
	qLearnV2(){
		ArrayList<double[][]> qTable = new ArrayList<double[][]>();//[0][1-7]=state,[1][0-7]=action and value
		
		int moves = 0;
		int[] origin = pos.clone();
		while(true) {//creating QTable;
			double[][] SA = new double[2][8];//[0][1-7]=state,[1][0-7]=action and value
			boolean addQValue = false;
			int storedIndex = 0;
			
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
				double[] QValue = QFunction(SA, moves);
				qTable.get(storedIndex)[1][(int) QValue[0]] = QValue[1];
			}
			if(pos == main.end) {
				pos = origin;
				break;
			}
			moves++;
		}
		
		while(true) {
			double[][] SA = new double[2][8];//[0][1-7]=state,[1][0-7]=action and value
			
			for(int i = 0; i<qTable.size();i++) {//checking for matching state
				if((SA[0][0]==qTable.get(i)[0][0])&&(SA[0][1]==qTable.get(i)[0][1])&&(SA[0][2]==qTable.get(i)[0][2])&&(SA[0][3]==qTable.get(i)[0][3])&&(SA[0][4]==qTable.get(i)[0][4])&&(SA[0][5]==qTable.get(i)[0][5])&&(SA[0][6]==qTable.get(i)[0][6])&&(SA[0][7]==qTable.get(i)[0][7])) {				
					int optimalAction = 0;
					for(int j = 0; j<input.length;j++) {
						if(qTable.get(i)[1][j]>qTable.get(i)[1][optimalAction]) {
							optimalAction = j;
						}
					}
					history.add(pos);
					fire(optimalAction,pos);
				}
			}
		}
	}
	
	double[] QFunction(double[][] SA, int i) {
		
		double reward = 0;
		double epsilon = 1/(i * 0.9 + 1);
		double[] QValue = new double[2];//[0]=action taken, [1]=QValue
		int action = ThreadLocalRandom.current().nextInt(0,7);
		
		vision();
		reward = reward(pos,input,i);
		fire(action,pos);
		QValue[1] = reward + (epsilon*maxQValue(i));
		QValue[0] = action;

		return QValue;
	}
	
	double maxQValue(int i) {
		double maxQValue = 0;
		int[] origin = pos.clone();
		for(int j = 0; j<input.length;j++) {
			fire(j,pos);
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
			reward = (100/((((Math.abs(pos[0]-main.end[0]))^2)+((Math.abs(pos[1]-main.end[1]))^2))^(1/2)))-maxSigma-j;
		} catch (Error e) {
			reward=+100;
		}
		return reward;
		
	}
	
}
