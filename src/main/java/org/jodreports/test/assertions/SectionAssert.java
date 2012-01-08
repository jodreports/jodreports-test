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

import org.fest.assertions.GenericAssert;
import org.jodreports.test.simpleodfextensions.OdfNameFinder;
import org.odftoolkit.odfdom.dom.element.text.TextHElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.text.Section;

import java.util.ArrayList;
import java.util.Collection;

public class SectionAssert extends GenericAssert<SectionAssert, Section> {

  protected SectionAssert(Section actual) {
    super(SectionAssert.class, actual);
  }

  public HeaderAssert headersWithText(String expectedText) {
    Collection<TextHElement> textHeadings = new ArrayList<TextHElement>();
    Collection<OdfElement> elements = new OdfNameFinder(TextHElement.ELEMENT_NAME).findElements(actual.getOdfElement());
    for (OdfElement element : elements) {
      if (expectedText.equals(element.getTextContent())) {
        textHeadings.add((TextHElement) element);
      }
    }
    return new HeaderAssert(textHeadings);
  }
}
