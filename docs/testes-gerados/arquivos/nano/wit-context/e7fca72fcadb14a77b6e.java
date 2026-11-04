/* @test    @summary Test for ClassVisitor constructor with valid and invalid API values    @run main ClassVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ClassVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApiBelow();
        testInvalidApiAbove();
    }

    static void testValidApi() {
        try {
            ClassVisitor cv = new ClassVisitor(4, null);
            System.out.println("testValidApi passed");
        } catch (Exception e) {
            System.err.println("testValidApi failed: " + e);
        }
    }

    static void testInvalidApiBelow() {
        try {
            ClassVisitor cv = new ClassVisitor(3, null);
            System.err.println("testInvalidApiBelow failed: exception expected but not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidApiBelow passed");
        } catch (Exception e) {
            System.err.println("testInvalidApiBelow failed: wrong exception: " + e);
        }
    }

    static void testInvalidApiAbove() {
        try {
            ClassVisitor cv = new ClassVisitor(7, null);
            System.err.println("testInvalidApiAbove failed: exception expected but not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidApiAbove passed");
        } catch (Exception e) {
            System.err.println("testInvalidApiAbove failed: wrong exception: " + e);
        }
    }
}
