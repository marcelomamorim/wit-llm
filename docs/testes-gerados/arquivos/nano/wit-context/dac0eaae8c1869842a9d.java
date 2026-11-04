/*
 * @test
 * @summary Test for Package.setAllInnerClasses to verify exception paths and behavior.
 * @run main PackageWitupTest
 */

import com.sun.java.util.jar.pack.Package;
import com.sun.java.util.jar.pack.Package.InnerClass;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PackageWitupTest {

    public static void main(String[] args) {
        testSetAllInnerClassesWithEmptyCollection();
        testSetAllInnerClassesWithLargeCollection();
        testSetAllInnerClassesWithNullCollection();
    }

    static void testSetAllInnerClassesWithEmptyCollection() {
        Package pkg = new Package();
        Collection<InnerClass> empty = new HashSet<>();
        try {
            pkg.setAllInnerClasses(empty);
            System.out.println("Empty collection test passed.");
        } catch (IllegalArgumentException e) {
            System.err.println("Empty collection test failed: " + e);
        }
    }

    static void testSetAllInnerClassesWithLargeCollection() {
        Package pkg = new Package();
        InnerClass ic1 = new InnerClass();
        InnerClass ic2 = new InnerClass();
        Collection<InnerClass> large = Arrays.asList(ic1, ic2);
        try {
            pkg.setAllInnerClasses(large);
            System.out.println("Large collection test passed.");
        } catch (IllegalArgumentException e) {
            System.err.println("Large collection test failed: " + e);
        }
    }

    static void testSetAllInnerClassesWithNullCollection() {
        Package pkg = new Package();
        try {
            pkg.setAllInnerClasses(null);
            System.out.println("Null collection test passed.");
        } catch (NullPointerException e) {
            System.err.println("Null collection test failed: " + e);
        } catch (IllegalArgumentException e) {
            System.err.println("Null collection test failed: " + e);
        }
    }
}
