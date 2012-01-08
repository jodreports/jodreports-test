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
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.text.Paragraph;

import java.util.Iterator;

public class CellAssert extends GenericAssert<CellAssert, Cell> {

  CellAssert(Cell actual) {
    super(CellAssert.class, actual);
  }

  public CellAssert showsText(String expectedStringValue) {
    Assertions.assertThat(actual.getStringValue())
        .as("String value of cell at column index " + actual.getColumnIndex() + ", table row " + actual.getRowIndex() +
            " of table named '" + actual.getTable().getTableName() + "'")
        .isEqualTo(expectedStringValue);
    DocumentAssertions.assertThat(actual).allParagraphsAreDisplayed();
    return this;
  }

  public CellAssert allParagraphsAreDisplayed() {
    Iterator<Paragraph> paragraphIterator = actual.getParagraphIterator();
    while (paragraphIterator.hasNext()) {
      Paragraph next = paragraphIterator.next();
      DocumentAssertions.assertThat(next.getOdfElement()).isDisplayed();
    }
    return this;
  }

  public CellAssert hasNotStringValue(String otherStringValue) {
    Assertions.assertThat(actual.getStringValue())
        .as("String value of cell at column index " + actual.getColumnIndex() + ", table row " + actual.getRowIndex() +
            " of table named '" + actual.getTable().getTableName() + "'")
        .isNotEqualTo(otherStringValue);
    return this;
  }
}
