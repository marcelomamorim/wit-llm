/*
 * @test
 * @summary Verifies that Provider.load(InputStream) stores properties read from the stream and keeps existing entries.
 * @run main ProviderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", 1.0, "test info");
        }
    }

    static InputStream stream(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.ISO_8859_1));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testLoadSingleProperty() throws IOException {
        TestProvider p = new TestProvider();
        p.load(stream("WitupKey=WitupValue\n"));
        check("WitupValue".equals(p.getProperty("WitupKey")),
              "loaded property not visible via getProperty");
    }

    static void testLoadMultipleProperties() throws IOException {
        TestProvider p = new TestProvider();
        p.load(stream("alpha=1\nbeta=2\n"));
        check("1".equals(p.getProperty("alpha")), "alpha not loaded");
        check("2".equals(p.getProperty("beta")), "beta not loaded");
    }

    static void testLoadEmptyStreamKeepsExisting() throws IOException {
        TestProvider p = new TestProvider();
        p.load(stream("keep=yes\n"));
        p.load(stream(""));
        check("yes".equals(p.getProperty("keep")),
              "existing property lost after loading empty stream");
    }

    static void testLoadOverwritesExistingValue() throws IOException {
        TestProvider p = new TestProvider();
        p.load(stream("dup=first\n"));
        p.load(stream("dup=second\n"));
        check("second".equals(p.getProperty("dup")),
              "value not overwritten by second load");
    }

    static void testLoadCommentsIgnored() throws IOException {
        TestProvider p = new TestProvider();
        p.load(stream("# comment\nreal=ok\n"));
        check("ok".equals(p.getProperty("real")), "property after comment not loaded");
        check(p.getProperty("# comment") == null, "comment was stored as property");
    }

    public static void main(String[] args) throws Exception {
        testLoadSingleProperty();
        testLoadMultipleProperties();
        testLoadEmptyStreamKeepsExisting();
        testLoadOverwritesExistingValue();
        testLoadCommentsIgnored();
    }
}
