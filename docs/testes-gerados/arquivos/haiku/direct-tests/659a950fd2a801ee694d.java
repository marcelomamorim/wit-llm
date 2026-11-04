/*
 * @test
 * @summary Covers the private Provider.Service(Provider) constructor via reflection and checks basic public Provider behavior
 * @modules java.base/java.security
 * @run main/othervm --add-opens java.base/java.security=ALL-UNNAMED ProviderWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProv", "1.0", "test info");
        }
    }

    public static void main(String[] args) throws Exception {
        TestProvider p = new TestProvider();

        if (!"TestProv".equals(p.getName())) {
            throw new RuntimeException("Unexpected name: " + p.getName());
        }
        if (!"1.0".equals(p.getVersionStr())) {
            throw new RuntimeException("Unexpected version: " + p.getVersionStr());
        }
        if (!"test info".equals(p.getInfo())) {
            throw new RuntimeException("Unexpected info: " + p.getInfo());
        }
        if (p.getService("MessageDigest", "SHA-256") != null) {
            throw new RuntimeException("Expected no service registered");
        }

        Class<?> svcClass = Class.forName("java.security.Provider$Service");
        Constructor<?> ctor = svcClass.getDeclaredConstructor(Provider.class);
        ctor.setAccessible(true);

        Object svc;
        try {
            svc = ctor.newInstance(p);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Service(Provider) threw", e.getCause());
        }

        if (svc == null) {
            throw new RuntimeException("Service(Provider) returned null");
        }
        if (!svcClass.isInstance(svc)) {
            throw new RuntimeException("Created object is not a Provider.Service");
        }
    }
}
