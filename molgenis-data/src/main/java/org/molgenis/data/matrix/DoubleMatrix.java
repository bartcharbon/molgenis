package org.molgenis.data.matrix;

import gnu.trove.TObjectIntHashMap;
import org.molgenis.data.MolgenisDataException;
import org.ujmp.core.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DoubleMatrix {
    private final File file;
    private final char seperator;

    private TObjectIntHashMap columnMap = new TObjectIntHashMap();
    private TObjectIntHashMap rowMap = new TObjectIntHashMap();
    private Matrix matrix;

    private boolean inited = false;

    public TObjectIntHashMap getColumnMap() {
        if(!inited) init();
        return columnMap;
    }

    public TObjectIntHashMap getRowMap() {
        if(!inited) init();
        return rowMap;
    }

    public DoubleMatrix(File file, char seperator) {
        this.file = file;
        this.seperator = seperator;
    }

    public double getValueAtCoordinate(int x, int y) {
        if(!inited) init();
        return matrix.getAsDouble(x,y);
    }

    public void init() {
        try {
            matrix = org.ujmp.core.Matrix.Factory.linkTo().file(file.getAbsolutePath()).asDenseCSV(seperator);
            setRowIndicesMap();
            setColumnIndicesMap();
        } catch (FileNotFoundException e) {
            throw new MolgenisDataException(e);
        }
        catch (IOException e) {
            throw new MolgenisDataException(e);
        }
        inited = true;
    }

    private void setRowIndicesMap() throws FileNotFoundException {
        int i = 0;
        String gene;
        while (i < matrix.getRowCount())
        {
            gene = matrix.getAsString(i, 0);
            rowMap.put(gene, i);
            i++;
        }
    }

    private void setColumnIndicesMap(){
        int i = 0;
        String hpo;
        while (i < matrix.getColumnCount())
        {
            hpo = matrix.getAsString(0, i);
            columnMap.put(hpo, i);
            i++;
        }
    }
}
