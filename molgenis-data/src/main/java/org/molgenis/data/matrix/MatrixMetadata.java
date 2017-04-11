package org.molgenis.data.matrix;

import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.SystemPackage;
import org.molgenis.data.system.model.RootSystemPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_LABEL;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_LOOKUP;
import static org.molgenis.data.system.model.RootSystemPackage.PACKAGE_SYSTEM;

@Component
public class MatrixMetadata extends SystemEntityType {
    public static String FILE_LOCATION = "matrixFileLocation";
    public static String ID = "id";
    public static String SEPERATOR = "seperator";
    private static final String SIMPLE_NAME = "Matrix";
    private final RootSystemPackage systemPackage;

    @Autowired
	public MatrixMetadata(RootSystemPackage systemPackage)
        {
            super(SIMPLE_NAME, PACKAGE_SYSTEM);
            this.systemPackage = requireNonNull(systemPackage);
        }

        @Override
        public void init()
        {
            setLabel("Matrix metadata");

            setDescription("metadata with information about the matrix file");

            addAttribute(ID, ROLE_ID).setDescription("automatically generated internal id, only for internal use.");
            addAttribute(FILE_LOCATION, ROLE_LABEL, ROLE_LOOKUP).setLabel("Location of the matrix file").setUnique(true)
                    .setNillable(false);
            addAttribute(SEPERATOR, ROLE_LABEL, ROLE_LOOKUP).setLabel("The seperator used in the matrix file")
                    .setNillable(false);
        }
}
