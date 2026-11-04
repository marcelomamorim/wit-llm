/*
 * @test
 * @summary Verifies that Package.addBytes(byte[]) rejects a null array with a NullPointerException
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED PackageWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PackageWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.sun.java.util.jar.pack.Package");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object pkg = ctor.newInstance();

        Method addBytes = c.getMethod("addBytes", byte[].class);
        try {
            addBytes.invoke(pkg, (Object) null);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof NullPointerException)) {
                throw new RuntimeException("Expected NullPointerException but got: " + cause, e);
            }
            return;
        }
        throw new RuntimeException("addBytes(null) did not throw an exception");
    }
}
