/*
 * @test
 * @summary Test for Textifier.visitUse(String use) method, focusing on exception paths.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitUseWithNull();
        testVisitUseWithEmptyString();
        testVisitUseWithValidString();
    }

    private static void testVisitUseWithNull() {
        Textifier t = new Textifier();
        try {
            t.visitUse(null);
            System.out.println("testVisitUseWithNull: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testVisitUseWithNull: Passed - NullPointerException caught");
        } catch (Exception e) {
            System.out.println("testVisitUseWithNull: Failed - Unexpected exception: " + e);
        }
    }

    private static void testVisitUseWithEmptyString() {
        Textifier t = new Textifier();
        try {
            t.visitUse("");
            System.out.println("testVisitUseWithEmptyString: Passed");
        } catch (Exception e) {
            System.out.println("testVisitUseWithEmptyString: Failed - Exception: " + e);
        }
    }

    private static void testVisitUseWithValidString() {
        Textifier t = new Textifier();
        String useStr = "Lcom/example/MyClass;";
        try {
            t.visitUse(useStr);
            System.out.println("testVisitUseWithValidString: Passed");
        } catch (Exception e) {
            System.out.println("testVisitUseWithValidString: Failed - Exception: " + e);
        }
    }
}
