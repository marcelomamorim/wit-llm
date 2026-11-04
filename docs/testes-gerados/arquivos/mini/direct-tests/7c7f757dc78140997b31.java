/*
 * @test
 * @summary Test System.addReadsAllUnnamed(Module) method
 * @run main SystemWitupTest
 */

import java.lang.Module;

public class SystemWitupTest {
    public static void main(String[] args) {
        SystemWitupTest test = new SystemWitupTest();
        test.testAddReadsAllUnnamed();
    }

    public void testAddReadsAllUnnamed() {
        // Create a dummy Module subclass to test implAddReadsAllUnnamed call
        Module testModule = new Module(null, null, null, null, null, null, null, null) {
            boolean called = false;
            @Override
            public void implAddReadsAllUnnamed() {
                called = true;
            }
        };

        // Since Module is final in JDK 11, we cannot subclass it directly.
        // Instead, test that calling addReadsAllUnnamed with a real Module does not throw.
        // We test with the boot module (unnamed module) which is accessible.

        Module m = System.class.getModule();
        // Call the method under test
        new System().addReadsAllUnnamed(m);

        // No exception means pass
    }
}
