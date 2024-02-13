import java.util.Comparator;

//Class which acts as comparator so that we can initialise our priority queue
public class IntersectionComparator implements Comparator<HistogramIntersection>{

    @Override
    public int compare(HistogramIntersection o1, HistogramIntersection o2) {
        return Double.valueOf(o1.GetIntersectConstant()).compareTo(Double.valueOf(o2.GetIntersectConstant()));
    }    
}