import java.util.ArrayList;

public class DQN extends qLearn{
	
	
	
	int moves = 0;
	int[] origin = pos.clone();
	
	DQN(){
	}
	
	double[] cost(double[][] SA) {
		double cost = ((int) (maxQValue(moves)- QFunction(SA, moves, origin)[1]) )^2; //mean squared error function
		
		double action = QFunction(SA, moves, origin)[0];
		double[] value = {action, cost};
		
		return value;
	}
	
	@Override
	double[] calcValue(double[][] SA, int moves, int[] tempOrigin) {
		return cost(SA);
	}
	
	@Override
	boolean value(ArrayList<double[][]> qTable, int i, int j, int optimalAction) {
		return qTable.get(i)[1][j]<qTable.get(i)[1][optimalAction];
	}
	
	@Override
	String training() {
		return "DQN training moves: ";
	}
	@Override
	String testing() {
		return "DQN agent moves: ";
	}
}
