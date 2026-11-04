/*
 * @test
 * @summary Test for JrtPath.register method to ensure it calls the overloaded method with correct parameters.
 * @run main JrtPathWitupTest
 */

import java.nio.file.WatchService;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;

public class JrtPathWitupTest {

    public static void main(String[] args) {
        testRegisterCallsOverloadedMethod();
    }

    public static void testRegisterCallsOverloadedMethod() {
        class DummyWatchService implements WatchService {
            boolean registered = false;
            WatchEvent.Kind<?>[] receivedEvents = null;
            @Override
            public void close() {}
            @Override
            public boolean isOpen() { return true; }
            @Override
            public WatchKey register(WatchEvent.Kind<?>... events) {
                this.registered = true;
                this.receivedEvents = events;
                return null;
            }
        }

        DummyWatchService dummyService = new DummyWatchService();
        jdk.internal.jrtfs.JrtPath path = new jdk.internal.jrtfs.JrtPath(null, "/some/path");
        try {
            WatchKey key = path.register(dummyService, new WatchEvent.Kind<?>[] {null});
            if (dummyService.registered && dummyService.receivedEvents != null && dummyService.receivedEvents.length == 1) {
                System.out.println("PASS: register method called with correct parameters.");
            } else {
                System.out.println("FAIL: register method not called as expected.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown during register: " + e);
        }
    }
}
