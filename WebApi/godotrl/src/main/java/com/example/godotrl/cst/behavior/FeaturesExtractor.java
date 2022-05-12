package com.example.godotrl.cst.behavior;

/* Atenção: pode ser necessário adicionar o estado antigo para que o aproximador funcione adequadamente.
 * 1. Precisa adicionar features de acordo com o jogo (recomendado fazer em uma classe filha)
 * */
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FeaturesExtractor {
    public LinkedHashMap<String, Double> getFeatures(ArrayList<Domain> state, Domain action) {
        LinkedHashMap<String, Double> f = new LinkedHashMap<String, Double>();

        f.put("bias", 1.0);
        /*
         * create features
         * */

        return f;
    }
}
