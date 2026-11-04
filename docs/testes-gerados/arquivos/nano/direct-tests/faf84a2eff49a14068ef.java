/* @test    @summary Test constructor of AnnotationVisitor for JDK 11+28    @run main AnnotationVisitorWitupTest */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidApi();
        testConstructorWithInvalidApi();
    }

    static void testConstructorWithValidApi() {
        int[] validApis = {1, 2, 3}; // Example valid opcodes, assuming ASM4, ASM5, ASM6
        for (int api : validApis) {
            AnnotationVisitor av = new AnnotationVisitor(api);
            assert av != null : "AnnotationVisitor instance should not be null for api " + api;
        }
    }

    static void testConstructorWithInvalidApi() {
        int invalidApi = -1;
        try {
            AnnotationVisitor av = new AnnotationVisitor(invalidApi);
            assert av != null : "AnnotationVisitor instance should be created even with invalid api " + invalidApi;
        } catch (Exception e) {
            // If constructor throws, test passes if exception is expected
            // But based on source, constructor does not throw, so this is just a safeguard
            assert false : "Constructor threw exception with invalid api: " + e;
        }
    }
}
