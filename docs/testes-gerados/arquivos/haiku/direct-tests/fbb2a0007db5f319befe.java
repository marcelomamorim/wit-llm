/*
 * @test
 * @summary Behavioral tests for Properties.load(Reader) using only public API
 * @run main PropertiesWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

public class PropertiesWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) throws IOException {
        testSimpleKeyValues();
        testCommentsAreIgnored();
        testWhitespaceSeparator();
        testKeyWithoutValue();
        testLineContinuation();
        testUnicodeEscape();
        testNullReaderThrowsNpe();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testSimpleKeyValues() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("a=1\nb=2\n"));
        check("1".equals(p.getProperty("a")), "a should be 1");
        check("2".equals(p.getProperty("b")), "b should be 2");
        check(p.size() == 2, "size should be 2 but was " + p.size());
    }

    private static void testCommentsAreIgnored() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("#comment\n!another\n\n"));
        check(p.isEmpty(), "comment and blank lines should yield no entries");
    }

    private static void testWhitespaceSeparator() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("k   v\n"));
        check("v".equals(p.getProperty("k")), "whitespace separator should yield value v");
    }

    private static void testKeyWithoutValue() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("k\n"));
        check(p.containsKey("k"), "key without separator should be present");
        check("".equals(p.getProperty("k")), "value should be empty string");
    }

    private static void testLineContinuation() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("k=line1\\\nline2\n"));
        check("line1line2".equals(p.getProperty("k")),
              "continued line should be joined, got: " + p.getProperty("k"));
    }

    private static void testUnicodeEscape() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("k=\\u0041\n"));
        check("A".equals(p.getProperty("k")), "unicode escape should decode to A");
    }

    private static void testNullReaderThrowsNpe() {
        Properties p = new Properties();
        try {
            p.load((Reader) null);
            check(false, "null reader should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            check(false, "unexpected IOException: " + e);
        }
    }
}
