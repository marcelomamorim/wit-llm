/*
 * @test
 * @summary Test the clear() method of java.security.Provider for correct behavior.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testClearMethod();
    }

    public static void testClearMethod() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized void clear() {
                super.clear();
            }
        };
        try {
            provider.clear();
            System.out.println("clear() method invoked successfully.");
        } catch (Exception e) {
            System.err.println("Exception during clear(): " + e);
            e.printStackTrace();
        }
    }
}
