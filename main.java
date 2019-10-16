import java.lang.Math;
public class main {
    private static final int number_of_epochs = 3;
 
        // 
	public static void main( String[] args ) {
        //initialize weights and biases for between the input layer and the first layer.
        double[][] weights_0_1 = new double [3][4];
        double[][] bias_0_1 = new double [3][1];
        double[][] bias_0_2 = new double [2][1];
        int[][] input = new int[4][4];
        int[][] output = new int[4][2];

        //learning rate
        double eta = 1.0;

        //######################################
        //Test:
        //inputs and outputs from excel sheet
        //minibatch #1 from excel sheet
        input[0][0] = 0;
        input[0][1] = 1;
        input[0][2] = 0;
        input[0][3] = 1;
        output[0][1] = 0;
        output[0][1] = 1;
        
        input[1][0] = 1;
        input[1][1] = 0;
        input[1][2] = 1;
        input[1][3] = 0;
        output[0][1] = 1;
        output[0][1] = 0;

        //minibatch 2 from excel sheet
        input[2][0] = 0;
        input[2][1] = 0;
        input[2][2] = 1;
        input[2][3] = 1;
        output[0][1] = 0;
        output[0][1] = 1;

        input[3][0] = 1;
        input[3][1] = 1;
        input[3][2] = 0;
        input[3][3] = 0;
        output[0][1] = 1;
        output[0][1] = 0;

        //weights (will be randomized)
        weights_0_1[0][0] = -0.21;
        weights_0_1[0][1] = 0.72;
        weights_0_1[0][2] = -0.25;
        weights_0_1[0][3] = 1;
        
        weights_0_1[1][0] = -0.94;
        weights_0_1[1][1] = -0.41;
        weights_0_1[1][2] = -0.47;
        weights_0_1[1][3] = 0.63;
        
        weights_0_1[2][0] = 0.15;
        weights_0_1[2][1] = 0.55;
        weights_0_1[2][2] = -0.49;
        weights_0_1[2][3] = -0.75;

        //biases
        bias_0_1[0][0] = 0.1;
        bias_0_1[1][0] = -0.36;
        bias_0_1[2][0] = -0.31;
        
        //weights and biases for layer 2
        double[][] weights_0_2 = new double[2][3];

        weights_0_2[0][0] = 0.76;
        weights_0_2[0][1] = 0.48;
        weights_0_2[0][2] = -0.73;

        weights_0_2[1][0] = 0.34;
        weights_0_2[1][1] = 0.89;
        weights_0_2[1][2] = -0.23;

        bias_0_2[0][0] = 0.16;
        bias_0_2[1][0] = -0.46;

        Train();
        System.exit( 0 ); 
    }

    static void Train(){
        for(int i = 0; i < number_of_epochs; i++){
            System.out.println("start epoch: " + i);

        }
    }

  
  
}

// BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
// while ((row = csvReader.readLine()) != null) {
//     String[] data = row.split(",");
//     // do something with the data
// }
// system.out.print(data);
// csvReader.close();