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
import org.jodreports.test.simpleodfextensions.TextReference;

import static org.fest.assertions.Formatting.format;

public class TextReferenceAssert extends GenericAssert<TextReferenceAssert, TextReference> {

  public TextReferenceAssert(TextReference textReference) {
    super(TextReferenceAssert.class, textReference);
  }

  public TextReferenceAssert exists() {
    isNotNull();
    if (actual.exists()) {
      return myself;
    }
    failIfCustomMessageIsSet();
    throw failure(format("Text reference with name <%s> should exist.", actual.getName()));
  }

  public TextReferenceAssert showsText(String expected) {
    String textContent = actual.getTextContent();
    Assertions.assertThat(textContent).isEqualTo(expected);

    DocumentAssertions.assertThat(actual.getStylableElement()).isDisplayed();
    return myself;
  }
}
