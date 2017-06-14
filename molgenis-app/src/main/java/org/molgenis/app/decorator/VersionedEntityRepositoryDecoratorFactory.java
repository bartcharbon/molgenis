package org.molgenis.app.decorator;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.Repository;
import org.molgenis.data.meta.model.AttributeFactory;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.platform.decorators.CustomDecoratorFactory;
import org.molgenis.security.user.UserService;
import org.molgenis.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class VersionedEntityRepositoryDecoratorFactory extends CustomDecoratorFactory
{
	private final UserService userService;
	private final VersionedEntityType versionedEntityMetadata;
	private final AttributeFactory attributeFactory;
	private final DataService dataService;

	public VersionedEntityRepositoryDecoratorFactory(DataService dataService, UserService userService,
			VersionedEntityType versionedEntityMetadata, AttributeFactory attributeFactory)
	{
		super(versionedEntityMetadata);
		this.dataService = dataService;
		this.userService = userService;
		this.versionedEntityMetadata = versionedEntityMetadata;
		this.attributeFactory = attributeFactory;
	}

	@Override
	public Repository<Entity> createDecoratedRepository(Repository repository)
	{
		return new VersionedEntityRepositoryDecorator(userService, repository, attributeFactory, dataService.getMeta());
	}

	@Override
	public boolean isSuitableForEntityType(EntityType entityType)
	{
		return EntityUtils.equals(entityType.getExtends(), versionedEntityMetadata);
	}
}
