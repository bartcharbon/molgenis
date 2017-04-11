package org.molgenis.data.matrix;

import gnu.trove.TObjectIntHashMap;
import org.ujmp.core.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MatrixTest {

    private static File file;
    private static DoubleMatrix matrix;

    private static String mapping = "C:\\Users\\Bart\\Documents\\data\\mart_export.txt";
    private static String inputFile = "C:\\Users\\Bart\\Documents\\data\\GeneNetwork.txt";
    private static Map<String, String> geneMap;

    public static void main(String[] args) throws IOException {
        long start = new Date().getTime();
        System.out.println("Start: "+start);
        file = new File(inputFile);

        matrix = new DoubleMatrix(file ,'\t');
        matrix.init();

        geneMap = createEnsembleHugoMap(mapping);
        System.out.println("maps inited: "+(new Date().getTime()-start));

        System.out.println(getZScore("COL7A1","HP_3000050"));
        System.out.println("get score 1: "+(new Date().getTime()-start));
        System.out.println(getZScore("BRCA2","HP_3000050"));
        System.out.println("get score 2: "+(new Date().getTime()-start));
        System.out.println(getZScore("BRCA1","HP_0012072"));
        System.out.println("get score 3: "+(new Date().getTime()-start));
        System.out.println(getZScore("COL7A1","HP_0000505"));
        System.out.println("get score 4: "+(new Date().getTime()-start));
        System.out.println(getZScore("TTN","HP_0005607"));
        System.out.println("get score 5: "+(new Date().getTime()-start));
        System.out.println(getZScore("CHD7","HP_0100540"));
        System.out.println("get score 6: "+(new Date().getTime()-start));
    }

    private static Double getZScore(String hugo, String hpo) {
        String ensemble = geneMap.get(hugo);
        if(ensemble != null) {
            int columnIndex = matrix.getColumnMap().get(hpo);

            int rowIndex = matrix.getRowMap().get(ensemble);
            return matrix.getValueAtCoordinate(rowIndex, columnIndex);
        }
        //FIXME
        else return null;
    }

    protected static Map<String, String> createEnsembleHugoMap(String geneMappingFilePath) throws FileNotFoundException
    {
        File geneMappingFile = new File(geneMappingFilePath);
        Scanner geneMappingScanner = new Scanner(geneMappingFile, "UTF-8");
        Map<String, String> geneMap = new HashMap<>();
        geneMappingScanner.nextLine();//skip header
        while (geneMappingScanner.hasNext())
        {
            String hugo = "";
            String ensembl;
            Scanner geneScanner = new Scanner(geneMappingScanner.nextLine());
            if (geneScanner.hasNext()) ensembl = geneScanner.next();
            else throw new RuntimeException("every line should have at lease an ensembl ID");
            if (geneScanner.hasNext()) hugo = geneScanner.next();
            geneMap.put(hugo, ensembl);
        }
        return geneMap;
    }
}