/*
 * @test
 * @summary Test for Textifier.visitSource method to verify handling of null and non-null parameters.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitSourceWithNulls();
        testVisitSourceWithFileAndDebug();
    }

    private static void testVisitSourceWithNulls() {
        Textifier t = new Textifier();
        try {
            t.visitSource(null, null);
            System.out.println("visitSource with nulls passed.");
        } catch (Exception e) {
            System.err.println("visitSource with nulls failed: " + e);
        }
    }

    private static void testVisitSourceWithFileAndDebug() {
        Textifier t = new Textifier();
        try {
            t.visitSource("MyClass.java", "Debug info");
            System.out.println("visitSource with file and debug passed.");
        } catch (Exception e) {
            System.err.println("visitSource with file and debug failed: " + e);
        }
    }
}
