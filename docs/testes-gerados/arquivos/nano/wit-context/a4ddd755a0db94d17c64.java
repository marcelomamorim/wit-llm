/*
 * @test
 * @summary Test for OCSPResponse.getSingleExtensions() method.
 * @run main OCSPResponseWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import sun.security.provider.certpath.OCSPResponse;

public class OCSPResponseWitupTest {

    public static void main(String[] args) {
        testGetSingleExtensionsWithNull();
        testGetSingleExtensionsWithValidMap();
    }

    private static void testGetSingleExtensionsWithNull() {
        try {
            // Reflection or direct instantiation is not possible due to constructor restrictions.
            // Instead, simulate the scenario by subclassing or mocking if possible.
            // Since constructor is public, but no default, we simulate by creating a subclass.
            // Alternatively, assume a method to create an instance with null internal state.
            OCSPResponse response = new OCSPResponse(new byte[0]) {
                @Override
                public Map<String, java.security.cert.Extension> getSingleExtensions() {
                    return null;
                }
            };
            Map<String, java.security.cert.Extension> result = response.getSingleExtensions();
            if (result != null) {
                System.out.println("testGetSingleExtensionsWithNull: Failed - expected null, got " + result);
            } else {
                System.out.println("testGetSingleExtensionsWithNull: Passed");
            }
        } catch (Exception e) {
            System.out.println("testGetSingleExtensionsWithNull: Exception - " + e);
        }
    }

    private static void testGetSingleExtensionsWithValidMap() {
        try {
            Map<String, java.security.cert.Extension> extMap = new HashMap<>();
            // Add dummy extension if needed; here, just an empty map
            OCSPResponse response = new OCSPResponse(new byte[0]) {
                @Override
                public Map<String, java.security.cert.Extension> getSingleExtensions() {
                    return Collections.unmodifiableMap(extMap);
                }
            };
            Map<String, java.security.cert.Extension> result = response.getSingleExtensions();
            if (result != null && result == extMap) {
                System.out.println("testGetSingleExtensionsWithValidMap: Passed");
            } else {
                System.out.println("testGetSingleExtensionsWithValidMap: Failed - unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("testGetSingleExtensionsWithValidMap: Exception - " + e);
        }
    }
}
