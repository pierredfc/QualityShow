package videolibrary.street.quality.qualityshow.utils;


import java.util.ArrayList;
import java.util.List;

public class CitationHelper {

    private List<String> citations;

    public CitationHelper(){
        this.citations = new ArrayList<String>();
        fillCitations();
    }

    public String getCitation(){
        int position = (int)( Math.random()*( this.citations.size() + 1));
        return "« " + this.citations.get(position) + " »";
    }

    private void fillCitations(){
        this.citations.add("Winter is coming.");
        this.citations.add("My name is Oliver Queen.");
        this.citations.add("Sssplendide !");
        this.citations.add("Silence maraud, je parlemente !");
        this.citations.add("My name is Barry Allen and I am the fastest man alive.");

        this.citations.add("Luke, je suis ton père.");
        this.citations.add("Une vodka-Martini, au shaker.");
        this.citations.add("Leilou Dallas, multipass.");
        this.citations.add("J’ai les mains faites pour l’or.");
        this.citations.add("Nom de Zeus !");
    }
}
