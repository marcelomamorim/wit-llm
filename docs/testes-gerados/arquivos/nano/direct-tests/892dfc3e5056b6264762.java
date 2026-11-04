/* @test    @summary Test Provider.forEach method with a simple action    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.function.BiConsumer;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testForEachInvokesAction();
    }

    public static void testForEachInvokesAction() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
                super.forEach(action);
            }
        };
        final boolean[] invoked = {false};
        BiConsumer<Object, Object> action = (k, v) -> {
            invoked[0] = true;
            // Optionally, check key and value types or values
            if (k == null || v == null) {
                throw new AssertionError("Key or value is null");
            }
        };
        provider.forEach(action);
        if (!invoked[0]) {
            throw new AssertionError("Action was not invoked by forEach");
        }
    }
}
