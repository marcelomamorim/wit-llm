/* @test    @summary Test AnnotationVisitor constructor with valid parameters    @run main AnnotationVisitorWitupTest */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    static void testValidConstructor() {
        AnnotationVisitor av = new AnnotationVisitor(4, null);
        assert av != null : "AnnotationVisitor should be instantiated with valid api";
        assert av.api == 4 : "API version should be set to 4";
        assert av.av == null : "Delegated visitor should be null";
    }

    static void testInvalidConstructor() {
        boolean exceptionThrown = false;
        try {
            new AnnotationVisitor(3, null); // Invalid API, less than ASM4
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for invalid api less than ASM4";

        exceptionThrown = false;
        try {
            new AnnotationVisitor(7, null); // Invalid API, greater than ASM6
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for invalid api greater than ASM6";
    }
}
