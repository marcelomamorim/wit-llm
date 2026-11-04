/*
 * @test
 * @summary Checks PropertyResourceBundle(InputStream) loads ASCII properties, exposes keys, and handles an empty stream
 * @run main PropertyResourceBundleWitupTest
 */

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class PropertyResourceBundleWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] data = "greeting=hello\ncount=42\n".getBytes(StandardCharsets.ISO_8859_1);
        PropertyResourceBundle prb =
            new PropertyResourceBundle(new ByteArrayInputStream(data));

        check("hello".equals(prb.getString("greeting")), "greeting value mismatch");
        check("42".equals(prb.getString("count")), "count value mismatch");

        Set<String> keys = new HashSet<>(Collections.list(prb.getKeys()));
        check(keys.equals(new HashSet<>(Arrays.asList("greeting", "count"))),
              "keys mismatch: " + keys);

        boolean thrown = false;
        try {
            prb.getString("missing");
        } catch (MissingResourceException e) {
            thrown = true;
        }
        check(thrown, "missing key should throw MissingResourceException");

        PropertyResourceBundle empty =
            new PropertyResourceBundle(new ByteArrayInputStream(new byte[0]));
        check(!empty.getKeys().hasMoreElements(), "empty bundle should have no keys");
    }
}
