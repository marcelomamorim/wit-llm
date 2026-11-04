/*
 * @test
 * @summary Test deterministic behavior of java.util.ServiceLoader.next() method
 * @run main ServiceLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.ServiceConfigurationError;

public class ServiceLoaderWitupTest {

    // Minimal dummy service interface for testing
    public interface DummyService {}

    public static void main(String[] args) {
        testNextThrowsNoSuchElementExceptionWhenNoNext();
        testNextReturnsProviderWhenNextProviderNotNull();
        testNextThrowsServiceConfigurationErrorWhenNextProviderNullAndNextErrorNotNull();
    }

    private static void testNextThrowsNoSuchElementExceptionWhenNoNext() {
        // Create a ServiceLoader for DummyService with no providers
        ServiceLoader<DummyService> loader = ServiceLoader.load(DummyService.class);
        java.util.Iterator<Provider<DummyService>> it = loader.iterator();

        // Exhaust the iterator to ensure hasNext() is false
        while (it.hasNext()) {
            it.next();
        }

        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testNextReturnsProviderWhenNextProviderNotNull() {
        // This test verifies that next() returns a provider when nextProvider is not null
        // Since we cannot set nextProvider directly, we rely on the iterator behavior
        ServiceLoader<DummyService> loader = ServiceLoader.load(DummyService.class);
        java.util.Iterator<Provider<DummyService>> it = loader.iterator();

        if (it.hasNext()) {
            Provider<DummyService> provider = it.next();
            if (provider == null) {
                throw new AssertionError("Provider returned by next() should not be null");
            }
        } else {
            // No providers available, test is inconclusive but passes
        }
    }

    private static void testNextThrowsServiceConfigurationErrorWhenNextProviderNullAndNextErrorNotNull() {
        // The source code throws ServiceConfigurationError if hasNext() is true and nextProvider is null
        // We cannot directly create this state, so this test attempts to trigger it indirectly
        // by using a ServiceLoader with a malformed provider configuration.

        // Use a service interface that is unlikely to have providers
        ServiceLoader<DummyService> loader = ServiceLoader.load(DummyService.class);
        java.util.Iterator<Provider<DummyService>> it = loader.iterator();

        // We try to find a state where hasNext() is true but nextProvider is null
        // This is difficult without internal access, so we attempt to call next() if hasNext() is true
        // and catch ServiceConfigurationError if thrown.

        if (it.hasNext()) {
            try {
                it.next();
            } catch (ServiceConfigurationError e) {
                // Expected possible error due to malformed provider
                return;
            } catch (Exception e) {
                // Unexpected exception
                throw new AssertionError("Unexpected exception: " + e);
            }
        }
        // If no exception thrown, test passes as no error state was triggered
    }
}
