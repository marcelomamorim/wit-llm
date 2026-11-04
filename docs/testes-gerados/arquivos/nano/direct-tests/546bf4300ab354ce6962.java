/* @test    @summary Test ModuleVisitor constructor with valid ASM6 API    @run main ModuleVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class ModuleVisitorWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidApiThrows();
    }

    static void testValidConstructor() {
        ModuleVisitor mv = new ModuleVisitor(6, null);
        assert mv != null : "ModuleVisitor instance should not be null";
    }

    static void testInvalidApiThrows() {
        boolean thrown = false;
        try {
            new ModuleVisitor(5, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert thrown : "Constructor with invalid API should throw IllegalArgumentException";
    }
}
