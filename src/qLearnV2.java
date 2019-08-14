import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class qLearnV2 extends Agent{
	public static ArrayList<double[][]> qTable = new ArrayList<double[][]>();//[0][1-7]=state,[1][0-7]=action
	
	qLearnV2(){
		double[][] SA = new double[2][8];//[0][1-7]=state,[1][0-7]=action
		boolean addQValue = false;
		
		for(int i = 0; i<input.length;i++) {
			SA[0][i]=input[i];
		}
		
		for(int i = 0; i<qTable.size();i++) {//checking if Q-value is already in Q-table
			if((SA[0][0]==qTable.get(i)[0][0])&&(SA[0][1]==qTable.get(i)[0][1])&&(SA[0][2]==qTable.get(i)[0][2])&&(SA[0][3]==qTable.get(i)[0][3])&&(SA[0][4]==qTable.get(i)[0][4])&&(SA[0][5]==qTable.get(i)[0][5])&&(SA[0][6]==qTable.get(i)[0][6])&&(SA[0][7]==qTable.get(i)[0][7])) {				
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
		}else {
			QFunction(SA);
		}
	}
	
	double QFunction(double[][] SA) {
		int[] origin = pos.clone();
		double reward = 0;
		double QValue = 0;
		
		vision();
		reward = reward(pos,input,0);
		fire(ThreadLocalRandom.current().nextInt(0,7),pos);
		QValue = reward + maxQValue();
		
		return QValue;
	}
	
	double maxQValue() {
		double totalMaxQValue = 0;
		int k = 0;
		while(true) {
			double[] maxQValue = {0,0};
			int[] origin = pos.clone();
			for(int j = 0; j<input.length;j++) {
				fire(j,pos);
				vision();
				if(reward(pos,input,k)>maxQValue[0]) {
					maxQValue[0]=reward(pos,input,k);
					maxQValue[1]=j;
				}
			}
			fire((int) maxQValue[1],pos);
			totalMaxQValue =+ maxQValue[0];
			if(pos == main.end) {
				pos = origin;
				break;
			}
			k++;
		}
		return totalMaxQValue;
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
		
		double epsilon = 1/j * 0.9 + 1;
		
		double reward = (100/((((Math.abs(pos[0]-main.start[0]))^2)+((Math.abs(pos[1]-main.start[1]))^2))^(1/2)))-maxSigma-j;
		if(pos==main.end) {
			reward=+100;
			return reward*epsilon;
		}else{
			return reward*epsilon;
		}
	}
	
}
