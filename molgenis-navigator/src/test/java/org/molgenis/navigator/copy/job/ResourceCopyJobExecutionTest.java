package org.molgenis.navigator.copy.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.molgenis.data.config.EntityBaseTestConfig;
import org.molgenis.data.meta.AbstractSystemEntityTest;
import org.molgenis.jobs.config.JobTestConfig;
import org.molgenis.jobs.model.JobExecution.Status;
import org.molgenis.jobs.model.JobExecutionMetaData;
import org.molgenis.jobs.model.JobPackage;
import org.molgenis.navigator.model.ResourceIdentifier;
import org.molgenis.navigator.model.ResourceType;
import org.molgenis.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
    classes = {
      EntityBaseTestConfig.class,
      ResourceCopyJobExecutionMetadata.class,
      ResourceCopyJobExecutionFactory.class,
      JobPackage.class,
      JobTestConfig.class
    })
public class ResourceCopyJobExecutionTest extends AbstractSystemEntityTest {

  @Autowired ResourceCopyJobExecutionMetadata metadata;
  @Autowired ResourceCopyJobExecutionFactory factory;

  @Override
  protected Map<String, Pair<Class, Object>> getOverriddenReturnTypes() {
    Map<String, Pair<Class, Object>> map = new HashMap<>();

    Pair<Class, Object> resourcesPair = new Pair<>();
    resourcesPair.setA(List.class);
    ResourceIdentifier resource =
        ResourceIdentifier.create(ResourceType.ENTITY_TYPE, "testResource");
    resourcesPair.setB(Collections.singletonList(resource));

    Pair<Class, Object> statusPair = new Pair<>();
    statusPair.setA(Status.class);
    statusPair.setB(Status.SUCCESS);

    map.put(ResourceCopyJobExecutionMetadata.RESOURCES, resourcesPair);
    map.put(JobExecutionMetaData.STATUS, statusPair);

    return map;
  }

  @Override
  protected List<String> getExcludedAttrs() {
    List<String> attrs = new ArrayList<>();
    attrs.add(JobExecutionMetaData.FAILURE_EMAIL);
    attrs.add(JobExecutionMetaData.SUCCESS_EMAIL);
    return attrs;
  }

  @SuppressWarnings("squid:S2699") // Tests should include assertions
  @Test
  protected void testSystemEntity() {
    internalTestAttributes(
        metadata,
        ResourceCopyJobExecution.class,
        factory,
        getOverriddenReturnTypes(),
        getExcludedAttrs());
  }
}
