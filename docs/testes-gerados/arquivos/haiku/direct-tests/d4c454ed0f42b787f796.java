/*
 * @test
 * @summary Behavioral tests for Properties.load(InputStream) using public API only
 * @run main PropertiesWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) throws IOException {
        testNullInputThrowsNullPointerException();
        testSimpleKeyValue();
        testCommentLinesAreIgnored();
        testLineContinuation();
        testUnicodeEscapeDecoded();
        testDuplicateKeyLastWins();
        testColonSeparator();
        testEmptyValue();
        testDefaultsFallbackAfterLoad();
        testExistingEntriesArePreserved();
    }

    static InputStream in(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.ISO_8859_1));
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }

    static void testNullInputThrowsNullPointerException() {
        Properties p = new Properties();
        boolean thrown = false;
        try {
            p.load((InputStream) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new AssertionError("unexpected IOException", e);
        }
        check(thrown, "load(null) must throw NullPointerException");
    }

    static void testSimpleKeyValue() throws IOException {
        Properties p = new Properties();
        p.load(in("key=value\n"));
        check("value".equals(p.getProperty("key")), "simple key/value not loaded");
    }

    static void testCommentLinesAreIgnored() throws IOException {
        Properties p = new Properties();
        p.load(in("#comment\n!another\nk=v\n"));
        check(p.size() == 1, "comment lines must not create entries, size=" + p.size());
        check("v".equals(p.getProperty("k")), "value after comments not loaded");
    }

    static void testLineContinuation() throws IOException {
        Properties p = new Properties();
        p.load(in("k=a\\\n   b\n"));
        check("ab".equals(p.getProperty("k")), "line continuation not handled, got: "
                + p.getProperty("k"));
    }

    static void testUnicodeEscapeDecoded() throws IOException {
        Properties p = new Properties();
        p.load(in("k=\\u0041\n"));
        check("A".equals(p.getProperty("k")), "unicode escape not decoded, got: "
                + p.getProperty("k"));
    }

    static void testDuplicateKeyLastWins() throws IOException {
        Properties p = new Properties();
        p.load(in("k=1\nk=2\n"));
        check("2".equals(p.getProperty("k")), "last duplicate key must win");
    }

    static void testColonSeparator() throws IOException {
        Properties p = new Properties();
        p.load(in("k:v\n"));
        check("v".equals(p.getProperty("k")), "colon separator not supported");
    }

    static void testEmptyValue() throws IOException {
        Properties p = new Properties();
        p.load(in("k=\n"));
        check(p.containsKey("k"), "key with empty value must be present");
        check("".equals(p.getProperty("k")), "empty value must be empty string");
    }

    static void testDefaultsFallbackAfterLoad() throws IOException {
        Properties defaults = new Properties();
        defaults.setProperty("d", "default");
        Properties p = new Properties(defaults);
        p.load(in("k=v\n"));
        check("default".equals(p.getProperty("d")), "defaults must still be consulted");
        check("v".equals(p.getProperty("k")), "loaded value not visible");
        check(p.getProperty("missing") == null, "missing key must return null");
    }

    static void testExistingEntriesArePreserved() throws IOException {
        Properties p = new Properties();
        p.setProperty("a", "1");
        p.load(in("b=2\n"));
        check("1".equals(p.getProperty("a")), "existing entry must be preserved");
        check("2".equals(p.getProperty("b")), "new entry must be loaded");
    }
}
