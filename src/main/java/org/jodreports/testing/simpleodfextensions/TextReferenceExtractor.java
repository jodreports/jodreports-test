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
package org.jodreports.testing.simpleodfextensions;

import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.Component;
import org.odftoolkit.simple.TextDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

public class TextReferenceExtractor {

  private OdfElement rootElement;
  private Collection<TextReference> textReferences = new ArrayList<TextReference>();

  public TextReferenceExtractor(Component component) {
    assertThat(component).isNotNull();
    rootElement = component.getOdfElement();
  }

  public TextReferenceExtractor(TextDocument textDocument) {
    assertThat(textDocument).isNotNull();
    try {
      this.rootElement = textDocument.getContentRoot();
    }
    catch (Exception e) {
      this.rootElement = null;
    }
  }

  public Collection<TextReference> getTextReferences() {
    if (rootElement != null) {
      collectElementsWithReferenceName(rootElement);
    }
    return textReferences;
  }

  private void collectElementsWithReferenceName(OdfElement element) {
    NodeList nodesByTagName = element.getElementsByTagName(TextReference.TEXT_REFERENCE_MARK_START);
    for (int i = 0; i < nodesByTagName.getLength(); i++) {
      Node item = nodesByTagName.item(i);
      textReferences.add(new TextReference(item));
    }
  }
}
