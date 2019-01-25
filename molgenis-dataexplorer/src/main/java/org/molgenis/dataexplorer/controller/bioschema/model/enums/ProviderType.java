package org.molgenis.dataexplorer.controller.bioschema.model.enums;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public enum ProviderType {
  Person,
  Organisation;

  /**
   * Returns the value string for the given enum value
   *
   * @param value enum value
   * @return value string
   */
  public static String getValueString(ProviderType value) {
    return normalize(value.toString());
  }

  /**
   * Returns the value strings for all enum types in the defined enum order
   *
   * @return value strings
   */
  public static List<String> getOptionsLowercase() {
    return Arrays.stream(values()).map(ProviderType::getValueString).collect(toList());
  }

  private static String normalize(String valueString) {
    return StringUtils.remove(valueString, '_').toLowerCase();
  }
}
