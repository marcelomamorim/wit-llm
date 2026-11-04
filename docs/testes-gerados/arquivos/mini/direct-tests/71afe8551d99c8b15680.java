/*
 * @test
 * @summary Test java.security.Provider.clear() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest extends Provider {

    private boolean implClearCalled = false;
    private boolean checkCalled = false;
    private String checkName = null;

    protected ProviderWitupTest() {
        super("TestProvider", 1.0, "Test Provider for clear() method");
    }

    @Override
    protected void implClear() {
        implClearCalled = true;
    }

    @Override
    protected void check(String name) {
        checkCalled = true;
        checkName = name;
    }

    public static void main(String[] args) {
        ProviderWitupTest p = new ProviderWitupTest();

        // Before clear
        if (p.implClearCalled) {
            throw new RuntimeException("implClear should not be called before clear()");
        }
        if (p.checkCalled) {
            throw new RuntimeException("check should not be called before clear()");
        }

        p.clear();

        // After clear
        if (!p.checkCalled) {
            throw new RuntimeException("check was not called during clear()");
        }
        if (!"clearProviderProperties.TestProvider".equals(p.checkName)) {
            throw new RuntimeException("check called with wrong name: " + p.checkName);
        }
        if (!p.implClearCalled) {
            throw new RuntimeException("implClear was not called during clear()");
        }

        System.out.println("Test passed.");
    }
}
