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
package org.jodreports.test;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import org.odftoolkit.simple.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import static org.odftoolkit.simple.TextDocument.loadDocument;

public class DataMerger<T> {

  Logger logger = LoggerFactory.getLogger(DataMerger.class);

  private T dataModel;
  private DocumentTemplate documentTemplate;
  private ByteArrayOutputStream outputStream;

  private DataMerger(T dataModel) {
    this.dataModel = dataModel;
  }

  public static <T> DataMerger<T> mergeDataFrom(T request) {
    return new DataMerger<T>(request);
  }

  public DataMerger<T> into(DocumentTemplate template) {
    this.documentTemplate = template;
    return this;
  }

  public DataMerger<T> saveTo(ByteArrayOutputStream outputStream) throws DocumentTemplateException, IOException {
    validateModel();
    this.outputStream = outputStream;
    documentTemplate.createDocument(dataModel, this.outputStream);
    return this;
  }

  public TextDocument getAsDocument() throws Exception {
    return loadDocument(InputStreamFactory.from(outputStream));
  }

  private void validateModel() {
    final Validator validator = createValidator();
    final Set<ConstraintViolation<T>> violations = validator.validate(dataModel);
    if (!violations.isEmpty()) {
      handleViolations(violations);
      throw new ValidationException("Illegal Data Model");
    }
  }

  private void handleViolations(Set<ConstraintViolation<T>> violations) {
    logger.error("Failed attempt to merge data model into ODT document template.");
    logger.error("Violations:");
    for (ConstraintViolation<T> violation : violations) {
      logger.error("    Property " + violation.getPropertyPath() + ": " + violation.getMessage());
    }
  }

  private Validator createValidator() {
    final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    return validatorFactory.getValidator();
  }
}
