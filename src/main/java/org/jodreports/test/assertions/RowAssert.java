/**
 * This file is part of "JODReports Testing Support Library".
 *
 * Copyright (C) 2011-2012 Ansgar Konermann and contributing authors.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the  GNU Lesser General Public License  as published
 * by the  Free Software Foundation,  either version 3  of the License, or
 * (at your option) any later version.
 *
 * This program is  distributed in  the hope that  it will be  useful, but
 * WITHOUT   ANY   WARRANTY;   without   even   the  implied  warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see http://www.gnu.org/licenses/
 */
package org.jodreports.test.assertions;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;

import static org.jodreports.test.assertions.DocumentAssertions.assertThat;

public class RowAssert extends GenericAssert<RowAssert, Row> {

  RowAssert(Row actual) {
    super(RowAssert.class, actual);
  }

  public RowAssert hasCellCount(int expectedCellCount) {
    Assertions
        .assertThat(actual.getCellCount())
        .as("Cell count of row index " + actual.getRowIndex() + " of table named '" + actual.getTable().getTableName() + "'")
        .isEqualTo(expectedCellCount);
    return this;
  }

  public RowAssert hasNotCellContaining(String text) {
    boolean cellFound = false;

    int cellCount = actual.getCellCount();
    for (int i = 0; i < cellCount && !cellFound; i++) {
      Cell cellByIndex = actual.getCellByIndex(i);
      if (cellByIndex != null) {
        String value = cellByIndex.getStringValue();
        cellFound = value != null && value.contains(text);
      }
    }
    assertThat(cellFound).as("Cell found containing '" + text + "'").isFalse();
    return this;
  }

  public RowAssert hasLabelColumnWith(String text) {
    assertThat(actual.getCellByIndex(0)).showsText(text);
    return this;
  }

  public RowAssert hasDataColumnWith(String text) {
    assertThat(actual.getCellByIndex(1)).showsText(text);
    return this;
  }
}
