package org.molgenis.data.platform.decorators;

import org.molgenis.data.AbstractSystemRepositoryDecoratorFactory;
import org.molgenis.data.meta.SystemEntityType;
import org.molgenis.data.meta.model.EntityType;

public abstract class CustomDecoratorFactory extends AbstractSystemRepositoryDecoratorFactory
{
	public CustomDecoratorFactory(SystemEntityType entityType)
	{
		super(entityType);
	}

	public abstract boolean isSuitableForEntitytype(EntityType entityType);
}

