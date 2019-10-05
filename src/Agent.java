import java.util.ArrayList;
public class Agent {
	ArrayList<int[]> history = new ArrayList<int[]>();
	double[] input = new double[8]; //fMain, fLeft, fRight, left, right, bleft, bright, back
	//the greater the input value, the further away the agent is from the obstacle/wall
	int[] pos = main.start.clone();
	Agent(){
		history.clear();
	}
	void vision() { //setter for input values (sensors)
		for(int i = 0;i<input.length;i++) {//repeats until all inputs are checked
			int[] temp = pos.clone();
			for(int j = 0;j<(main.grid[0]+main.grid[1]);j++) {//repeats until vision reaches end of screen
				fire(i,temp);
				for(int k = 0;k<main.obstacles.size();k++) {//repeats until all obstacles are checked
					if((temp[0]==main.obstacles.get(k)[0])&&(temp[1]==main.obstacles.get(k)[1]))

					{//checks if vision position is an obstacle

						int[] displacement = new int[2];//x,y
						if(j==2||j==3||j==6||j==7) {//diagonal sensors
							displacement[0] = Math.abs(temp[0]-pos[0]);//checks for distance away from object

							displacement[1] = Math.abs(temp[1]-pos[1]);//checks for distance away from object

							input[i]=Math.sqrt((displacement[0]^2)+(displacement[1]^2));
							break;
						} else {//horizontal or vertical sensors
							displacement[0] = Math.abs(temp[0]-pos[0]);//checks for distance away from object

							displacement[1] = Math.abs(temp[1]-pos[1]);//checks for distance away from object

							if(displacement[0]>displacement[1]) {
								input[i]=displacement[0];
								break;
							}else {
								input[i]=displacement[1];
								break;
							}
						}
					}
				}
			}
		}
	}
	//vision2 is for obtaining information after training stages. repeated code.
	void vision2() { //setter for input values (sensors)
		for(int i = 0;i<input.length;i++) {//repeats until all inputs are checked
			int[] temp = pos.clone();
			for(int j = 0;j<(main.grid[0]+main.grid[1]);j++) {//repeats until vision reaches end of screen
				fire(i,temp);


				for(int k = 0;k<main.obstacles2.size();k++) {//repeats until all obstacles2 are checked
					if((temp[0]==main.obstacles2.get(k)[0])&&(temp[1]==main.obstacles2.get(k)[1]))

					{//checks if vision position is an obstacle

						int[] displacement = new int[2];//x,y
						if(j==2||j==3||j==6||j==7) {//diagonal sensors
							displacement[0] = Math.abs(temp[0]-pos[0]);//checks for distance away from object

							displacement[1] = Math.abs(temp[1]-pos[1]);//checks for distance away from object

							input[i]=Math.sqrt((displacement[0]^2)+(displacement[1]^2));
							break;
						} else {//horizontal or vertical sensors
							displacement[0] = Math.abs(temp[0]-pos[0]);//checks for distance away from object

							displacement[1] = Math.abs(temp[1]-pos[1]);//checks for distance away from object

							if(displacement[0]>displacement[1]) {
								input[i]=displacement[0];
								break;
							}else {
								input[i]=displacement[1];
								break;
							}
						}
					}
				}
			}
		}
	}
	void fire(int i,int[] a) { //possible movements by agent (action,movement)
		switch(i) {
		case 0:
			a[0]++;//front
			break;
		case 1:
			a[0]++;//front right
			a[1]++;
			break;
		case 2:
			a[0]++;//front left
			a[1]--;
			break;
		case 3:
			a[1]++;//right
			break;
		case 4:
			a[1]--;//left
			break;
		case 5:
			a[0]--;//back right
			a[1]++;
			break;
		case 6:
			a[0]--;//back left
			a[1]--;
			break;
		case 7:
			a[0]--;//back
			break;

		}
	}
}