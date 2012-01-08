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
package org.jodreports.testing;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Instructs JodReportsTest to dump the generated ODT document inside {@link JodReportsTest#instanceData} into a file. If no
 * filename is specified, a generated one will be used, otherwise the one specified as {@link #toFile()}.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface DumpGeneratedDocument {

  /** Name of file to dump the generated document instance into. */
  String toFile() default "";
}
