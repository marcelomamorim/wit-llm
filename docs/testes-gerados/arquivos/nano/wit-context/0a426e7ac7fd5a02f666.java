/* @test    @summary Test for ModuleVisitor constructor with valid API value    @run main ModuleVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import static java.lang.System.out;

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApi();
    }

    private static void testValidApi() {
        try {
            ModuleVisitor mv = new ModuleVisitor(6); // ASM6
            out.println("testValidApi passed");
        } catch (Exception e) {
            out.println("testValidApi failed: " + e);
        }
    }

    private static void testInvalidApi() {
        try {
            ModuleVisitor mv = new ModuleVisitor(5); // Invalid, should throw
            out.println("testInvalidApi failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            out.println("testInvalidApi passed: caught expected exception");
        } catch (Exception e) {
            out.println("testInvalidApi failed: caught unexpected exception: " + e);
        }
    }
}
