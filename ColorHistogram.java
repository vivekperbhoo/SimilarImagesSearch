import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ColorHistogram {
    private int d, pixelCount;
    private int[] histogram;
    private double[] normalizedHistogram;
    private ColorImage colorImage;
    private boolean isQuery;

    public ColorHistogram (int d){
        this.d = d;
        histogram = new int[(int)Math.pow(2, d*3)];
        isQuery = true;
    }

    public ColorHistogram (String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        //Read first line which tells number of entries in histogram and create array accordingly and store d at same time
        histogram = new int[Integer.parseInt(line = br.readLine())];
        d = (int)((Math.log(Integer.parseInt(line))/Math.log(2))/3);

        //Read the actual data for the histogram and store it in the array
        int idx = 0;
        while((line = br.readLine()) != null){
            String[] values = line.split(" ");
            for(int i = 0; i < values.length; i++){
                histogram[idx] = Integer.parseInt(values[i]);
                //Also calculate and store total pixel count in image
                pixelCount += histogram[idx];
                idx++;
            }
        }
        br.close();
        isQuery = false;
    }

    public void setImage(ColorImage image){
        colorImage = image;
    }

    public double[] getHistogram(){
        //Initialise our normalized histogram
        normalizedHistogram = new double[(int)Math.pow(2, 3*d)];

        //We check if it is a query image 
        if(isQuery){
            //Calculate pixel count baseed on image height and depth
            pixelCount = (colorImage.getHeight() * colorImage.getWidth());

            //Loop through rows and column of color image 
            for(int i = 0; i < colorImage.getHeight(); i++){
                for(int j = 0; j < colorImage.getWidth(); j++){
                    int[] pixel = colorImage.getPixel(i, j);
                    int R = pixel[0];
                    int G = pixel[1];
                    int B = pixel[2];

                    //Calculate index to insert in normalized histogram based on R G B values
                    int idx = (R<<(2*d))+((G<<d)+B);

                    //Increment count in histogram at that index
                    histogram[idx]++;
                }
            }
        }
        
        //Getting our normalized histogram by dividing by pixel count
        for(int i = 0; i < histogram.length; i++){
            normalizedHistogram[i] = ((double)histogram[i]/(double)pixelCount);
        }
        
        return normalizedHistogram;
    }

    public double compare(ColorHistogram hist){
        double k = 0;

        //if we try to compare with null histogram, we compute it first
        if(normalizedHistogram == null){
            getHistogram();
        }

        //Loop through both histograms 
        for(int i = 0; i < normalizedHistogram.length; i++){
            //Get minimum of both histograms at that index and add it to k
            k += Math.min(normalizedHistogram[i], hist.getHistogram()[i]);
        }
        return k;
    }

    public void SaveColorHistogram (String filename){

    }

    public static void main(String[] args) {
        ColorImage image = null;
        try {
            image = new ColorImage("queryImages\\q00.ppm");
        } catch (IOException e) {
            e.printStackTrace();
        }

        image.reduceColor(3);
        ColorHistogram hist = new ColorHistogram(image.getDepth());
        hist.setImage(image);
        System.out.println(Arrays.toString(hist.getHistogram()));
    }
}