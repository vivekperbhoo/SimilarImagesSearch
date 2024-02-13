//Class which represents the histogram intersection of a query image and a dataset image

public class HistogramIntersection {
    private String queryFileName, datasetFileName;
    private double intersectConstant;

    public HistogramIntersection(String q, String d, double c){
        queryFileName = q;
        datasetFileName = d.replaceFirst(".txt", "");
        intersectConstant = c;
    }       

    //GETTERS
    public String GetQueryFileName(){return queryFileName;}

    public String GetDatasetFileName(){return datasetFileName;}

    public double GetIntersectConstant(){return intersectConstant;}

}
