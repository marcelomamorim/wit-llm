/*
 * @test
 * @summary Verifies that HttpsURLConnectionOldImpl.setAllowUserInteraction delegates the value so getAllowUserInteraction reflects it
 * @run main/othervm --add-opens java.base/com.sun.net.ssl.internal.www.protocol.https=ALL-UNNAMED HttpsURLConnectionOldImplWitupTest
 */

import java.lang.reflect.Constructor;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        String implName = "com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl";
        String handlerName = "com.sun.net.ssl.internal.www.protocol.https.Handler";
        Class<?> implClass = Class.forName(implName);
        Class<?> handlerClass = Class.forName(handlerName);
        Constructor<?> ctor = implClass.getDeclaredConstructor(URL.class, Proxy.class, handlerClass);
        ctor.setAccessible(true);
        URL url = new URL("https://localhost/");
        URLConnection conn = (URLConnection) ctor.newInstance(url, Proxy.NO_PROXY, null);

        conn.setAllowUserInteraction(true);
        if (!conn.getAllowUserInteraction()) {
            throw new RuntimeException("Expected allowUserInteraction to be true after setting true");
        }

        conn.setAllowUserInteraction(false);
        if (conn.getAllowUserInteraction()) {
            throw new RuntimeException("Expected allowUserInteraction to be false after setting false");
        }

        conn.setAllowUserInteraction(true);
        conn.setAllowUserInteraction(true);
        if (!conn.getAllowUserInteraction()) {
            throw new RuntimeException("Repeated set(true) should keep value true");
        }
    }
}
