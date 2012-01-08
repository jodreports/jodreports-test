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

import org.jodreports.test.JodReportsTest;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.jodreports.test.simpleodfextensions.TextDocumentFunctions.getTextReferenceByName;
import static org.odftoolkit.simple.TextDocument.loadDocument;

public class TextReferenceExtractorTest extends JodReportsTest {

  private static final String TEXT_REF_TEXT_REFERENCE = "TextReference";
  private static final String TEXT_REF_TEXT_REFERENCE_IN_PARAGRAPH = "TextReferenceInSeverealParagraphs";
  private static final String TEXT_REF_MULTIPLE_ONE = "MultipleTextRefOne";
  private static final String TEXT_REF_MULTIPLE_TWO = "MultipleTextRefTwo";
  private static final String TEXT_REF_MULTIPLE_THREE = "MultipleTextRefThree";
  private static final String TEXT_REF_TABLE_TOP_LEFT = "TableTextRef1";
  private static final String TEXT_REF_TABLE_BOTTOM_RIGHT = "TableTextRef2";
  private static final String SECTION_NAME_1 = "Section1";
  private static final String SECTION_NAME_2 = "Section2";
  private static final String SECTION_NAME_3 = "Section3";
  private static final String TABLE_NAME_1 = "Table1";

  private TextDocument textDocument;

  @BeforeClass
  public void setUp() throws Exception {
    URL resource = getClass().getResource("TextReferenceExtractorTest.odt");
    textDocument = loadDocument(resource.openStream());
  }

  @Test
  public void testOverallTextReferenceCountIsCorrect() {
    Collection<TextReference> textReferences = new TextReferenceExtractor(textDocument).getTextReferences();
    assertThat(textReferences).hasSize(7);
  }

  @Test
  public void testSimpleTextReferenceIsFoundGlobally() {
    TextReference textReferenceByName = getTextReferenceByName(textDocument, TEXT_REF_TEXT_REFERENCE);
    assertThat(textReferenceByName.exists()).isTrue();
  }

  @Test
  public void testFirstParagraphContainsOneTextReference() {
    Paragraph firstParagraph = textDocument.getParagraphByIndex(0, false);
    TextReference textReferenceByName = getTextReferenceByName(firstParagraph, TEXT_REF_TEXT_REFERENCE);
    assertThat(textReferenceByName.exists()).isTrue();
  }

  @Test
  public void testFirstTextReferenceContainsCorrectText() {
    Paragraph firstParagraph = textDocument.getParagraphByIndex(0, false);
    TextReference textReferenceByName = getTextReferenceByName(firstParagraph, TEXT_REF_TEXT_REFERENCE);
    assertThat(textReferenceByName.getTextContent()).isEqualTo("TextReference");
  }

  @Test
  public void testNoTextReferenceIsFoundInFirstSection() {
    Section sectionByName = textDocument.getSectionByName(SECTION_NAME_1);
    Collection<TextReference> textReferences = new TextReferenceExtractor(sectionByName).getTextReferences();
    assertThat(textReferences).isEmpty();
  }

  @Test
  public void testTextReferenceIsFoundInSecondSection() {
    Section sectionByName = textDocument.getSectionByName(SECTION_NAME_2);
    TextReference textReferenceByName = getTextReferenceByName(sectionByName, TEXT_REF_TEXT_REFERENCE_IN_PARAGRAPH);
    assertThat(textReferenceByName.exists()).isTrue();
  }

  @Test
  public void testSecondTextReferenceContainsCorrectText() {
    Section sectionByName = textDocument.getSectionByName(SECTION_NAME_2);
    TextReference textReferenceByName = getTextReferenceByName(sectionByName, TEXT_REF_TEXT_REFERENCE_IN_PARAGRAPH);
    assertThat(textReferenceByName.getTextContent()).isEqualTo("AnotherTextReference");
  }

  @Test
  public void testThirdSectionContainsThreeTextReferences() {
    Section sectionByName = textDocument.getSectionByName(SECTION_NAME_3);
    Collection<TextReference> textReferences = new TextReferenceExtractor(sectionByName).getTextReferences();
    assertThat(textReferences).hasSize(3);
  }

  @Test(dataProvider = "getTextReferencesWithExpectedTextContentForSection3")
  public void testThirdSectionContainsExpectedTextReferencesWithCorrectContent(String textReferenceName, String expectedContent) {
    Section sectionByName = textDocument.getSectionByName(SECTION_NAME_3);
    TextReference textReferenceByName = getTextReferenceByName(sectionByName, textReferenceName);
    assertThat(textReferenceByName.getTextContent()).isEqualTo(expectedContent);
  }

  @DataProvider
  private Object[][] getTextReferencesWithExpectedTextContentForSection3() {
    return new Object[][] {
        {TEXT_REF_MULTIPLE_ONE, "more"},
        {TEXT_REF_MULTIPLE_TWO, "than"},
        {TEXT_REF_MULTIPLE_THREE, "possible"},
    };
  }

  @Test(dataProvider = "getTextReferencesWithExpectedTextContentForTable1")
  public void testTableContainsExpectedTextReferencesWithCorrectContent(String textReferenceName, String expectedContent) {
    Table tableByName = textDocument.getTableByName(TABLE_NAME_1);
    TextReference textReferenceByName = getTextReferenceByName(tableByName, textReferenceName);
    assertThat(textReferenceByName.getTextContent()).isEqualTo(expectedContent);
  }

  @DataProvider
  private Object[][] getTextReferencesWithExpectedTextContentForTable1() {
    return new Object[][] {
        {TEXT_REF_TABLE_TOP_LEFT, "pLef"},
        {TEXT_REF_TABLE_BOTTOM_RIGHT, "Bottom"},
    };
  }
}
