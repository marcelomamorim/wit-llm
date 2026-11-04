/*
 * @test
 * @summary Verify Textifier.visitPackage appends a package comment line to the printed text
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testSinglePackage();
        testMultiplePackagesKeepOrder();
        testNullPackageName();
    }

    static void testSinglePackage() {
        Textifier t = new Textifier();
        t.visitPackage("java/lang");
        String out = render(t);
        check("  // package java/lang\n".equals(out), "single: " + out);
    }

    static void testMultiplePackagesKeepOrder() {
        Textifier t = new Textifier();
        t.visitPackage("a/b");
        t.visitPackage("c/d");
        String out = render(t);
        check("  // package a/b\n  // package c/d\n".equals(out), "multi: " + out);
    }

    static void testNullPackageName() {
        Textifier t = new Textifier();
        t.visitPackage(null);
        String out = render(t);
        check("  // package null\n".equals(out), "null: " + out);
    }

    static String render(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Unexpected output: " + msg);
        }
    }
}
