package org.molgenis.dataexplorer;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;

import static org.molgenis.dataexplorer.GenomeBrowserAttributesMetadata.*;

public class GenomeBrowserAttributes extends StaticEntity
{
	public GenomeBrowserAttributes(Entity entity)
	{
		super(entity);
	}

	public GenomeBrowserAttributes(EntityType entityType)
	{
		super(entityType);
	}

	public GenomeBrowserAttributes(String identifier, EntityType entityType)
	{
		super(identifier, entityType);
	}

	public Attribute getIdentifier()
	{
		return getEntity(IDENTIFIER, Attribute.class);
	}

	public void setIdentifier(Attribute identifier)
	{
		set(IDENTIFIER, identifier);
	}

	public Attribute getPos()
	{
		return getEntity(POS, Attribute.class);
	}

	public void setPos(Attribute pos)
	{
		set(POS, pos);
	}

	public Attribute getChrom()
	{
		return getEntity(CHROM, Attribute.class);
	}

	public void setChrom(Attribute chrom)
	{
		set(CHROM, chrom);
	}

	public Attribute getRef()
	{
		return getEntity(REF, Attribute.class);
	}

	public void setRef(Attribute ref)
	{
		set(REF, ref);
	}

	public Attribute getAlt()
	{
		return getEntity(ALT, Attribute.class);
	}

	public void setAlt(Attribute alt)
	{
		set(ALT, alt);
	}

	public Attribute getStop()
	{
		return getEntity(STOP, Attribute.class);
	}

	public void setStop(Attribute stop)
	{
		set(STOP, stop);
	}

	public Attribute getDescription()
	{
		return getEntity(DESCRIPTION, Attribute.class);
	}

	public void setDescription(Attribute description)
	{
		set(DESCRIPTION, description);
	}

	public Attribute getPatientID()
	{
		return getEntity(PATIENT_ID, Attribute.class);
	}

	public void setPatientID(Attribute patientID)
	{
		set(PATIENT_ID, patientID);
	}

	public Attribute getLinkout()
	{
		return getEntity(LINKOUT, Attribute.class);
	}

	public void setLinkout(Attribute linkout)
	{
		set(LINKOUT, linkout);
	}
}
