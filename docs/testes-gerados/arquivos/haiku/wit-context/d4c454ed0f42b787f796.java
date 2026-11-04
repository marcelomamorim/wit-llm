/*
 * @test
 * @summary Witup-derived checks for Properties.load(InputStream)
 * @run main PropertiesWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNpe();
        testLoadsSimpleEntries();
        testEmptyStreamLoadsNothing();
    }

    static void testNullInputThrowsNpe() {
        Properties p = new Properties();
        try {
            p.load((InputStream) null);
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e, e);
        }
        throw new RuntimeException("expected NullPointerException for null InputStream");
    }

    static void testLoadsSimpleEntries() throws Exception {
        Properties p = new Properties();
        byte[] data = "a=1\nb:two\n".getBytes(StandardCharsets.ISO_8859_1);
        p.load(new ByteArrayInputStream(data));
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("expected a=1 but got " + p.getProperty("a"));
        }
        if (!"two".equals(p.getProperty("b"))) {
            throw new RuntimeException("expected b=two but got " + p.getProperty("b"));
        }
        if (p.size() != 2) {
            throw new RuntimeException("expected 2 entries but got " + p.size());
        }
    }

    static void testEmptyStreamLoadsNothing() throws Exception {
        Properties p = new Properties();
        p.load(new ByteArrayInputStream(new byte[0]));
        if (p.size() != 0) {
            throw new RuntimeException("expected empty properties but got size " + p.size());
        }
    }
}
