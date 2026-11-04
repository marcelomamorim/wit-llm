/*
 * @test
 * @summary Verifies PropertyResourceBundle(Reader) loads key/value pairs from a Reader
 * @run main PropertyResourceBundleWitupTest
 */

import java.io.StringReader;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    static void check(Object expected, Object actual, String what) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException(what + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void main(String[] args) throws Exception {
        String props = "a=1\nb=two\nunicode=\\u0041\nk=old\nk=new\n";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));

        check("1", bundle.getString("a"), "simple value");
        check("two", bundle.getString("b"), "second value");
        check("A", bundle.getString("unicode"), "unicode escape decoded");
        check("new", bundle.getString("k"), "last duplicate key wins");
        check(Boolean.TRUE, bundle.containsKey("a"), "containsKey present");
        check(Boolean.FALSE, bundle.containsKey("missing"), "containsKey absent");

        int count = 0;
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            keys.nextElement();
            count++;
        }
        check(4, count, "number of distinct keys");

        PropertyResourceBundle empty = new PropertyResourceBundle(new StringReader(""));
        check(Boolean.FALSE, empty.getKeys().hasMoreElements(), "empty reader yields no keys");

        boolean thrown = false;
        try {
            bundle.getString("missing");
        } catch (MissingResourceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("missing key should raise MissingResourceException");
        }
    }
}
