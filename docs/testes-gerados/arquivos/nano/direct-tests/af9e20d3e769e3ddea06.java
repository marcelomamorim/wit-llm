/*
 * @test
 * @summary Test the constructor MethodVisitor(int api) for correct instantiation.
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodVisitorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidApi();
        testConstructorWithZeroApi();
        testConstructorWithNegativeApi();
    }

    static void testConstructorWithValidApi() {
        int api = 11;
        MethodVisitor mv = new MethodVisitor(api);
        if (mv == null) {
            throw new AssertionError("MethodVisitor instance should not be null for valid api");
        }
    }

    static void testConstructorWithZeroApi() {
        int api = 0;
        MethodVisitor mv = new MethodVisitor(api);
        if (mv == null) {
            throw new AssertionError("MethodVisitor instance should not be null for api=0");
        }
    }

    static void testConstructorWithNegativeApi() {
        int api = -1;
        MethodVisitor mv = new MethodVisitor(api);
        if (mv == null) {
            throw new AssertionError("MethodVisitor instance should not be null for negative api");
        }
    }
}
