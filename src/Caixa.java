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
    @Override
    public int compareTo(Caixa caixa2) {
        ArrayList<Integer> caixa2Values = caixa2.getDimencoes();
        int retorno = -1;
        for(int i = 0; i < LENGTH; i++){
            retorno = this.dimencoes.get(i) - caixa2Values.get(i);
            if(retorno >= 0)return retorno;
        }

        return retorno;
    }
}