/*
 * @test
 * @summary Test Package.setAllInnerClasses behavior
 * @run main PackageWitupTest
 */

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PackageWitupTest {

    static class InnerClass {
        final Object thisClass;
        InnerClass(Object thisClass) {
            this.thisClass = thisClass;
        }
    }

    static class Package {
        private final Collection<InnerClass> allInnerClasses = new ArrayList<>();
        private Map<Object, InnerClass> allInnerClassesByThis = new HashMap<>();

        void setAllInnerClasses(Collection<InnerClass> ics) {
            if (ics == allInnerClasses) {
                throw new IllegalArgumentException("Input collection must not be the internal allInnerClasses");
            }
            allInnerClasses.clear();
            allInnerClasses.addAll(ics);

            allInnerClassesByThis = new HashMap<>(allInnerClasses.size());
            for (InnerClass ic : allInnerClasses) {
                Object pic = allInnerClassesByThis.put(ic.thisClass, ic);
                if (pic != null) {
                    throw new IllegalArgumentException("Duplicate thisClass key detected");
                }
            }
        }

        // Expose for test verification
        Map<Object, InnerClass> getAllInnerClassesByThis() {
            return allInnerClassesByThis;
        }

        Collection<InnerClass> getAllInnerClasses() {
            return allInnerClasses;
        }
    }

    public static void main(String[] args) {
        Package pkg = new Package();

        // Test 1: Normal usage with unique keys
        ArrayList<InnerClass> list1 = new ArrayList<>();
        list1.add(new InnerClass("A"));
        list1.add(new InnerClass("B"));
        pkg.setAllInnerClasses(list1);
        if (pkg.getAllInnerClasses().size() != 2) {
            throw new RuntimeException("Test 1 failed: size mismatch");
        }
        if (!pkg.getAllInnerClassesByThis().containsKey("A") || !pkg.getAllInnerClassesByThis().containsKey("B")) {
            throw new RuntimeException("Test 1 failed: keys missing");
        }

        // Test 2: Empty collection
        ArrayList<InnerClass> list2 = new ArrayList<>();
        pkg.setAllInnerClasses(list2);
        if (pkg.getAllInnerClasses().size() != 0) {
            throw new RuntimeException("Test 2 failed: expected empty collection");
        }

        // Test 3: Duplicate keys should throw IllegalArgumentException
        ArrayList<InnerClass> list3 = new ArrayList<>();
        list3.add(new InnerClass("X"));
        list3.add(new InnerClass("X"));
        boolean threw = false;
        try {
            pkg.setAllInnerClasses(list3);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Test 3 failed: expected IllegalArgumentException for duplicate keys");
        }

        // Test 4: Passing internal allInnerClasses collection should throw IllegalArgumentException
        threw = false;
        try {
            pkg.setAllInnerClasses(pkg.getAllInnerClasses());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Test 4 failed: expected IllegalArgumentException for passing internal collection");
        }

        System.out.println("All tests passed.");
    }
}
