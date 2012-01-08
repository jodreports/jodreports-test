/**
 * This file is part of "JODReports :: Testing Support Library".
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
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jodreports.testing.assertions;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import org.odftoolkit.simple.table.Column;

import static org.jodreports.testing.assertions.DocumentAssertions.assertThat;

public class ColumnAssert extends GenericAssert<ColumnAssert, Column> {

  protected ColumnAssert(Column actual) {
    super(ColumnAssert.class, actual);
  }

  public void showsText(String... expectedCellTexts) {
    Assertions.assertThat(actual.getCellCount()).as("number of cells").isEqualTo(expectedCellTexts.length);

    for (int i = 0; i < expectedCellTexts.length; i++) {
      assertThat(actual.getCellByIndex(i)).as("Cell at index " + i).showsText(expectedCellTexts[i]);
    }
  }
}
