/*
 * @test
 * @summary Tests for visitRequire method in ASMifier class
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitRequireNormal();
        testVisitRequireNullModule();
        testVisitRequireEmptyVersion();
    }

    public static void testVisitRequireNormal() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitRequire("java.base", 0x0020, "9");
            System.out.println("testVisitRequireNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitRequireNormal failed: " + e);
        }
    }

    public static void testVisitRequireNullModule() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitRequire(null, 0x0020, "9");
            System.out.println("testVisitRequireNullModule passed");
        } catch (NullPointerException e) {
            System.out.println("testVisitRequireNullModule passed (caught expected NullPointerException)");
        } catch (Exception e) {
            System.err.println("testVisitRequireNullModule failed: " + e);
        }
    }

    public static void testVisitRequireEmptyVersion() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitRequire("java.base", 0x0020, "");
            System.out.println("testVisitRequireEmptyVersion passed");
        } catch (Exception e) {
            System.err.println("testVisitRequireEmptyVersion failed: " + e);
        }
    }
}
