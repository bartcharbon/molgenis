package org.molgenis.dataexplorer.controller.bioschema.model.enums;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public enum Type {
  Beacon,
  DataCatalog,
  DataRecord,
  Dataset,
  Event,
  Gene,
  LabProtocol,
  Protein,
  ProteinAnnotation,
  ProteinStructure,
  Sample,
  Taxon,
  Tool,
  PropertyValue;

  private static final HashMap<String, Type> strValMap;

  static {
    Type[] types = Type.values();
    strValMap = newHashMapWithExpectedSize(types.length);
    for (Type type : types) {
      strValMap.put(getValueString(type), type);
    }
  }

  public static Type toEnum(String valueString) {
    return strValMap.get(normalize(valueString));
  }

  /**
   * Returns the value string for the given enum value
   *
   * @param value enum value
   * @return value string
   */
  public static String getValueString(Type value) {
    return normalize(value.toString());
  }

  /**
   * Returns the value strings for all enum types in the defined enum order
   *
   * @return value strings
   */
  public static List<String> getOptionsLowercase() {
    return Arrays.stream(values()).map(Type::getValueString).collect(toList());
  }

  private static String normalize(String valueString) {
    return StringUtils.remove(valueString, '_').toLowerCase();
  }
}
