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
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jodreports.test;

import org.jodreports.test.simpleodfextensions.TextReference;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Section;

import static org.fest.assertions.Assertions.assertThat;

public class DocumentPredicates {

  public static Predicate<Table> withTableName(final String expectedTableName) {
    assertThat(expectedTableName).as("Expected table name for filtering").isNotEmpty();
    return new Predicate<Table>() {
      public boolean isSatisfiedFor(Table item) {
        return expectedTableName.equals(item.getTableName());
      }
    };
  }

  public static Predicate<Section> withSectionName(final String expectedSectionName) {
    assertThat(expectedSectionName).as("Expected section name for filtering").isNotEmpty();
    return new Predicate<Section>() {
      public boolean isSatisfiedFor(Section item) {
        return expectedSectionName.equals(item.getName());
      }
    };
  }

  public static Predicate<TextReference> withReferenceName(final String expectedReferenceName) {
    assertThat(expectedReferenceName).as("Expected reference name for filtering").isNotEmpty();
    return new Predicate<TextReference>() {
      public boolean isSatisfiedFor(TextReference item) {
        return expectedReferenceName.equals(item.getName());
      }
    };
  }
}
