package org.molgenis.data.meta.model;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.Repository;
import org.molgenis.data.meta.AttributeType;
import org.molgenis.security.user.UserService;
import org.molgenis.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class VersionedEntityRepositoryDecoratorFactory extends CustomDecoratorFactory
{
	private final UserService userService;
	private final VersionedEntityMetadata versionedEntityMetadata;
	private final AttributeFactory attributeFactory;
	private final DataService dataService;

	public VersionedEntityRepositoryDecoratorFactory(DataService dataService, UserService userService,
			VersionedEntityMetadata versionedEntityMetadata, AttributeFactory attributeFactory)
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
		return new VersionedEntityRepositoryDecorator(dataService, userService, attributeFactory);
	}


	@Override
	public boolean isSuitableForEntitytype(EntityType entityType)
	{
		return EntityUtils.equals(entityType.getExtends(), versionedEntityMetadata);
	}

	public void init(DataService dataService, EntityType entityType)
	{
		if (!hasHistoryReference(entityType))
		{
			Attribute attr = attributeFactory.create();
			attr.setDataType(AttributeType.MREF);
			attr.setRefEntity(entityType);
			attr.setName("HistorySelfReference");
			attr.setLabel("History");
			attr.setVisible(false);
			attr.setDescription("This attribute references previous versions of this entity");
			EntityType newEntityType = entityType.addAttribute(attr);
			dataService.getMeta().updateEntityType(newEntityType);
		}
	}

	public boolean hasHistoryReference(EntityType entityType)
	{
		return entityType.getAttribute("HistorySelfReference") != null;
	}
}
