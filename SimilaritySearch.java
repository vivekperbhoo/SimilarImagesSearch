import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SimilaritySearch {
    public static void main(String[] args) {
        //Set up string path directory for query images
        String queryFilePath = "queryImages\\";

        //Take command line arguments for query image and dataset folder path
        String queryFileName = args[0];
        String datasetFilePath = args[1] + "\\";

        //We concatenate different strings to obtain desired file name
        queryFileName = queryFilePath + queryFileName + ".ppm";


        ColorImage qImage = null;
        try {
            qImage = new ColorImage(queryFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //We reduce depth of query image created
        qImage.reduceColor(3);
            
        //We create a color histogram corresponding to the current query image and set its image as the query file
        ColorHistogram queryHist = new ColorHistogram(qImage.getDepth());
        queryHist.setImage(qImage);
            

        //Create priority queue for similar images
        PriorityQueue<HistogramIntersection> queue = new PriorityQueue<>(new IntersectionComparator());

        //For the current query file we loop through all images in dataset and find similar images
        for(int j = 25; j < 5000; j++){
            //We obtain desired dataset file name
            String datasetFileName = datasetFilePath + String.valueOf(j) + ".jpg.txt";

            ColorHistogram datasetHist = null;
            try {
                datasetHist = new ColorHistogram(datasetFileName);
            } catch (IOException e) {
                //Here we continue if dataset file name does not exist since in the dataset some file names skip, for e.g 27.jpg.txt does not exist
                continue;
            }

            //Create object representing histogram intersection between query image and dataset image
            HistogramIntersection histogramIntersection = new HistogramIntersection(queryFileName.substring(12), datasetFileName.substring(20), queryHist.compare(datasetHist));

            //Add it to queue
            queue.add(histogramIntersection);
               
            //Dequeue if there are more than 5 elements in queue
            if(queue.size() > 5){
                queue.poll();
            }
        }

        //Create an array of all images 
        String[] similarImageNames = new String[5];
        for(int idx = 0; idx < 5; idx++){
            HistogramIntersection h = queue.poll();
            similarImageNames[4 - idx] = h.GetDatasetFileName();
        }
        System.out.println(Arrays.toString(similarImageNames));        
    }
}
