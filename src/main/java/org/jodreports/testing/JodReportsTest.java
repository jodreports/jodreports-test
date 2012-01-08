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

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class JodReportsTest implements IHookable {

  private ByteArrayOutputStream instanceData;

  static final String TARGET_DIRECTORY_NAME = "target" + File.separator + "generated-documents";

  @BeforeMethod(alwaysRun = true)
  public void setUpInstanceData() {
    instanceData = new ByteArrayOutputStream();
  }

//  static {
//    try {
//      final InputStream resourceAsStream = JodReportsTest.class.getResourceAsStream("/logging.properties");
//      LogManager.getLogManager().readConfiguration(resourceAsStream);
//    }
//    catch (IOException e) {
//      System.out.println("Unable to initialize Java logging");
//      System.exit(0);
//    }
//  }

  public void run(IHookCallBack callBack, ITestResult testResult) {
    callBack.runTestMethod(testResult);
    processDumpGeneratedDocumentAnnotation(testResult);
  }

  private void processDumpGeneratedDocumentAnnotation(ITestResult testResult) {
    final Method testMethod = testResult.getMethod().getMethod();
    final DumpGeneratedDocument dumpGeneratedDocument = testMethod.getAnnotation(DumpGeneratedDocument.class);
    if (dumpGeneratedDocument == null) {
      return;
    }

    final String targetFileName;
    if (!"".equals(dumpGeneratedDocument.toFile())) {
      targetFileName = dumpGeneratedDocument.toFile();
    }
    else {
      targetFileName = generateTargetFileName(testMethod);
    }
    dumpGeneratedDocument(instanceData, targetFileName);
  }

  private String generateTargetFileName(Method testMethod) {
    return testMethod.getDeclaringClass().getSimpleName() + "_" + testMethod.getName() + "_instance.odt";
  }

  private void dumpGeneratedDocument(ByteArrayOutputStream documentContent, String targetFileName) {
    try {
      new File(TARGET_DIRECTORY_NAME).mkdirs();

      String fileName = TARGET_DIRECTORY_NAME + File.separator + targetFileName;

      FileOutputStream fos = new FileOutputStream(fileName, false);
      fos.write(documentContent.toByteArray());
    }
    catch (IOException e) {
      throw new RuntimeException("Cannot dump document content", e);
    }
  }

  protected ByteArrayOutputStream getInstanceData() {
    return instanceData;
  }
}
