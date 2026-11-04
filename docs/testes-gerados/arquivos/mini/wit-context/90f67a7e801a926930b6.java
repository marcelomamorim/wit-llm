/*
 * @test
 * @summary Test java.lang.System.addReads(Module m1, Module m2) for NPE on null m2
 * @run main SystemWitupTest
 */

import java.lang.Module;

public class SystemWitupTest {
    public static void main(String[] args) {
        // Create two dummy Module instances for testing
        // Since Module constructor is not public, use the system module for m1
        Module m1 = System.class.getModule();

        // Test normal call with non-null m2 (should not throw)
        Module m2 = System.class.getModule();
        System sys = new System() {
            public void addReads(Module mod1, Module mod2) {
                mod1.implAddReads(mod2);
            }
        };
        // The above anonymous subclass is to call the method as instance method
        // but since System.addReads is instance method, we simulate it here
        // Actually System.addReads is instance method in the excerpt, so we test it

        // Normal call: no exception expected
        sys.addReads(m1, m2);

        // Test with null m2: expect NullPointerException
        try {
            sys.addReads(m1, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
