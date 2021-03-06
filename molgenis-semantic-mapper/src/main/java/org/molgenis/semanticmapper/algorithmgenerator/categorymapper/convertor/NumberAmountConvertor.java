package org.molgenis.semanticmapper.algorithmgenerator.categorymapper.convertor;

import java.util.Collections;
import java.util.List;
import javax.measure.unit.Unit;
import org.jscience.physics.amount.Amount;
import org.molgenis.semanticmapper.algorithmgenerator.bean.AmountWrapper;
import org.molgenis.semanticmapper.algorithmgenerator.categorymapper.CategoryMapperUtil;

public class NumberAmountConvertor extends AmountConvertor {

  private static final int DEFAULT_NUMBER = 0;

  public boolean matchCriteria(String description) {
    return true;
  }

  AmountWrapper getInternalAmount(String description) {
    List<Double> extractNumbers = CategoryMapperUtil.extractNumbers(description);
    Unit<?> unit = CategoryMapperUtil.findDurationUnit(description);
    Collections.sort(extractNumbers);

    if (unit != null && unit.isCompatible(STANDARD_PER_WEEK_UNIT)) {
      if (extractNumbers.size() == 1) {
        return AmountWrapper.create(
            Amount.valueOf(extractNumbers.get(0), unit).to(STANDARD_PER_WEEK_UNIT));
      } else if (extractNumbers.size() > 1) {
        return AmountWrapper.create(
            Amount.rangeOf(
                    extractNumbers.get(0), extractNumbers.get(extractNumbers.size() - 1), unit)
                .to(STANDARD_PER_WEEK_UNIT));
      } else {
        return AmountWrapper.create(
            Amount.valueOf(DEFAULT_NUMBER, unit).to(STANDARD_PER_WEEK_UNIT));
      }
    }

    return null;
  }
}
