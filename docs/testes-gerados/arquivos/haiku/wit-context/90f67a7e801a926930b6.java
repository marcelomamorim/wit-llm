/*
 * @test
 * @summary Verify that Module.addReads, the public API reaching the System.addReads JavaLangAccess path, rejects null and returns this module for valid input
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        testNullModuleThrowsNPE();
        testValidModuleReturnsSelf();
        System.out.println("PASSED");
    }

    static void testNullModuleThrowsNPE() {
        Module base = String.class.getModule();
        boolean thrown = false;
        try {
            base.addReads(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException when addReads receives null");
        }
    }

    static void testValidModuleReturnsSelf() {
        Module base = String.class.getModule();
        Module other = SystemWitupTest.class.getModule();
        Module result = base.addReads(other);
        if (result != base) {
            throw new RuntimeException("addReads should return the receiver module");
        }
    }
}
