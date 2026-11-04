/*
 * @test
 * @summary Verifies Provider.load(InputStream) loads properties, overwrites existing keys and propagates IOException
 * @run main ProviderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;

public class ProviderWitupTest {

    static final class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test provider");
        }
    }

    static final class FailingInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException("boom");
        }
    }

    public static void main(String[] args) throws Exception {
        TestProvider p = new TestProvider();

        byte[] data = "WitupKey=WitupValue\n".getBytes("ISO-8859-1");
        p.load(new ByteArrayInputStream(data));
        check("WitupValue".equals(p.getProperty("WitupKey")), "loaded property is visible");

        byte[] overwrite = "WitupKey=Other\n".getBytes("ISO-8859-1");
        p.load(new ByteArrayInputStream(overwrite));
        check("Other".equals(p.getProperty("WitupKey")), "second load overwrites existing key");

        p.load(new ByteArrayInputStream(new byte[0]));
        check("Other".equals(p.getProperty("WitupKey")), "empty stream keeps existing properties");

        boolean thrown = false;
        try {
            p.load(new FailingInputStream());
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "IOException from input stream is propagated");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
