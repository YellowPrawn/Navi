import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class neuralNetwork extends Agent{
	
	double threshhold= 10;//max sigma value before activation
	
	double[] weight = {5.0,3.0,3.0,1.0,1.0,0.9,0.9,0.5};//priority/bias of input value
	
	neuralNetwork(){
		while(true) {
			int[] origin = pos.clone();
			vision();
			for(int i = 0; i<input.length;i++) { //find sigma value of each input
				double sigma=weight[i]*input[i];//sigma value = priority of using input/bias	
				
				if (sigma >= threshhold) {
					fire(i,pos);//activates specific output
				}	
			}
			if(origin[0]==pos[0]&&origin[1]==pos[1]) {//if agent does'nt move and is stuck, it takes a random direction
				fire(ThreadLocalRandom.current().nextInt(0,7),pos);
			}
			if(main.end[0]==pos[0]&&main.end[1]==pos[1]) {//ends the process when agent reaches end point
				vision();
				System.out.println(Arrays.toString(input));
				System.out.println(history.size());//prints number of steps taken to achieve goal
				break;
			}
			history.add(pos);
			
		}
	}
}