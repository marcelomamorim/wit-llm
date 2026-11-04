/*
 * @test
 * @summary Black-box tests for the PropertyResourceBundle(Reader) constructor
 * @run main PropertyResourceBundleWitupTest
 */
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws Exception {
        testBasicLookup();
        testMissingKeyThrows();
        testDuplicateKeyLastWins();
        testEmptyReader();
        testKeysEnumeration();
        testNullReaderThrowsNpe();
    }

    static void testBasicLookup() throws Exception {
        PropertyResourceBundle b = new PropertyResourceBundle(new StringReader("s1=Hello\ns2=World\n"));
        check("Hello".equals(b.getString("s1")), "s1 expected Hello");
        check("World".equals(b.getString("s2")), "s2 expected World");
    }

    static void testMissingKeyThrows() throws Exception {
        PropertyResourceBundle b = new PropertyResourceBundle(new StringReader("s1=Hello\n"));
        boolean thrown = false;
        try {
            b.getString("absent");
        } catch (MissingResourceException e) {
            thrown = true;
        }
        check(thrown, "missing key should throw MissingResourceException");
    }

    static void testDuplicateKeyLastWins() throws Exception {
        PropertyResourceBundle b = new PropertyResourceBundle(new StringReader("k=first\nk=second\n"));
        check("second".equals(b.getString("k")), "duplicate key should keep last value");
    }

    static void testEmptyReader() throws Exception {
        PropertyResourceBundle b = new PropertyResourceBundle(new StringReader(""));
        check(!b.getKeys().hasMoreElements(), "empty input should yield no keys");
    }

    static void testKeysEnumeration() throws Exception {
        PropertyResourceBundle b = new PropertyResourceBundle(new StringReader("a=1\nb=2\nc=3\n"));
        Set<String> keys = new HashSet<>();
        Enumeration<String> e = b.getKeys();
        while (e.hasMoreElements()) {
            keys.add(e.nextElement());
        }
        Set<String> expected = new HashSet<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        check(keys.equals(expected), "keys enumeration mismatch: " + keys);
    }

    static void testNullReaderThrowsNpe() throws Exception {
        boolean thrown = false;
        try {
            new PropertyResourceBundle((Reader) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null reader should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
