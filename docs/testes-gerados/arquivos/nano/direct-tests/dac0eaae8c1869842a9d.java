/* @test    @summary Test setAllInnerClasses method for Package class    @run main PackageWitupTest */

import com.sun.java.util.jar.pack.Package;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;

public class PackageWitupTest {

    public static void main(String[] args) {
        testSetAllInnerClasses();
    }

    public static void testSetAllInnerClasses() {
        Package pkg = new Package();
        Collection<Package.InnerClass> innerClasses = new ArrayList<>();
        // Add a dummy InnerClass instance
        Package.InnerClass inner = new Package.InnerClass() {
            @Override
            public Object thisClass = new Object();
        };
        innerClasses.add(inner);
        // Call the method under test
        pkg.setAllInnerClasses(innerClasses);
        // Verify that allInnerClasses collection is updated
        if (pkg.allInnerClasses.size() != 1) {
            throw new AssertionError("allInnerClasses size mismatch");
        }
        // Verify that the map contains the key
        Object key = inner.thisClass;
        if (!pkg.allInnerClassesByThis.containsKey(key)) {
            throw new AssertionError("allInnerClassesByThis does not contain key");
        }
        // Verify that the value matches
        if (pkg.allInnerClassesByThis.get(key) != inner) {
            throw new AssertionError("Value mismatch in allInnerClassesByThis");
        }
    }
}
