/*
 * @test
 * @summary Reflective checks of Package.setAllInnerClasses: empty collection accepted, null collection throws NPE wrapped in InvocationTargetException
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED PackageWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PackageWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> pkgClass = Class.forName("com.sun.java.util.jar.pack.Package");
        Constructor<?> ctor = pkgClass.getConstructor();
        ctor.setAccessible(true);
        Object pkg = ctor.newInstance();

        Method m = pkgClass.getDeclaredMethod("setAllInnerClasses", Collection.class);
        m.setAccessible(true);

        m.invoke(pkg, Collections.emptyList());
        m.invoke(pkg, new ArrayList<Object>());

        try {
            m.invoke(pkg, (Object) null);
            throw new AssertionError("expected InvocationTargetException for null collection");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
        }

        m.invoke(pkg, Collections.emptyList());
    }
}
