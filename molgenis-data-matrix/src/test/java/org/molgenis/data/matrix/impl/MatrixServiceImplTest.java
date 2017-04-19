package org.molgenis.data.matrix.impl;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.matrix.MatrixService;
import org.molgenis.data.matrix.meta.MatrixMetadata;
import org.molgenis.file.FileStore;
import org.molgenis.util.ResourceUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class MatrixServiceImplTest {

    private MatrixService matrixService;

    @BeforeTest
    private void setUp() {
        Entity entity = mock(Entity.class);

        when(entity.getString(MatrixMetadata.FILE_LOCATION)).thenReturn(ResourceUtils.getFile(getClass(), "/testMatrix.txt").getAbsolutePath());
        when(entity.getString(MatrixMetadata.SEPERATOR)).thenReturn("TAB");

        DataService dataService = mock(DataService.class);
        when(dataService.findOneById(MatrixMetadata.PACKAGE + "_" + MatrixMetadata.SIMPLE_NAME, "test")).thenReturn(entity);

        FileStore fileStore = mock(FileStore.class);

        matrixService = new MatrixServiceImpl(dataService, fileStore);
    }


    @Test
    public void getValueByIndexTest() {
        assertEquals(1.123,matrixService.getValueByIndex("test",1,1));
    }

    @Test
    public void getValueByNamesTest() {
        assertEquals(1.234,matrixService.getValueByNames("test","gene1","hpo234"));
    }
}
