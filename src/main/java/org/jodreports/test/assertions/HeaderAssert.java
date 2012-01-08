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

import org.fest.assertions.GenericAssert;
import org.odftoolkit.odfdom.dom.element.text.TextHElement;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

public class HeaderAssert extends GenericAssert<HeaderAssert, Collection<TextHElement>> {

  public HeaderAssert(Collection<TextHElement> actual) {
    super(HeaderAssert.class, actual);
  }

  public HeaderAssert hasSize(int size) {
    assertThat(actual).hasSize(size);
    return myself;
  }
}
