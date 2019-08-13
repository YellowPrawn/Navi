/*import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class qLearn extends Agent{
	public static ArrayList<double[][]> qTable = new ArrayList<double[][]>();
	qLearn(){
		int i = 0;
		while(true) {
			QFunction(i);
			
			if(main.end[0]==pos[0]&&main.end[1]==pos[1]) {//ends the process when agent reaches end point
				System.out.println(history.size());//prints number of steps taken to achieve goal
				break;
			}
			history.add(pos);
			i++;
		}
	}
	
	void QFunction(int moves){
		
		vision();
		double[][] QSA = new double[2][8];//Q-value. [0][0-7]:state, [1][1]:index=action, element=value
		for(int i = 0; i<input.length; i++) {//generates state
			QSA[0][i] = input[i];
		}
			
		boolean addQValue = false;
			
		for(int i = 0; i < qTable.size();i++) {//check if current state is already in Q-table
			if((qTable.get(i)[0][0]==QSA[0][0])&&(qTable.get(i)[0][1]==QSA[0][1])&&(qTable.get(i)[0][2]==QSA[0][2])&&(qTable.get(i)[0][3]==QSA[0][3])&&(qTable.get(i)[0][4]==QSA[0][4])&&(qTable.get(i)[0][5]==QSA[0][5])&&(qTable.get(i)[0][6]==QSA[0][6])&&(qTable.get(i)[0][7]==QSA[0][7])) {
				addQValue = true;
				updateQTable(QSA,input);
				for(int j = 0; j <input.length;j++) {
					QValue(j,maxQSA(j,QSA,input),QSA,episilon(moves));
				}
				
				break;
			}else{
				addQValue=true;
			}
		}
		if(addQValue==true) {//add new state
			for(int j = 0;j<input.length;j++) {
				QSA[1][j] = 0;
			}
			qTable.add(QSA);
		}
	}

	double QValue(int action, double maxQSA, double[][] QSA, float epsilon) {
		double QValue = QSA[1][action]+epsilon*maxQSA;
		return QValue;
	}
	
	float episilon(int moves) {
		float episilon = (float) (moves*0.9);
		return episilon;
	}
	
	double maxQSA(int action, double[][] QSA, double[] input){
		int[] origin = pos.clone();
		
		double[] nextQSA = new double[8];
		fire(action,pos);
		int[] tempOrigin = pos.clone();
		for(int j = 0;j<input.length;j++) {//check values of all actions at state
			fire(j,pos);
			nextQSA[j] = reward(pos,input);
			pos = tempOrigin.clone();
		}
		
		int maxQSA=0;
		for(int i = 0;i<input.length;i++) {
			if(nextQSA[i]>nextQSA[maxQSA]) {
				maxQSA=i;
			}
		}
		pos = origin.clone();
		return(maxQSA);
	}
	
	void updateQTable(double[][] QSA, double[] input) {
		int[] origin = pos.clone();
		for(int j = 0;j<input.length;j++) {//check values of all actions at state
			fire(j,pos);
			QSA[1][j]=reward(pos,input);
			pos = origin.clone();
		}
	}
	
	double reward(int[] pos, double[] input){//generates reward based on distance from end point
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
		
		double reward = (1/((((Math.abs(pos[0]-main.start[0]))^2)+((Math.abs(pos[1]-main.start[1]))^2))^(1/2)))-maxSigma;
		if(pos==main.end) {
			reward=+10000000;
			return reward;
		}else{
			return reward;
		}
	}
}*/
