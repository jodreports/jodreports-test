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
package org.jodreports.test.simpleodfextensions;

import org.odftoolkit.simple.Component;
import org.odftoolkit.simple.TextDocument;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.jodreports.test.DocumentElementFilter.filter;
import static org.jodreports.test.DocumentPredicates.withReferenceName;

///** @deprecated to be moved to simple-odf api. */
//@Deprecated
public class TextDocumentFunctions {

  public static TextReference getTextReferenceByName(TextDocument textDocument, String referenceName) {
    Collection<TextReference> textReferences = new TextReferenceExtractor(textDocument).getTextReferences();
    return filterTextReferencesByName(referenceName, textReferences);
  }

  public static TextReference getTextReferenceByName(Component component, String referenceName) {
    Collection<TextReference> textReferences = new TextReferenceExtractor(component).getTextReferences();
    return filterTextReferencesByName(referenceName, textReferences);
  }

  private static TextReference filterTextReferencesByName(String referenceName, Collection<TextReference> textReferences) {
    Collection<TextReference> filteredReferences = filter(textReferences).keepItems(withReferenceName(referenceName));
    int numberOfReferences = filteredReferences.size();
    assertThat(numberOfReferences).as("Number of TextReferences with name '" + referenceName + "'").isLessThanOrEqualTo(1);
    if (numberOfReferences > 0) {
      return filteredReferences.iterator().next();
    }
    else {
      return TextReference.UNDEFINED;
    }
  }
}
