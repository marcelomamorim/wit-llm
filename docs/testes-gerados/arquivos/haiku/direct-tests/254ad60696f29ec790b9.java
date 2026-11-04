/*
 * @test
 * @summary Verifies the public signature of HttpsURLConnectionOldImpl.setIfModifiedSince(long) via reflection
 * @run main HttpsURLConnectionOldImplWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl");
        Method m = c.getMethod("setIfModifiedSince", long.class);

        if (!Modifier.isPublic(m.getModifiers())) {
            throw new RuntimeException("setIfModifiedSince must be public");
        }
        if (Modifier.isStatic(m.getModifiers())) {
            throw new RuntimeException("setIfModifiedSince must not be static");
        }
        if (m.getReturnType() != void.class) {
            throw new RuntimeException("setIfModifiedSince must return void");
        }
        if (m.getDeclaringClass() != c) {
            throw new RuntimeException("setIfModifiedSince must be declared by HttpsURLConnectionOldImpl");
        }
    }
}
