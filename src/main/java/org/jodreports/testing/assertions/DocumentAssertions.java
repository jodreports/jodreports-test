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
package org.jodreports.testing.assertions;

import org.fest.assertions.Assertions;
import org.jodreports.testing.simpleodfextensions.TextReference;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;

public class DocumentAssertions extends Assertions {

  public static CellAssert assertThat(Cell actual) {
    return new CellAssert(actual);
  }

  public static RowAssert assertThat(Row actual) {
    return new RowAssert(actual);
  }

  public static ColumnAssert assertThat(Column actual) {
    return new ColumnAssert(actual);
  }

  public static TableAssert assertThat(Table actual) {
    return new TableAssert(actual);
  }

  public static ParagraphAssert assertThat(Paragraph actual) {
    return new ParagraphAssert(actual);
  }

  public static TextDocumentAssert assertThat(TextDocument actual) {
    return new TextDocumentAssert(actual);
  }

  public static SectionAssert assertThat(Section actual) {
    return new SectionAssert(actual);
  }

  public static TextReferenceAssert assertThat(TextReference actual) {
    return new TextReferenceAssert(actual);
  }

  public static OdfStylableElementAssert assertThat(OdfStylableElement actual) {
    return new OdfStylableElementAssert(actual);
  }
}
