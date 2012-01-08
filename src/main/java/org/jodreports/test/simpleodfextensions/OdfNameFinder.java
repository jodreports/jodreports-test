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
package org.jodreports.test.simpleodfextensions;

import org.odftoolkit.odfdom.dom.DefaultElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.HashSet;

public class OdfNameFinder extends DefaultElementVisitor {

  private Collection<OdfElement> result;
  private OdfName searchedOdfName;

  public OdfNameFinder(OdfName searchedOdfName) {
    this.searchedOdfName = searchedOdfName;
    this.result = new HashSet<OdfElement>();
  }

  public Collection<OdfElement> findElements(OdfElement root) {
    visit(root);
    collectElementWithSearchedOdfName(root);

    return result;
  }

  private void collectElementWithSearchedOdfName(OdfElement element) {
    if (searchedOdfName.equals(element.getOdfName())) {
      result.add(element);
    }
  }

  @Override
  public void visit(OdfElement element) {
    Node node = element.getFirstChild();
    while (node != null) {
      if (node instanceof OdfElement) {
        OdfElement odfElement = (OdfElement) node;
        collectElementWithSearchedOdfName(odfElement);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          odfElement.accept(this);
        }
      }
      node = node.getNextSibling();
    }
  }
}
