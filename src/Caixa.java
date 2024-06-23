import java.util.*;

public class Caixa implements Comparable<Caixa>{
    private static final int LENGTH = 3;
    private ArrayList<Integer> dimencoes= new ArrayList<>(); 
    private String dimencoesString;
    
    public Caixa(){}
    public Caixa(String dimencoes){
        setDimensions(dimencoes);
    }

    public void setDimensions(String dimencoes){
        dimencoesString = dimencoes;
        String[] dimencoeStrings = dimencoes.split(" ");
        for(int i = 0; i < dimencoeStrings.length; i++){
            this.dimencoes.add(Integer.parseInt(dimencoeStrings[i].trim()));
        }
        Collections.sort(this.dimencoes);
    }

    public ArrayList<Integer> getDimencoes(){
        return this.dimencoes;
    }

    @Override
    public String toString(){
        return dimencoesString;
    }
    /*
    Sim isso se transformou em uma atrocidade
     */
    @Override
    public int compareTo(Caixa caixa2) {
        ArrayList<Integer> dim_caixa1 = dimencoes;
        ArrayList<Integer> dim_caixa2 = caixa2.getDimencoes();

        if ((dim_caixa1.get(0) > dim_caixa2.get(0)) && 
            (dim_caixa1.get(1) > dim_caixa2.get(1)) && 
            (dim_caixa1.get(2) > dim_caixa2.get(2))){
            return 1;
            }
        if ((dim_caixa1.get(0) < dim_caixa2.get(0)) && 
            (dim_caixa1.get(1) < dim_caixa2.get(1)) &&
            (dim_caixa1.get(2) < dim_caixa2.get(2))){
            return -1;
        } else {
            return 0;
        }
        
    }
}