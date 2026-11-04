/* @test    @summary Test constructor of ClassVisitor with valid API value    @run main ClassVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidApi();
        testConstructorWithInvalidApi();
    }

    static void testConstructorWithValidApi() {
        int validApi = 4; // ASM4
        ClassVisitor cv = new ClassVisitor(validApi);
        assert cv != null : "ClassVisitor instance should not be null";
    }

    static void testConstructorWithInvalidApi() {
        int invalidApi = -1; // invalid API value
        try {
            ClassVisitor cv = new ClassVisitor(invalidApi);
            assert false : "Expected an exception for invalid API";
        } catch (Exception e) {
            assert e != null : "Exception should be thrown for invalid API";
        }
    }
}
