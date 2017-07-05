package org.molgenis.dataexplorer;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.StaticEntity;

import static org.molgenis.dataexplorer.GenomeBrowserSettingsMetadata.*;

public class GenomeBrowserSettings extends StaticEntity
{
	public GenomeBrowserSettings(Entity entity)
	{
		super(entity);
	}

	public GenomeBrowserSettings(EntityType entityType)
	{
		super(entityType);
	}

	public GenomeBrowserSettings(String identifier, EntityType entityType)
	{
		super(identifier, entityType);
	}

	public String getIdentifier()
	{
		return getString(IDENTIFIER);
	}

	public void setIdentifier(String identifier)
	{
		set(IDENTIFIER, identifier);
	}

	public Attribute getLabelAttr()
	{
		return getEntity(LABEL_ATTR, Attribute.class);
	}

	public void setLabelAttr(Attribute labelAttr)
	{
		set(LABEL_ATTR, labelAttr);
	}

	public EntityType getEntity()
	{
		return getEntity(ENTITY, EntityType.class);
	}

	public void setEntity(EntityType entity)
	{
		set(ENTITY, entity);
	}

	public TrackType getTrackType()
	{
		return TrackType.valueOf(getString(TRACK_TYPE));
	}

	public void setTrackType(TrackType trackType)
	{
		set(TRACK_TYPE, trackType.toString().toUpperCase());
	}

	public GenomeBrowserSettings getMolgenisReferenceTracks()
	{
		return getEntity(MOLGENIS_REFERENCE_TRACKS, GenomeBrowserSettings.class);
	}

	public void setMolgenisReferenceTracks(GenomeBrowserSettings molgenisReferenceTracks)
	{
		set(MOLGENIS_REFERENCE_TRACKS, molgenisReferenceTracks);
	}

	public boolean isShowAllReferences()
	{
		return getBoolean(SHOW_ALL_MOLGENIS_REFERENCES);
	}

	public void setShowAllReferences(boolean showAllReferences)
	{
		set(SHOW_ALL_MOLGENIS_REFERENCES, showAllReferences);
	}

	public GenomeBrowserAttributes getGenomeBrowserAttrs()
	{
		return getEntity(GENOME_BROWSER_ATTRS, GenomeBrowserAttributes.class);
	}

	public void setGenomeBrowserAttrs(GenomeBrowserAttributes genomeBrowserAttrs)
	{
		set(GENOME_BROWSER_ATTRS, genomeBrowserAttrs);
	}

	public String getActions()
	{
		return getString(ACTIONS);
	}

	public void setActions(String actions)
	{
		set(ACTIONS, actions);
	}

	public String getAdditionalReferences()
	{
		return getString(ADDITIONAL_REFERENCES);
	}

	public void setAdditionalReferences(String additionalReferences)
	{
		set(ADDITIONAL_REFERENCES, additionalReferences);
	}

	public enum TrackType
	{
		VARIANT
	}
}
