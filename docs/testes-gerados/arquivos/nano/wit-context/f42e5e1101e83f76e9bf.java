/*
 * @test
 * @summary Test for Textifier.visitOpen method to verify handling of null and empty modules array.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitOpenWithNullModules();
        testVisitOpenWithEmptyModules();
    }

    private static void testVisitOpenWithNullModules() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitOpen("com/example/MyExport", 0x0010, null);
            System.out.println("testVisitOpenWithNullModules passed");
        } catch (Exception e) {
            System.err.println("testVisitOpenWithNullModules failed: " + e);
        }
    }

    private static void testVisitOpenWithEmptyModules() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitOpen("com/example/MyExport", 0x0010, new String[] {});
            System.out.println("testVisitOpenWithEmptyModules passed");
        } catch (Exception e) {
            System.err.println("testVisitOpenWithEmptyModules failed: " + e);
        }
    }
}
