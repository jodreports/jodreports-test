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

import org.odftoolkit.odfdom.dom.DefaultElementVisitor;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.style.props.OdfStyleProperty;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.HashSet;

public class StyleFinder extends DefaultElementVisitor {

  private OdfStyleProperty stylePropertyKey;
  private String stylePropertyValue;
  private Collection<OdfStylableElement> result;

  public StyleFinder(OdfStyleProperty stylePropertyKey, String stylePropertyValue) {
    this.stylePropertyKey = stylePropertyKey;
    this.stylePropertyValue = stylePropertyValue;
    this.result = new HashSet<OdfStylableElement>();
  }

  public Collection<OdfStylableElement> findElements(OdfStylableElement root) {
    visit(root);
    collectElementWithSearchedStyle(root);

    return result;
  }

  private void collectElementWithSearchedStyle(OdfStylableElement element) {
    String property = element.getProperty(stylePropertyKey);
    if (property != null && property.contains(stylePropertyValue)) {
      result.add(element);
    }
  }

  @Override
  public void visit(OdfElement element) {
    Node node = element.getFirstChild();
    while (node != null) {
      if (node instanceof OdfStylableElement) {
        collectElementWithSearchedStyle((OdfStylableElement) node);
      }
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        OdfElement elementNode = (OdfElement) node;
        elementNode.accept(this);
      }
      node = node.getNextSibling();
    }
  }
}
