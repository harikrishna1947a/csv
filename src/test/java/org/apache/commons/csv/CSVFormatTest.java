/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.csv;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.csv.Constants.CR;
import static org.apache.commons.csv.Constants.CRLF;
import static org.apache.commons.csv.Constants.LF;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 * @version $Id$
 */
public class CSVFormatTest {
    
    
	@Before
	public void init() {
		System.out.println( "Hello World! test" );
	}
	
    private static void assertNotEquals(final Object right, final Object left) {
	    System.out.println( "Hello World! test1" );
        assertFalse(right.equals(left));
        assertFalse(left.equals(right));
    }

    private static CSVFormat copy(final CSVFormat format) {
	    System.out.println( "Hello World! test2" );
        return format.withDelimiter(format.getDelimiter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelimiterSameAsCommentStartThrowsException() {
	    System.out.println( "Hello World! test3" );
        CSVFormat.DEFAULT.withDelimiter('!').withCommentMarker('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelimiterSameAsEscapeThrowsException() {
	    System.out.println( "Hello World! test4" );
        CSVFormat.DEFAULT.withDelimiter('!').withEscape('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateHeaderElements() {
	    System.out.println( "Hello World! test5" );
        CSVFormat.DEFAULT.withHeader("A", "A");
    }

    @Test
    public void testEquals() {
	    System.out.println( "Hello World! test6" );
        final CSVFormat right = CSVFormat.DEFAULT;
        final CSVFormat left = copy(right);

        assertFalse(right.equals(null));
        assertFalse(right.equals("A String Instance"));

        assertEquals(right, right);
        assertEquals(right, left);
        assertEquals(left, right);

        assertEquals(right.hashCode(), right.hashCode());
        assertEquals(right.hashCode(), left.hashCode());
    }

    @Test
    public void testEqualsCommentStart() {
	    System.out.println( "Hello World! test7" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withQuote('"')
                .withCommentMarker('#')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withCommentMarker('!');

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsDelimiter() {
	    System.out.println( "Hello World! test9" );
        final CSVFormat right = CSVFormat.newFormat('!');
        final CSVFormat left = CSVFormat.newFormat('?');

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsEscape() {
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withQuote('"')
                .withCommentMarker('#')
                .withEscape('+')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withEscape('!');

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsHeader() {
	    System.out.println( "Hello World! test8" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withRecordSeparator(CR)
                .withCommentMarker('#')
                .withEscape('+')
                .withHeader("One", "Two", "Three")
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withHeader("Three", "Two", "One");

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsIgnoreEmptyLines() {
	    System.out.println( "Hello World! test10" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withCommentMarker('#')
                .withEscape('+')
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withIgnoreEmptyLines(false);

        assertNotEquals(right, left);
    }

   

    @Test
    public void testEqualsQuoteChar() {
	      System.out.println( "Hello World! test31" );
        final CSVFormat right = CSVFormat.newFormat('\'').withQuote('"');
        final CSVFormat left = right.withQuote('!');

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsQuotePolicy() {
	      System.out.println( "Hello World! test32" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withQuoteMode(QuoteMode.MINIMAL);

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsRecordSeparator() {
	      System.out.println( "Hello World! test33" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withRecordSeparator(CR)
                .withCommentMarker('#')
                .withEscape('+')
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL);
        final CSVFormat left = right
                .withRecordSeparator(LF);

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsNullString() {
	    System.out.println( "Hello World! test12" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withRecordSeparator(CR)
                .withCommentMarker('#')
                .withEscape('+')
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL)
                .withNullString("null");
        final CSVFormat left = right
                .withNullString("---");

        assertNotEquals(right, left);
    }

    @Test
    public void testEqualsSkipHeaderRecord() {
	    System.out.println( "Hello World! test13" );
        final CSVFormat right = CSVFormat.newFormat('\'')
                .withRecordSeparator(CR)
                .withCommentMarker('#')
                .withEscape('+')
                .withIgnoreEmptyLines()
                .withIgnoreSurroundingSpaces()
                .withQuote('"')
                .withQuoteMode(QuoteMode.ALL)
                .withNullString("null")
                .withSkipHeaderRecord();
        final CSVFormat left = right
                .withSkipHeaderRecord(false);

        assertNotEquals(right, left);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEscapeSameAsCommentStartThrowsException() {
	      System.out.println( "Hello World! test34" );
        CSVFormat.DEFAULT.withEscape('!').withCommentMarker('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEscapeSameAsCommentStartThrowsExceptionForWrapperType() {
	      System.out.println( "Hello World! test35" );
        // Cannot assume that callers won't use different Character objects
        CSVFormat.DEFAULT.withEscape(new Character('!')).withCommentMarker(new Character('!'));
    }

    @Test
    public void testFormat() {
	    System.out.println( "Hello World! test14" );
        final CSVFormat format = CSVFormat.DEFAULT;

        assertEquals("", format.format());
        assertEquals("a,b,c", format.format("a", "b", "c"));
        assertEquals("\"x,y\",z", format.format("x,y", "z"));
    }

    @Test
    public void testGetHeader() throws Exception {
	    System.out.println( "Hello World! test15" );
        final String[] header = new String[]{"one", "two", "three"};
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.withHeader(header);
        // getHeader() makes a copy of the header array.
        final String[] headerCopy = formatWithHeader.getHeader();
        headerCopy[0] = "A";
        headerCopy[1] = "B";
        headerCopy[2] = "C";
        assertFalse(Arrays.equals(formatWithHeader.getHeader(), headerCopy));
        assertNotSame(formatWithHeader.getHeader(), headerCopy);
    }

    @Test
    public void testNullRecordSeparatorCsv106() {
	      System.out.println( "Hello World! test36" );
        final CSVFormat format = CSVFormat.newFormat(';').withSkipHeaderRecord().withHeader("H1", "H2");
        final String formatStr = format.format("A", "B");
        assertNotNull(formatStr);
        assertFalse(formatStr.endsWith("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuoteCharSameAsCommentStartThrowsException() {
	      System.out.println( "Hello World! test37" );
        CSVFormat.DEFAULT.withQuote('!').withCommentMarker('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuoteCharSameAsCommentStartThrowsExceptionForWrapperType() {
	      System.out.println( "Hello World! test38" );
        // Cannot assume that callers won't use different Character objects
        CSVFormat.DEFAULT.withQuote(new Character('!')).withCommentMarker('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuoteCharSameAsDelimiterThrowsException() {
	      System.out.println( "Hello World! test39" );
        CSVFormat.DEFAULT.withQuote('!').withDelimiter('!');
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuotePolicyNoneWithoutEscapeThrowsException() {
	      System.out.println( "Hello World! test40" );
        CSVFormat.newFormat('!').withQuoteMode(QuoteMode.NONE);
    }

    @Test
    public void testRFC4180() {
	    System.out.println( "Hello World! test17" );
        assertEquals(null, RFC4180.getCommentMarker());
        assertEquals(',', RFC4180.getDelimiter());
        assertEquals(null, RFC4180.getEscapeCharacter());
        assertFalse(RFC4180.getIgnoreEmptyLines());
        assertEquals(Character.valueOf('"'), RFC4180.getQuoteCharacter());
        assertEquals(null, RFC4180.getQuoteMode());
        assertEquals("\r\n", RFC4180.getRecordSeparator());
    }

    @SuppressWarnings("boxing") // no need to worry about boxing here
    @Test
    public void testSerialization() throws Exception {
	    System.out.println( "Hello World! test16" );
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(CSVFormat.DEFAULT);
        oos.flush();
        oos.close();

        final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
        final CSVFormat format = (CSVFormat) in.readObject();

        assertNotNull(format);
        assertEquals("delimiter", CSVFormat.DEFAULT.getDelimiter(), format.getDelimiter());
        assertEquals("encapsulator", CSVFormat.DEFAULT.getQuoteCharacter(), format.getQuoteCharacter());
        assertEquals("comment start", CSVFormat.DEFAULT.getCommentMarker(), format.getCommentMarker());
        assertEquals("record separator", CSVFormat.DEFAULT.getRecordSeparator(), format.getRecordSeparator());
        assertEquals("escape", CSVFormat.DEFAULT.getEscapeCharacter(), format.getEscapeCharacter());
        assertEquals("trim", CSVFormat.DEFAULT.getIgnoreSurroundingSpaces(), format.getIgnoreSurroundingSpaces());
        assertEquals("empty lines", CSVFormat.DEFAULT.getIgnoreEmptyLines(), format.getIgnoreEmptyLines());
    }

    @Test
    public void testWithCommentStart() throws Exception {
        final CSVFormat formatWithCommentStart = CSVFormat.DEFAULT.withCommentMarker('#');
        assertEquals( Character.valueOf('#'), formatWithCommentStart.getCommentMarker());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithCommentStartCRThrowsException() {
	    System.out.println( "Hello World! test18" );
        CSVFormat.DEFAULT.withCommentMarker(CR);
    }

    @Test
    public void testWithDelimiter() throws Exception {
	    System.out.println( "Hello World! test19" );
        final CSVFormat formatWithDelimiter = CSVFormat.DEFAULT.withDelimiter('!');
        assertEquals('!', formatWithDelimiter.getDelimiter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithDelimiterLFThrowsException() {
	      System.out.println( "Hello World! test41" );
        CSVFormat.DEFAULT.withDelimiter(LF);
    }

    @Test
    public void testWithEscape() throws Exception {
	      System.out.println( "Hello World! test42" );
        final CSVFormat formatWithEscape = CSVFormat.DEFAULT.withEscape('&');
        assertEquals(Character.valueOf('&'), formatWithEscape.getEscapeCharacter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEscapeCRThrowsExceptions() {
	      System.out.println( "Hello World! test43" );
        CSVFormat.DEFAULT.withEscape(CR);
    }

    @Test
    public void testWithHeader() throws Exception {
	    System.out.println( "Hello World! test20" );
        final String[] header = new String[]{"one", "two", "three"};
        // withHeader() makes a copy of the header array.
        final CSVFormat formatWithHeader = CSVFormat.DEFAULT.withHeader(header);
        assertArrayEquals(header, formatWithHeader.getHeader());
        assertNotSame(header, formatWithHeader.getHeader());
        header[0] = "A";
        header[1] = "B";
        header[2] = "C";
        assertFalse(Arrays.equals(formatWithHeader.getHeader(), header));
    }

    @Test
    public void testJiraCsv154_withCommentMarker() throws IOException {
	    System.out.println( "Hello World! test21" );
        final String comment = "This is a header comment";
        CSVFormat format = CSVFormat.EXCEL.withHeader("H1", "H2").withCommentMarker('#').withHeaderComments(comment);
        StringBuilder out = new StringBuilder();
        final CSVPrinter printer = format.print(out);
        printer.print("A");
        printer.print("B");
        printer.close();
        String s = out.toString();
        Assert.assertTrue(s, s.contains(comment));
    }

    @Test
    public void testJiraCsv154_withHeaderComments() throws IOException {
	    System.out.println( "Hello World! test22" );
        final String comment = "This is a header comment";
        CSVFormat format = CSVFormat.EXCEL.withHeader("H1", "H2").withHeaderComments(comment).withCommentMarker('#');
        StringBuilder out = new StringBuilder();
        final CSVPrinter printer = format.print(out);
        printer.print("A");
        printer.print("B");
        printer.close();
        String s = out.toString();
        Assert.assertTrue(s, s.contains(comment));
    }
    
    @Test
    public void testWithIgnoreEmptyLines() throws Exception {
	    System.out.println( "Hello World! test23" );
        assertFalse(CSVFormat.DEFAULT.withIgnoreEmptyLines(false).getIgnoreEmptyLines());
        assertTrue(CSVFormat.DEFAULT.withIgnoreEmptyLines().getIgnoreEmptyLines());
    }

    @Test
    public void testWithIgnoreSurround() throws Exception {
	    System.out.println( "Hello World! test24" );
        assertFalse(CSVFormat.DEFAULT.withIgnoreSurroundingSpaces(false).getIgnoreSurroundingSpaces());
        assertTrue(CSVFormat.DEFAULT.withIgnoreSurroundingSpaces().getIgnoreSurroundingSpaces());
    }

    @Test
    public void testWithNullString() throws Exception {
	    System.out.println( "Hello World! test24" );
        final CSVFormat formatWithNullString = CSVFormat.DEFAULT.withNullString("null");
        assertEquals("null", formatWithNullString.getNullString());
    }

    @Test
    public void testWithQuoteChar() throws Exception {
	    System.out.println( "Hello World! test25" );
        final CSVFormat formatWithQuoteChar = CSVFormat.DEFAULT.withQuote('"');
        assertEquals(Character.valueOf('"'), formatWithQuoteChar.getQuoteCharacter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithQuoteLFThrowsException() {
	    System.out.println( "Hello World! test26" );
        CSVFormat.DEFAULT.withQuote(LF);
    }

    @Test
    public void testWithQuotePolicy() throws Exception {
	    System.out.println( "Hello World! test27" );
        final CSVFormat formatWithQuotePolicy = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL);
        assertEquals(QuoteMode.ALL, formatWithQuotePolicy.getQuoteMode());
    }

    @Test
    public void testWithRecordSeparatorCR() throws Exception {
	    System.out.println( "Hello World! test28" );
        final CSVFormat formatWithRecordSeparator = CSVFormat.DEFAULT.withRecordSeparator(CR);
        assertEquals(String.valueOf(CR), formatWithRecordSeparator.getRecordSeparator());
    }

    @Test
    public void testWithRecordSeparatorLF() throws Exception {
	    System.out.println( "Hello World! test29" );
        final CSVFormat formatWithRecordSeparator = CSVFormat.DEFAULT.withRecordSeparator(LF);
        assertEquals(String.valueOf(LF), formatWithRecordSeparator.getRecordSeparator());
    }

    @Test
    public void testWithRecordSeparatorCRLF() throws Exception {
	    System.out.println( "Hello World! test30" );
        final CSVFormat formatWithRecordSeparator = CSVFormat.DEFAULT.withRecordSeparator(CRLF);
        assertEquals(CRLF, formatWithRecordSeparator.getRecordSeparator());
    }
}
