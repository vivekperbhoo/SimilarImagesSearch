import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ColorImage {
    private int width, height, depth;
    private int[][][] threeDArr;


    //Constructor that creates image from ppm file
    public ColorImage(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));

        //variable to store each line being read
        String line;

        //initialise indices for our 3D array
        int i = 0;
        int j = 0;
        int k = 0;
        
        //to skip the magic number P3
        br.readLine();

        //to skip the comment
        br.readLine();

        //read dimensions from 3rd line
        line = br.readLine();
        String[] dimensions = line.split(" ");
        width = Integer.valueOf(dimensions[0]);
        height = Integer.valueOf(dimensions[1]);

        //getting max rgb value which is depth
        depth = 0;
        int maxRGBVal = Integer.parseInt(br.readLine());
        while(maxRGBVal >= 1){
            maxRGBVal /= 2;
            depth++;
        }

        //declare 3D array to store image with  representing channel values of RGB for each pixel
        threeDArr = new int[height][width][3];

        //start looping through RGB values in ppm file and extract them into our 3D array
        while((line = br.readLine()) != null){
            //values is an array of all RGB values on one line
            String[] values = line.split(" ");
            //loop through each element in values and insert into our 3D array accordingly
            for(int idx = 0; idx < values.length; idx++){
                //k > 2 to change column
                if(k > 2){
                    //j >= width - 1 to change row
                    if(j >= width - 1){
                        j = 0;
                        k = 0;
                        i++;
                    }
                    //else change just the column and not row
                    else{ 
                        k = 0;
                        j++;
                    }                   
                }

                threeDArr[i][j][k] = Integer.valueOf(values[idx]);
                
                //k++ to change channel
                k++;
            }
        }
        br.close();
    }
    

    public void reduceColor(int d){
        //loop through each row
        for(int i = 0; i < height; i++){
            //loop through each column
            for(int j = 0; j < width; j++){
                //perform bitwise right shift by depth - d units to obtain reduced color space 
                threeDArr[i][j][0] = threeDArr[i][j][0] >> (depth - d);
                threeDArr[i][j][1] = threeDArr[i][j][1] >> (depth - d);
                threeDArr[i][j][2] = threeDArr[i][j][2] >> (depth - d);
            }
        }
        depth = d;
    }

    //GETTERS
    public int[] getPixel(int i, int j){
        return threeDArr[i][j];
    }

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public int getDepth(){return depth;}
}
