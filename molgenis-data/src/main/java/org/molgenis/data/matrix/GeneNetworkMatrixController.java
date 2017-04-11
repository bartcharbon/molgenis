package org.molgenis.data.matrix;

import gnu.trove.TObjectIntHashMap;
import org.molgenis.data.MolgenisDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/matrix")
public class GeneNetworkMatrixController {

    Map<String, String> geneMap;
    DoubleMatrix matrix = null;


    @Autowired
    MatrixServiceImpl matrixService;
    private TObjectIntHashMap columnMap = null;
    private TObjectIntHashMap rowMap = null;

    @RequestMapping(value = "/score", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Double getZScore(@RequestParam("hugo") String hugo, @RequestParam("hpo") String hpo) throws MolgenisDataException {
        if(geneMap == null){
            geneMap = createEnsembleHugoMap("C:\\Users\\Bart\\Documents\\data\\mart_export.txt");
        }
        if(matrix == null){
            matrix = matrixService.getMatrixByEntityTypeId("GeneNetworkMatrix");
        }
        if(columnMap == null){
            columnMap = matrix.getColumnMap();
        }
        if(rowMap == null){
            rowMap = matrix.getRowMap();
        }
        String ensemble = geneMap.get(hugo);
        if(ensemble != null) {
            int columnIndex = columnMap.get(hpo);

            int rowIndex = rowMap.get(ensemble);
            return matrix.getValueAtCoordinate(rowIndex, columnIndex);
        }
        //FIXME
        else return null;
    }

    protected static Map<String, String> createEnsembleHugoMap(String geneMappingFilePath) throws MolgenisDataException
    {
        try {
            File geneMappingFile = new File(geneMappingFilePath);
            Scanner geneMappingScanner = new Scanner(geneMappingFile, "UTF-8");
            Map<String, String> geneMap = new HashMap<>();
            geneMappingScanner.nextLine();//skip header
            while (geneMappingScanner.hasNext()) {
                String hugo = "";
                String ensembl;
                Scanner geneScanner = new Scanner(geneMappingScanner.nextLine());
                if (geneScanner.hasNext()) ensembl = geneScanner.next();
                else throw new MolgenisDataException("Every line in the GeneMappingFile should have at lease an ensembl ID");
                if (geneScanner.hasNext()) hugo = geneScanner.next();
                geneMap.put(hugo, ensembl);
            }
            return geneMap;
        }
        catch (FileNotFoundException e){
            throw new MolgenisDataException(e);
        }
    }
}
