package org.molgenis.ontology.utils;

import static org.molgenis.data.meta.AttributeType.ONE_TO_MANY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.Attribute;

public class SortaServiceUtil {
  private SortaServiceUtil() {}

  public static List<Map<String, Object>> getEntityAsMap(Iterable<? extends Entity> entities) {
    List<Map<String, Object>> docs = new ArrayList<>();
    for (Entity entity : entities) {
      docs.add(getEntityAsMap(entity));
    }
    return docs;
  }

  public static Map<String, Object> getEntityAsMap(Entity entity) {
    if (entity == null) return Collections.emptyMap();
    Map<String, Object> doc = new LinkedHashMap<>();

    for (Attribute attr : entity.getEntityType().getAtomicAttributes()) {
      if (attr.getDataType() == ONE_TO_MANY) {
        continue;
      }
      String attrName = attr.getName();
      Object object = entity.get(attrName);
      if (object instanceof Iterable<?>) {
        List<Map<String, Object>> refEntities = new ArrayList<>();
        for (Object refEntity : (Iterable<?>) object) {
          if (refEntity instanceof Entity) {
            refEntities.add(getEntityAsMap((Entity) refEntity));
          }
        }
        doc.put(attrName, refEntities);
      } else if (object instanceof Entity) {
        doc.put(attrName, getEntityAsMap((Entity) object));
      } else {
        doc.put(attrName, object);
      }
    }
    return doc;
  }
}
