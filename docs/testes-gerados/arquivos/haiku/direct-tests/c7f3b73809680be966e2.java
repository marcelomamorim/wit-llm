/*
 * @test
 * @summary Verify the textual output produced by Textifier.visitMainClass
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testSingleMainClass();
        testMultipleMainClasses();
        testEmptyMainClass();
        testNullMainClass();
    }

    static void testSingleMainClass() {
        Textifier t = new Textifier();
        t.visitMainClass("java/lang/Object");
        check(dump(t), "  // main class java/lang/Object\n");
    }

    static void testMultipleMainClasses() {
        Textifier t = new Textifier();
        t.visitMainClass("A");
        t.visitMainClass("B");
        check(dump(t), "  // main class A\n  // main class B\n");
    }

    static void testEmptyMainClass() {
        Textifier t = new Textifier();
        t.visitMainClass("");
        check(dump(t), "  // main class \n");
    }

    static void testNullMainClass() {
        Textifier t = new Textifier();
        String nul = null;
        t.visitMainClass(nul);
        check(dump(t), "  // main class null\n");
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }
}
