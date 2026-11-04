/*
 * @test
 * @summary Verifies that ClassLoader.getSystemResource delegates to the system class loader's getResource
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        String missing = "witup/no/such/resource_4f1a9c.txt";
        URL absent = ClassLoader.getSystemResource(missing);
        if (absent != null) {
            throw new RuntimeException("Expected null for missing resource, got: " + absent);
        }
        if (ClassLoader.getSystemClassLoader().getResource(missing) != null) {
            throw new RuntimeException("System class loader disagrees on missing resource");
        }

        String existing = "java/lang/Object.class";
        URL viaStatic = ClassLoader.getSystemResource(existing);
        URL viaLoader = ClassLoader.getSystemClassLoader().getResource(existing);
        if (viaStatic == null) {
            throw new RuntimeException("Expected non-null URL for " + existing);
        }
        if (viaLoader == null || !viaStatic.toString().equals(viaLoader.toString())) {
            throw new RuntimeException("getSystemResource differs from system class loader: "
                    + viaStatic + " vs " + viaLoader);
        }
    }
}
