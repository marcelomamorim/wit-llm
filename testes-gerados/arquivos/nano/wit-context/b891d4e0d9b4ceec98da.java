/* @test    @summary Tests the load(InputStream) method of java.security.Provider    @run main ProviderWitupTest */

import java.io.*;
import java.security.*;

public class ProviderWitupTest {

    public static void main(String[] args) throws Exception {
        testLoadWithValidInputStream();
        testLoadWithNullInputStream();
    }

    private static void testLoadWithValidInputStream() throws Exception {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized void load(InputStream inStream) throws IOException {
                super.load(inStream);
            }
        };
        String propertiesContent = "key1=value1\nkey2=value2";
        InputStream inputStream = new ByteArrayInputStream(propertiesContent.getBytes());
        provider.load(inputStream);
        // No exception means pass
    }

    private static void testLoadWithNullInputStream() {
        Provider provider = new Provider("TestProviderNull", 1.0, "Test Info") {
            @Override
            public synchronized void load(InputStream inStream) throws IOException {
                super.load(inStream);
            }
        };
        try {
            provider.load(null);
            // Expecting NullPointerException or IOException, depending on implementation
        } catch (NullPointerException | IOException e) {
            // Expected exception, test passes
        }
    }
}
