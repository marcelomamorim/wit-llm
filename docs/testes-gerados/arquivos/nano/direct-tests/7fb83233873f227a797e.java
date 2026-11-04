/*
 * @test
 * @summary Test the default constructor of SSLSocketFactoryImpl.
 * @run main SSLSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLSocketFactoryImpl;
import java.lang.reflect.Constructor;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() throws Exception {
        Constructor<SSLSocketFactoryImpl> constructor = SSLSocketFactoryImpl.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SSLSocketFactoryImpl instance = constructor.newInstance();
        if (instance == null) {
            throw new AssertionError("Instance should not be null");
        }
    }
}
