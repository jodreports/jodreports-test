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
package org.jodreports.test.assertions;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import org.odftoolkit.simple.table.Table;

public class TableAssert extends GenericAssert<TableAssert, Table> {

  TableAssert(Table actual) {
    super(TableAssert.class, actual);
  }

  public TableAssert hasRowCount(int expectedRows) {
    Assertions.assertThat(actual.getRowCount()).as("Row count of table '" + actual.getTableName() + "'").isEqualTo(expectedRows);
    return this;
  }

  public TableAssert hasColumnCount(int expectedColumns) {
    Assertions.assertThat(actual.getColumnCount()).as("Column count of table '" + actual.getTableName() + "'").isEqualTo(expectedColumns);
    return this;
  }
}
