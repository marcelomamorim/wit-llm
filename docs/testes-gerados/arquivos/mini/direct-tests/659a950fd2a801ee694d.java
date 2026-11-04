/*
 * @test
 * @summary Test java.security.Provider.Service constructor behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal Provider subclass to pass to Service constructor
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;
        };

        // Use reflection to access the private Service constructor
        java.lang.reflect.Constructor<?> ctor = Provider.class.getDeclaredClasses()[0].getDeclaredConstructor(Provider.class);
        ctor.setAccessible(true);
        Object service = ctor.newInstance(p);

        // Check that the provider field is set correctly
        java.lang.reflect.Field providerField = service.getClass().getDeclaredField("provider");
        providerField.setAccessible(true);
        Object providerValue = providerField.get(service);
        if (providerValue != p) {
            throw new AssertionError("Service.provider field not set correctly");
        }

        // Check that aliases is empty list
        java.lang.reflect.Field aliasesField = service.getClass().getDeclaredField("aliases");
        aliasesField.setAccessible(true);
        Object aliasesValue = aliasesField.get(service);
        if (!(aliasesValue instanceof List)) {
            throw new AssertionError("Service.aliases is not a List");
        }
        if (!((List<?>) aliasesValue).isEmpty()) {
            throw new AssertionError("Service.aliases is not empty");
        }

        // Check that attributes is empty map
        java.lang.reflect.Field attributesField = service.getClass().getDeclaredField("attributes");
        attributesField.setAccessible(true);
        Object attributesValue = attributesField.get(service);
        if (!(attributesValue instanceof Map)) {
            throw new AssertionError("Service.attributes is not a Map");
        }
        if (!((Map<?, ?>) attributesValue).isEmpty()) {
            throw new AssertionError("Service.attributes is not empty");
        }
    }
}
