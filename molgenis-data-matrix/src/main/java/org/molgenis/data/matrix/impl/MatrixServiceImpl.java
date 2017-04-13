package org.molgenis.data.matrix.impl;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.MolgenisDataException;
import org.molgenis.data.matrix.MatrixMapper;
import org.molgenis.data.matrix.MatrixService;
import org.molgenis.data.matrix.meta.MatrixMetadata;
import org.molgenis.file.FileStore;
import org.molgenis.file.model.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Service
@RequestMapping("/api/matrix")
public class MatrixServiceImpl implements MatrixService {
    @Autowired
    DataService dataService;

    @Autowired
    FileStore fileStore;

    private Map<String, DoubleMatrix> matrixMap = new HashMap();
    private Map<String, MatrixMapper> columnMappingMap = new HashMap();
    private Map<String, MatrixMapper> rowMappingMap = new HashMap();

    private DoubleMatrix getMatrixByEntityTypeId(String entityId) {
        DoubleMatrix matrix = null;
        Entity entity = dataService.findOneById(MatrixMetadata.PACKAGE + "_" + MatrixMetadata.SIMPLE_NAME, entityId);
        if (entity != null) {
            if (entity.getEntity(MatrixMetadata.COLUMNMAPPINGFILE) != null && columnMappingMap.get(entityId) == null) {
                createMapper(entityId, entity);
            }
            if (entity.getEntity(MatrixMetadata.ROWMAPPINGFILE) != null && rowMappingMap.get(entityId) == null) {
                createMapper(entityId, entity);
            }
            matrix = getMatrix(entityId, entity);
        }
        return matrix;
    }

    private DoubleMatrix getMatrix(String entityId, Entity entity) {
        DoubleMatrix matrix;
        if (matrixMap.get(entityId) == null) {
            String fileLocation = entity.getString(MatrixMetadata.FILE_LOCATION);
            char seperator = MatrixMetadata.getSeparatorValue(entity.getString(MatrixMetadata.SEPERATOR));
            matrix = new DoubleMatrix(new File(fileLocation), seperator);
            matrixMap.put(entityId, matrix);
        } else {
            matrix = matrixMap.get(entityId);
        }
        return matrix;
    }

    private void createMapper(String entityId, Entity entity) {
        FileMeta meta = entity.getEntity(MatrixMetadata.ROWMAPPINGFILE, FileMeta.class);
        File file = fileStore.getFile(meta.getId());
        MatrixMapper mapper = new MatrixMapperImpl(file);
        rowMappingMap.put(entityId, mapper);
    }

    @Override
    @RequestMapping(value = "{entityId}/valueByIndex", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Double getValueByIds(@PathVariable("entityId") String entityName, @RequestParam("row") int row, @RequestParam("column") int column) throws MolgenisDataException {
        DoubleMatrix matrix = getMatrixByEntityTypeId(entityName);
        return matrix.getValueByIndex(row, column);
    }

    @Override
    @RequestMapping(value = "{entityId}/valueByName", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Double getValueByNames(@PathVariable("entityId") String entityName, @RequestParam("row") String row, @RequestParam("column") String column) throws MolgenisDataException {
        DoubleMatrix matrix = getMatrixByEntityTypeId(entityName);
        MatrixMapper columnMapper = columnMappingMap.get(entityName);
        MatrixMapper rowMapper = rowMappingMap.get(entityName);
        if (columnMapper != null) column = columnMapper.map(column);
        if (rowMapper != null) row = rowMapper.map(row);
        return matrix.getValueByName(row, column);
    }
}
