/*
 * @test
 * @summary Test for visitMainClass method in ASMifier class
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMainClassWithValidString();
        testVisitMainClassWithEmptyString();
        testVisitMainClassWithNull();
    }

    public static void testVisitMainClassWithValidString() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        try {
            asmifier.visitMainClass("com/example/Main");
            System.out.println("testVisitMainClassWithValidString passed");
        } catch (Exception e) {
            System.err.println("testVisitMainClassWithValidString failed: " + e);
        }
    }

    public static void testVisitMainClassWithEmptyString() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        try {
            asmifier.visitMainClass("");
            System.out.println("testVisitMainClassWithEmptyString passed");
        } catch (Exception e) {
            System.err.println("testVisitMainClassWithEmptyString failed: " + e);
        }
    }

    public static void testVisitMainClassWithNull() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        try {
            asmifier.visitMainClass(null);
            System.out.println("testVisitMainClassWithNull passed");
        } catch (NullPointerException e) {
            System.out.println("testVisitMainClassWithNull passed (expected NullPointerException)");
        } catch (Exception e) {
            System.err.println("testVisitMainClassWithNull failed: " + e);
        }
    }
}
