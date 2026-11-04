/* @test    @summary Test constructor of ClassVisitor with valid parameters    @run main ClassVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidApiBelowRange();
        testInvalidApiAboveRange();
    }

    static void testValidConstructor() {
        ClassVisitor cv = new ClassVisitor(4, null) {
            // Empty anonymous subclass for instantiation
        };
        assert cv != null : "Constructor should create a non-null object";
        assert cv.api == 4 : "API version should be set to 4";
        assert cv.cv == null : "Delegate should be null";
    }

    static void testInvalidApiBelowRange() {
        boolean exceptionThrown = false;
        try {
            new ClassVisitor(3, null) {
                // Empty anonymous subclass
            };
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for API below ASM4";
    }

    static void testInvalidApiAboveRange() {
        boolean exceptionThrown = false;
        try {
            new ClassVisitor(7, null) {
                // Empty anonymous subclass
            };
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for API above ASM6";
    }
}
