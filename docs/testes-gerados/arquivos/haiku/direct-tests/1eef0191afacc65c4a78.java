/*
 * @test
 * @summary Verify characters() accumulates entry text only inside entry elements
 * @modules java.base/jdk.internal.util.xml
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        checkValue("<properties><entry key=\"k\">value</entry></properties>", "value");
        checkValue("<properties><entry key=\"k\">a&amp;b</entry></properties>", "a&b");
        checkValue("<properties><entry key=\"k\">x&lt;y&gt;z</entry></properties>", "x<y>z");
        checkIgnoresTextOutsideEntry();
    }

    private static void checkValue(String xml, String expected) throws Exception {
        Properties props = new Properties();
        load(props, xml);
        String actual = props.getProperty("k");
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }

    private static void checkIgnoresTextOutsideEntry() throws Exception {
        Properties props = new Properties();
        load(props, "<properties>outside<entry key=\"k\">v</entry>tail</properties>");
        if (props.size() != 1) {
            throw new RuntimeException("Expected 1 property but got " + props.size());
        }
        if (!"v".equals(props.getProperty("k"))) {
            throw new RuntimeException("Unexpected value: " + props.getProperty("k"));
        }
    }

    private static void load(Properties props, String xml) throws Exception {
        try (InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            new PropertiesDefaultHandler().load(props, in);
        }
    }
}
