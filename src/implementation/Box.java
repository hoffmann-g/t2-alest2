package implementation;
import java.util.*;

public class Box implements Comparable<Box>{
    private ArrayList<Integer> dimensions= new ArrayList<>(); 
    private String dimensionsString;
    
    public Box(){}
    public Box(String dimensions){
        setDimensions(dimensions);
    }

    public void setDimensions(String dimensions){
        dimensionsString = dimensions;
        String[] dimencoeStrings = dimensions.split(" ");
        for(int i = 0; i < dimencoeStrings.length; i++){
            this.dimensions.add(Integer.parseInt(dimencoeStrings[i].trim()));
        }
        Collections.sort(this.dimensions);
    }

    public ArrayList<Integer> getDimensions(){
        return this.dimensions;
    }

    @Override
    public String toString(){
        return dimensionsString;
    }
    /*
    Sim isso se transformou em uma atrocidade
     */
    @Override
    public int compareTo(Box boxToCompare) {
        ArrayList<Integer> boxDimensions = dimensions;
        ArrayList<Integer> boxToCompareDimensions = boxToCompare.getDimensions();

        if ((boxDimensions.get(0) > boxToCompareDimensions.get(0)) && 
            (boxDimensions.get(1) > boxToCompareDimensions.get(1)) && 
            (boxDimensions.get(2) > boxToCompareDimensions.get(2))){
            return 1;
            }
        if ((boxDimensions.get(0) < boxToCompareDimensions.get(0)) && 
            (boxDimensions.get(1) < boxToCompareDimensions.get(1)) &&
            (boxDimensions.get(2) < boxToCompareDimensions.get(2))){
            return -1;
        } else {
            return 0;
        }
        
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dimensionsString == null) ? 0 : dimensionsString.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Box other = (Box) obj;
        if (dimensionsString == null) {
            if (other.dimensionsString != null)
                return false;
        } else if (!dimensionsString.equals(other.dimensionsString))
            return false;
        return true;
    }

    
}