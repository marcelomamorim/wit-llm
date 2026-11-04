/*
 * @test
 * @summary Verifies the public signature of NegotiateAuthentication.getHeaderValue(URL, String) without invoking it
 * @modules java.base/sun.net.www.protocol.http
 * @run main NegotiateAuthenticationWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

public class NegotiateAuthenticationWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.net.www.protocol.http.NegotiateAuthentication");
        Method m = c.getDeclaredMethod("getHeaderValue", URL.class, String.class);

        if (!Modifier.isPublic(m.getModifiers())) {
            throw new AssertionError("getHeaderValue must be public");
        }
        if (m.getReturnType() != String.class) {
            throw new AssertionError("getHeaderValue must return String");
        }
    }
}
