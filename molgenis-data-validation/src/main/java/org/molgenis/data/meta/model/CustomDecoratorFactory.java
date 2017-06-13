package org.molgenis.data.platform.decorators;

import org.molgenis.data.AbstractSystemRepositoryDecoratorFactory;
import org.molgenis.data.meta.SystemEntityType;

public abstract class CustomDecoratorFactory extends AbstractSystemRepositoryDecoratorFactory
{
	public CustomDecoratorFactory(SystemEntityType entityType)
	{
		super(entityType);
	}
}

