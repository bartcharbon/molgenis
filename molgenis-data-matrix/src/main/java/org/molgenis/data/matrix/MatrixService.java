package org.molgenis.data.matrix;

import org.molgenis.data.MolgenisDataException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Bart on 4/12/2017.
 */
public interface MatrixService {
    @RequestMapping(value = "{entityName}/valueById", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    Double getValueByIndex(@PathVariable("entityName") String entityName, @RequestParam("rowIndex") int row, @RequestParam("columnIndex") int column) throws MolgenisDataException;

    @RequestMapping(value = "{entityName}/valueByName", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    Double getValueByNames(@PathVariable("entityName") String entityName, @RequestParam("rowName") String row, @RequestParam("columnName") String column) throws MolgenisDataException;
}
