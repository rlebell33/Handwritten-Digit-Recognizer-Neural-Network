import java.lang.Math;
import java.io.*;
public class main {
    //stuff to setup training
    public final double eta = 10.0; //learning rate, faster to type eta :)
    public final int size_of_minibatchs = 2; // size of our training batches
    public final int epochs = 1; // how many times we iterate of data

    // layers of our big network
    public final static int input_size  = 4; //input layer {0} will be 784
    public final static int hidden_size = 3; // Hidden (intermediate layer) {1} will be 30 or 100
    public final static int output_size = 2; // output (final layer) {2} Will be 10 (0-9)

    public final int trainingRowSize = 4; // will be 60000
    public final int testingRowSize = 10000; // will be 10000
    public final int columns = 785;

    private static final int number_of_epochs = 3;
    private static final int number_of_mini_batches = 2;
    private static double[][] data = new double[4][4];
    private static double[][] weights_0_1 = new double[hidden_size][input_size];
    private static double[][] weights_0_2 = new double[output_size][hidden_size];
    private static double[][] bias_0_1 = new double[hidden_size][1];
    private static double[][] bias_0_2 = new double[output_size][1];
    private static int[][] input = new int[input_size][4];
    private static int[][] output = new int[input_size][output_size];

    private static double[] layer_one_output = new double[hidden_size];

    private static double[] d_layer_2 = new double[output_size];



    //
    public static void main(String[] args) {
        // ######################################
        // Test:
        // inputs and outputs from excel sheet
        // minibatch #1 from excel sheet
        input[0][0] = 0;
        input[0][1] = 1;
        input[0][2] = 0;
        input[0][3] = 1;
        output[0][0] = 0;
        output[0][1] = 1;

        input[1][0] = 1;
        input[1][1] = 0;
        input[1][2] = 1;
        input[1][3] = 0;
        output[0][0] = 1;
        output[0][1] = 0;

        // minibatch 2 from excel sheet
        input[2][0] = 0;
        input[2][1] = 0;
        input[2][2] = 1;
        input[2][3] = 1;
        output[0][0] = 0;
        output[0][1] = 1;

        input[3][0] = 1;
        input[3][1] = 1;
        input[3][2] = 0;
        input[3][3] = 0;
        output[0][0] = 1;
        output[0][1] = 0;

        // weights (will be randomized)
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

        // biases
        bias_0_1[0][0] = 0.1;
        bias_0_1[1][0] = -0.36;
        bias_0_1[2][0] = -0.31;

        // weights and biases for layer 2
        weights_0_2[0][0] = 0.76;
        weights_0_2[0][1] = 0.48;
        weights_0_2[0][2] = -0.73;

        weights_0_2[1][0] = 0.34;
        weights_0_2[1][1] = 0.89;
        weights_0_2[1][2] = -0.23;

        bias_0_2[0][0] = 0.16;
        bias_0_2[1][0] = -0.46;
        LoadData();
        Train();
        System.exit(0);
    }

    static void CreateMiniBatches() {
        for (int i = 0; i < number_of_mini_batches; i++) {

        }

    }

    static void LoadData() {
        boolean has_data = true;
        int row = 0;
        String row_data = "";
        String[] read_data;
        String file = "./test.csv";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            while (has_data == true) {
                row_data = buffer.readLine();
                if (row_data == null) {
                    buffer.close();
                    break;
                }
                read_data = row_data.split(",");
                System.out.println("the read_data length: " + read_data.length);
                for (int i = 2; i < read_data.length; i++) {
                    System.out.println(Integer.parseInt(read_data[i]));
                    data[row][i - 2] = Integer.parseInt(read_data[i]);
                }
                row++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void Train() {
        // SGD start
        for (int j = 0; j < data.length; j++) {
            for (int k = 0; k < data[j].length; k++) {
                System.out.println(data[j][k]);

            }
        }
        for (int i = 0; i < number_of_epochs; i++) {
            System.out.println("start epoch: " + i);
        }
        //grab input from data or previous layer
        int[] input1 = new int[input_size];
        int[] output1 = new int[output_size];
        int[] input2 = new int[input_size];
        int[] output2 = new int[output_size];

        double[] output_guess = new double[output_size];
        input1[0] = 0;
        input1[1] = 1;
        input1[2] = 0; 
        input1[3] = 1;

        input2[0] = 1;
        input2[1] = 0;
        input2[2] = 1; 
        input2[3] = 0;

        output1[0] = 0;
        output1[1] = 1;

        output2[0] = 1;
        output2[1] = 0;

        double[] output_0_1 = new double[hidden_size];
        //calculate forward pass of layer 1
        output_0_1 = CalculateLayerOneOutput(weights_0_1, bias_0_1, input1);
        //calculate forward pass of layer 2
        output_guess = CalculateLayerTwoOutput(weights_0_2, bias_0_2, output_0_1);
        CalculateCost(output1, output_guess);

        BackPass_2_1(output_guess, output1);
        BackPass_1_0(output_0_1, weights_0_2, input1);
    }

    static double[] CalculateLayerOneOutput(double[][] weights, double[][] bias, int[] input){
        double[] z_layer = new double[hidden_size];
        for(int i = 0; i<z_layer.length; i++){
            double weighted_sum = 0;
            for(int k = 0; k < weights[0].length; k++){
                System.out.println("meow" + input[i]);
                weighted_sum += input[k]*weights[i][k];
            }
            z_layer[i] = weighted_sum + bias[i][0];
        }
        layer_one_output = sigmoid(z_layer, hidden_size);
        System.out.println("Layer 1: ");
        for (int j = 0; j < layer_one_output.length; j++) {
            System.out.println(layer_one_output[j]);
        }
        return layer_one_output;
    }

    static double[][] BackPass_2_1(double[] layer_two_output, int[] expected_output){
        //calc d layer
        for(int i = 0; i < layer_two_output.length; i++){
            d_layer_2[i] = ((layer_two_output[i] - expected_output[i]) * layer_two_output[i] * (1-layer_two_output[i]));
            System.out.println("d Layer 2: " + d_layer_2[i]);
        }
        //calc gb
        double[] gb = new double[layer_two_output.length];
        gb = d_layer_2;

        //gradient of weights layer 2
        double[][] gradient_of_weights_2_1 = new double[output_size][hidden_size];
        for(int j = 0; j < output_size; j++){
            for(int k = 0; k < hidden_size; k++){
                gradient_of_weights_2_1[j][k] = d_layer_2[j] * layer_one_output[k];
                System.out.println("grad of weights Layer 2: " + gradient_of_weights_2_1[j][k]);
            }
        }
        return gradient_of_weights_2_1;
    }

    static double[] CalculateLayerTwoOutput(double[][] weights, double[][] bias, double[] input){
        double[] z_layer = new double[output_size];
        double[] layer_one_output = new double[output_size];
        for(int i = 0; i<z_layer.length; i++){
            double weighted_sum = 0;
            for(int k = 0; k < weights[0].length; k++){
                weighted_sum += input[k]*weights[i][k];
                System.out.println("input: " + input[k]);
            }
            z_layer[i] = weighted_sum + bias[i][0];
        }
        layer_one_output = sigmoid(z_layer, output_size);
        System.out.println("layer 2:");
        for (int j = 0; j < layer_one_output.length; j++) {
            System.out.println(layer_one_output[j]);
        }
        return layer_one_output;
    }

    static double[][] BackPass_1_0(double[] layer_one_output, double[][] layer_two_weights, int[] expected_output){
        //calc d layer
        double[] d_layer = new double[layer_one_output.length];
        double total = 0.0;
        for(int i = 0; i < hidden_size; i++){
            total = 0;
            for(int j = 0; j < output_size; j++){
                total += (layer_two_weights[j][i] * d_layer_2[j]);
            }
            d_layer[i] = total * (layer_one_output[i] * (1 - layer_one_output[i]));
            System.out.println("d Layer 1: " + d_layer[i]);
        }
        //calc gb
        double[] gb = new double[layer_one_output.length];
        gb = d_layer;

        //gradient of weights layer 2
        double[][] gradient_of_weights_1_0 = new double[hidden_size][input_size];
        for(int j = 0; j < hidden_size; j++){
            for(int k = 0; k < input_size; k++){
                gradient_of_weights_1_0[j][k] = d_layer[j] * expected_output[k];
                System.out.println("grad of weights Layer 1: " + gradient_of_weights_1_0[j][k]);
            }
        }
        return gradient_of_weights_1_0;
    }

    static double[] sigmoid(double[] z_layer, int num_of_nodes){
        double[] a_layer = new double[num_of_nodes];

        for (int j = 0; j < z_layer.length; j++) {
            //System.out.println(z_layer[j]);
            a_layer[j] = 1 / (1 + Math.pow(2.71828, - z_layer[j]));
            //System.out.println(a_layer[j]);
        }
        return a_layer;
    }

    static double CalculateCost(int[] output, double[] calculated){
        double result = 0.0;
        double cost;
        for(int i = 0; i < output.length; i++){
            result += Math.pow(output[i] - calculated[i], 2);
            System.out.println("output: " + output[i]);
            System.out.println("calculated: " + calculated[i]);

        }
        cost = 0.5 * result;
        System.out.println("cost: " + cost);
        return cost;
    }
    static void ForwardFeed(){

    }
}