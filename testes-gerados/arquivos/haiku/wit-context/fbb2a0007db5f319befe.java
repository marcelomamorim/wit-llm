/*
 * @test
 * @summary Checks Properties.load(Reader) null-argument rejection and basic observable loading behavior
 * @run main PropertiesWitupTest
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) throws Exception {
        testNullReaderThrowsNPE();
        testLoadSimplePair();
        testLoadMultiplePairsAndComment();
        testLoadEmptyReaderLeavesPropertiesEmpty();
    }

    static void testNullReaderThrowsNPE() {
        Properties p = new Properties();
        boolean thrown = false;
        try {
            p.load((java.io.Reader) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        check(thrown, "load(null Reader) must throw NullPointerException");
    }

    static void testLoadSimplePair() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("key=value"));
        check("value".equals(p.getProperty("key")), "key must map to value");
    }

    static void testLoadMultiplePairsAndComment() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader("# comment line\na=1\nb=2\n"));
        check("1".equals(p.getProperty("a")), "a must map to 1");
        check("2".equals(p.getProperty("b")), "b must map to 2");
        check(p.size() == 2, "comment line must not create an entry, size was " + p.size());
    }

    static void testLoadEmptyReaderLeavesPropertiesEmpty() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader(""));
        check(p.isEmpty(), "empty input must leave Properties empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
