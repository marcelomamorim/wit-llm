/*
 * @test
 * @summary Verifies the public signature of HttpsURLConnectionOldImpl.setIfModifiedSince(long)
 * @modules java.base/com.sun.net.ssl.internal.www.protocol.https
 * @run main HttpsURLConnectionOldImplWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl");
        Method m = c.getMethod("setIfModifiedSince", long.class);
        int mods = m.getModifiers();
        if (!Modifier.isPublic(mods)) {
            throw new RuntimeException("setIfModifiedSince(long) must be public");
        }
        if (Modifier.isStatic(mods)) {
            throw new RuntimeException("setIfModifiedSince(long) must not be static");
        }
        if (m.getReturnType() != void.class) {
            throw new RuntimeException("setIfModifiedSince(long) must return void");
        }
    }
}
