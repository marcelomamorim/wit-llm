/* @test    @summary Test constructor of ModuleVisitor with valid API value    @run main ModuleVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidApi();
        testConstructorWithInvalidApi();
    }

    public static void testConstructorWithValidApi() {
        int validApi = 6; // ASM6 as per documentation
        ModuleVisitor mv = new ModuleVisitor(validApi);
        if (mv == null) {
            throw new AssertionError("ModuleVisitor instance should not be null for valid API");
        }
    }

    public static void testConstructorWithInvalidApi() {
        int invalidApi = -1; // invalid API value
        try {
            ModuleVisitor mv = new ModuleVisitor(invalidApi);
            if (mv == null) {
                throw new AssertionError("ModuleVisitor instance should not be null even for invalid API");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor threw an exception for invalid API: " + e);
        }
    }
}
