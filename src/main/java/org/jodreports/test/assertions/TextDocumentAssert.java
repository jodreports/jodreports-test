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
import org.jodreports.test.simpleodfextensions.TextDocumentFunctions;
import org.jodreports.test.simpleodfextensions.TextReference;
import org.jodreports.test.simpleodfextensions.TextReferenceExtractor;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Section;

public class TextDocumentAssert extends GenericAssert<TextDocumentAssert, TextDocument> {

  TextDocumentAssert(TextDocument actual) {
    super(TextDocumentAssert.class, actual);
  }

  public TextDocumentAssert containsTable(String tableName) {
    final Table table = actual.getTableByName(tableName);
    Assertions
        .assertThat(table)
        .overridingErrorMessage("Expected table named '" + tableName + "' to be part of document, but is missing.")
        .isNotNull();
    return myself;
  }

  public TextDocumentAssert doesNotContainTable(String tableName) {
    final Table table = actual.getTableByName(tableName);
    Assertions
        .assertThat(table)
        .overridingErrorMessage("Expected table named '" + tableName + "' to be excluded from document, but is present.")
        .isNull();
    return myself;
  }

  public TextDocumentAssert containsSection(String sectionName) {
    final Section section = actual.getSectionByName(sectionName);
    Assertions
        .assertThat(section)
        .overridingErrorMessage("Expected section named '" + sectionName + "' to be part of document, but is missing.")
        .isNotNull();
    return myself;
  }

  public TextDocumentAssert doesNotContainSection(String sectionName) {
    final Section section = actual.getSectionByName(sectionName);
    Assertions
        .assertThat(section)
        .overridingErrorMessage("Expected section named '" + sectionName + "' to be excluded from document, but is present.")
        .isNull();
    return myself;
  }

  public TextReferenceAssert containsTextReference(String referenceName) {
    TextReference textReference = TextDocumentFunctions.getTextReferenceByName(actual, referenceName);
    Assertions
        .assertThat(textReference.exists())
        .overridingErrorMessage("Document should contain text reference with name <'" + referenceName + "'>, but no such reference found.")
        .isTrue();

    return new TextReferenceAssert(textReference);
  }
}
