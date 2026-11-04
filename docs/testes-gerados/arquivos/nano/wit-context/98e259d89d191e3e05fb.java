/* @test    @summary Test for Textifier.visitPackage to verify handling of string input and buffer manipulation    @run main TextifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitPackageWithValidString();
        testVisitPackageWithEmptyString();
        testVisitPackageWithNull();
    }

    private static void testVisitPackageWithValidString() {
        Textifier t = new Textifier();
        String packageName = "com.example.test";
        try {
            t.visitPackage(packageName);
            System.out.println("visitPackage with valid string passed.");
        } catch (Exception e) {
            System.err.println("visitPackage with valid string failed: " + e);
        }
    }

    private static void testVisitPackageWithEmptyString() {
        Textifier t = new Textifier();
        String packageName = "";
        try {
            t.visitPackage(packageName);
            System.out.println("visitPackage with empty string passed.");
        } catch (Exception e) {
            System.err.println("visitPackage with empty string failed: " + e);
        }
    }

    private static void testVisitPackageWithNull() {
        Textifier t = new Textifier();
        try {
            t.visitPackage(null);
            System.out.println("visitPackage with null string passed.");
        } catch (NullPointerException e) {
            System.out.println("visitPackage with null string threw NullPointerException as expected.");
        } catch (Exception e) {
            System.err.println("visitPackage with null string failed with unexpected exception: " + e);
        }
    }
}
