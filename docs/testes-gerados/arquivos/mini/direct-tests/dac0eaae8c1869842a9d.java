/*
 * @test
 * @summary Test Package.setAllInnerClasses method behavior
 * @run main PackageWitupTest
 */

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PackageWitupTest {

    static class InnerClass {
        final Object thisClass;
        InnerClass(Object thisClass) {
            this.thisClass = thisClass;
        }
    }

    static class Package {
        Collection<InnerClass> allInnerClasses = new ArrayList<>();
        Map<Object, InnerClass> allInnerClassesByThis = new HashMap<>();

        void setAllInnerClasses(Collection<InnerClass> ics) {
            assert(ics != allInnerClasses);
            allInnerClasses.clear();
            allInnerClasses.addAll(ics);

            allInnerClassesByThis = new HashMap<>(allInnerClasses.size());
            for (InnerClass ic : allInnerClasses) {
                Object pic = allInnerClassesByThis.put(ic.thisClass, ic);
                assert(pic == null);  // caller must ensure key uniqueness!
            }
        }
    }

    public static void main(String[] args) {
        Package pkg = new Package();

        // Prepare distinct InnerClass instances with unique keys
        InnerClass ic1 = new InnerClass("class1");
        InnerClass ic2 = new InnerClass("class2");

        List<InnerClass> list = new ArrayList<>();
        list.add(ic1);
        list.add(ic2);

        // Call setAllInnerClasses with a different collection instance
        pkg.setAllInnerClasses(list);

        // Verify allInnerClasses contains the elements
        if (!pkg.allInnerClasses.contains(ic1) || !pkg.allInnerClasses.contains(ic2)) {
            throw new RuntimeException("allInnerClasses does not contain expected InnerClass instances");
        }

        // Verify allInnerClassesByThis maps keys correctly
        if (pkg.allInnerClassesByThis.size() != 2) {
            throw new RuntimeException("allInnerClassesByThis size incorrect");
        }
        if (pkg.allInnerClassesByThis.get("class1") != ic1) {
            throw new RuntimeException("allInnerClassesByThis mapping for class1 incorrect");
        }
        if (pkg.allInnerClassesByThis.get("class2") != ic2) {
            throw new RuntimeException("allInnerClassesByThis mapping for class2 incorrect");
        }

        // Test that passing the same collection instance as allInnerClasses triggers assertion
        boolean assertionThrown = false;
        try {
            pkg.setAllInnerClasses(pkg.allInnerClasses);
        } catch (AssertionError e) {
            assertionThrown = true;
        }

        if (!assertionThrown) {
            throw new RuntimeException("Expected assertion error when passing same collection instance");
        }
    }
}
