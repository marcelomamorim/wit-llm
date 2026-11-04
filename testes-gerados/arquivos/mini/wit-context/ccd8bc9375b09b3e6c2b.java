/*
 * @test
 * @summary Test ProxyGenerator.getClass(String) for null input and normal behavior
 * @run main ProxyGeneratorWitupTest
 */

import java.lang.reflect.ProxyGenerator;

public class ProxyGeneratorWitupTest {

    public static void main(String[] args) {
        ProxyGeneratorWitupTest test = new ProxyGeneratorWitupTest();
        test.testGetClassWithNullName();
        test.testGetClassWithValidName();
    }

    // Test that getClass throws NullPointerException when name is null
    public void testGetClassWithNullName() {
        ProxyGenerator proxyGen = new ProxyGenerator();
        try {
            proxyGen.getClass(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test getClass with a valid string input returns a non-negative short
    public void testGetClassWithValidName() {
        ProxyGenerator proxyGen = new ProxyGenerator();
        String validName = "java/lang/Object";
        short result = proxyGen.getClass(validName);
        if (result < 0) {
            throw new RuntimeException("getClass returned negative index for valid name");
        }
    }
}
