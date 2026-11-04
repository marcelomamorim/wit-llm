/*
 * @test
 * @summary Witup-derived checks for PropertyResourceBundle(InputStream)
 * @run main PropertyResourceBundleWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] data = "greeting=hello\nname=\\u0057orld\n".getBytes("ISO-8859-1");
        InputStream in = new ByteArrayInputStream(data);
        PropertyResourceBundle bundle = new PropertyResourceBundle(in);
        if (!"hello".equals(bundle.getString("greeting"))) {
            throw new RuntimeException("unexpected value for greeting");
        }
        if (!"World".equals(bundle.getString("name"))) {
            throw new RuntimeException("unicode escape not decoded");
        }

        PropertyResourceBundle empty =
            new PropertyResourceBundle(new ByteArrayInputStream(new byte[0]));
        if (!empty.keySet().isEmpty()) {
            throw new RuntimeException("empty stream should yield no keys");
        }

        try {
            new PropertyResourceBundle((InputStream) null);
            throw new RuntimeException("expected NullPointerException for null stream");
        } catch (NullPointerException expected) {
        }
    }
}
