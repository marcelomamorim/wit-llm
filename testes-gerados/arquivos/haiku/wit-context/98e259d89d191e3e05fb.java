/*
 * @test
 * @summary Checks the textual output produced by Textifier.visitPackage
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testSinglePackage();
        testSequentialPackagesDoNotAccumulate();
        testNullPackageName();
    }

    static void testSinglePackage() {
        Textifier t = new Textifier();
        t.visitPackage("java/lang");
        check(render(t), "  // package java/lang\n");
    }

    static void testSequentialPackagesDoNotAccumulate() {
        Textifier t = new Textifier();
        t.visitPackage("a/b");
        t.visitPackage("c/d");
        check(render(t), "  // package a/b\n  // package c/d\n");
    }

    static void testNullPackageName() {
        Textifier t = new Textifier();
        t.visitPackage(null);
        check(render(t), "  // package null\n");
    }

    static String render(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
