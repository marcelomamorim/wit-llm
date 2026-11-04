/* @test    @summary Test setDoOutput method of HttpsURLConnectionImpl for correct delegation and behavior    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDoOutputTrue();
        testSetDoOutputFalse();
    }

    private static void testSetDoOutputTrue() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        conn.setDoOutput(true);
        boolean result = getDelegateDoOutput(conn);
        if (!result) {
            throw new AssertionError("Expected delegate to have doOutput=true");
        }
    }

    private static void testSetDoOutputFalse() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        conn.setDoOutput(false);
        boolean result = getDelegateDoOutput(conn);
        if (result) {
            throw new AssertionError("Expected delegate to have doOutput=false");
        }
    }

    private static boolean getDelegateDoOutput(HttpsURLConnectionImpl conn) {
        try {
            java.lang.reflect.Field delegateField = HttpsURLConnectionImpl.class.getDeclaredField("delegate");
            delegateField.setAccessible(true);
            Object delegateObj = delegateField.get(conn);
            java.lang.reflect.Method getDoOutputMethod = delegateObj.getClass().getMethod("getDoOutput");
            Object result = getDoOutputMethod.invoke(delegateObj);
            return (Boolean) result;
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Reflection error accessing delegate or its method", e);
        }
    }
}
