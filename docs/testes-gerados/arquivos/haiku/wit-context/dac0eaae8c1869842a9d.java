/*
 * @test
 * @summary Smoke test for the package-private Package.setAllInnerClasses method with empty inputs
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED PackageWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PackageWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.Package");
        Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object pkg = ctor.newInstance();
        Method m = cls.getDeclaredMethod("setAllInnerClasses", Collection.class);
        m.setAccessible(true);
        try {
            m.invoke(pkg, Collections.emptyList());
            m.invoke(pkg, new ArrayList<Object>());
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw new AssertionError("setAllInnerClasses threw for empty input", e.getCause());
        }
    }
}
