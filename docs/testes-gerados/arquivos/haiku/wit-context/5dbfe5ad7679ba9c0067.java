/*
 * @test
 * @summary Verify the public signature of HttpsURLConnectionOldImpl.setAllowUserInteraction(boolean)
 * @modules java.base/com.sun.net.ssl.internal.www.protocol.https
 * @run main HttpsURLConnectionOldImplWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl");
        Method m = c.getMethod("setAllowUserInteraction", boolean.class);
        if (!Modifier.isPublic(m.getModifiers())) {
            throw new AssertionError("setAllowUserInteraction must be public");
        }
        if (m.getReturnType() != void.class) {
            throw new AssertionError("setAllowUserInteraction must return void");
        }
    }
}
