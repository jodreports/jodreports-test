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
package org.jodreports.testing.simpleodfextensions;

import org.odftoolkit.odfdom.dom.attribute.text.TextNameAttribute;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.element.text.TextReferenceMarkEndElement;
import org.odftoolkit.odfdom.dom.element.text.TextReferenceMarkStartElement;
import org.w3c.dom.Node;

public class TextReference {

  private static final String TEXT_ATTRIBUTE_NAME = TextNameAttribute.ATTRIBUTE_NAME.getQName();
  public static final String TEXT_REFERENCE_MARK_START = TextReferenceMarkStartElement.ELEMENT_NAME.getQName();
  public static final String TEXT_REFERENCE_MARK_END = TextReferenceMarkEndElement.ELEMENT_NAME.getQName();

  private String name;
  private Node startNode;

  private StringBuilder textContentBuilder = new StringBuilder();

  private TextReference() {
    name = null;
  }

  public TextReference(Node startNode) {
    this.startNode = startNode;
    this.name = startNode.getAttributes().getNamedItem(TEXT_ATTRIBUTE_NAME).getNodeValue();
  }

  public boolean exists() {
    return true;
  }

  public String getName() {
    return name;
  }

  public String getTextContent() {
    collectTextContent(startNode);
    return textContentBuilder.toString();
  }

  public OdfStylableElement getStylableElement() {
    OdfStylableElement stylableParent = findNearestStylableParent(startNode);
    if (stylableParent == null) {
      throw new IllegalStateException("no stylable parent found.");
    }
    return stylableParent;
  }

  private void collectTextContent(Node sibling) {
    textContentBuilder.append(sibling.getTextContent());
    if (!hasNextSibling(sibling) && !isEndNode(sibling)) {
      throw new IllegalStateException("no text reference end found, overlapping elements aren't supported.");
    }
    if (!isEndNode(sibling)) {
      collectTextContent(sibling.getNextSibling());
    }
  }

  private boolean hasNextSibling(Node sibling) {
    return sibling.getNextSibling() != null;
  }

  private boolean isEndNode(Node sibling) {
    return TEXT_REFERENCE_MARK_END.equals(sibling.getNodeName());
  }

  private OdfStylableElement findNearestStylableParent(Node child) {
    if (child instanceof OdfStylableElement) {
      return (OdfStylableElement) child;
    }
    else if (child.getParentNode() != null) {
      return findNearestStylableParent(child.getParentNode());
    }
    else {
      return null;
    }
  }

  public static final TextReference UNDEFINED = new TextReference() {
    @Override
    public boolean exists() {
      return false;
    }

    @Override
    public String getTextContent() {
      throw bomb();
    }

    @Override
    public OdfStylableElement getStylableElement() {
      throw bomb();
    }

    private UnsupportedOperationException bomb() {
      return new UnsupportedOperationException("Not supported for UNDEFINED TextReference");
    }
  };
}
