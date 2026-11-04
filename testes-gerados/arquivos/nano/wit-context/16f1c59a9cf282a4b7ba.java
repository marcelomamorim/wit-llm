/*
 * @test
 * @summary Test for ASMifier.visitUse(String)
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitUseWithNull();
        testVisitUseWithEmptyString();
        testVisitUseWithNormalString();
        testVisitUseWithLongString();
    }

    static void testVisitUseWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitUse(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null input test");
            return;
        }
        System.out.println("Failed null input test");
    }

    static void testVisitUseWithEmptyString() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitUse("");
            System.out.println("Passed empty string test");
        } catch (Exception e) {
            System.out.println("Failed empty string test");
        }
    }

    static void testVisitUseWithNormalString() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitUse("TestService");
            System.out.println("Passed normal string test");
        } catch (Exception e) {
            System.out.println("Failed normal string test");
        }
    }

    static void testVisitUseWithLongString() {
        ASMifier asmifier = new ASMifier();
        String longString = new String(new char[1000]).replace("\0", "a");
        try {
            asmifier.visitUse(longString);
            System.out.println("Passed long string test");
        } catch (Exception e) {
            System.out.println("Failed long string test");
        }
    }
}
