/*
 * @test
 * @summary Test ProxyGenerator.getClass(String) method behavior
 * @run main ProxyGeneratorWitupTest
 */

import java.lang.reflect.ProxyGenerator;

public class ProxyGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        ProxyGenerator pg = new ProxyGenerator();

        // Test with a simple valid class name
        short result1 = pg.getClass("java/lang/Object");
        if (result1 == 0) {
            throw new RuntimeException("getClass returned 0 for valid class name");
        }

        // Test with empty string - should still return a short (likely 0 or valid)
        short result2 = pg.getClass("");
        if (result2 < 0) {
            throw new RuntimeException("getClass returned negative for empty string");
        }

        // Test with null - expect NullPointerException
        try {
            pg.getClass(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }

        System.out.println("ProxyGenerator.getClass tests passed");
    }
}
