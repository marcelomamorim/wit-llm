/*
 * @test
 * @summary Test for visitMethodEnd method in ASMifier class
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMethodEnd();
    }

    public static void testVisitMethodEnd() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMethodEnd();
            System.out.println("visitMethodEnd executed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
