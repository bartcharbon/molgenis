package org.molgenis.data.matrix;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.meta.MetaDataService;
import org.molgenis.data.meta.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class MatrixServiceImpl
{
    @Autowired
    DataService dataService;

    @Autowired
    MetaDataService metaDataService;

    private Map<String, DoubleMatrix> matrixMap = new HashMap();

    public DoubleMatrix getMatrixByEntityTypeId(String entityId){
        Entity entity = dataService.findOneById("sys_Matrix",entityId);
        if(entity != null) {
            if (matrixMap.get(entityId) == null) {
                matrixMap.put(entityId, new DoubleMatrix(new File(entity.getString(MatrixMetadata.FILE_LOCATION)), '\t'));//FIXME entity.getString(MatrixMetadata.SEPERATOR).charAt(0)));
            } else {
                return matrixMap.get(entityId);
            }
        }
        return null;
    }
}
