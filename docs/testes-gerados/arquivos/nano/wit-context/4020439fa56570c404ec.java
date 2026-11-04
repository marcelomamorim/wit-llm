/*
 * @test
 * @summary Test for MethodType.ConcurrentWeakInternSet constructor behavior
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;
import java.util.concurrent.ConcurrentHashMap;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testConcurrentWeakInternSetConstructor();
    }

    public static void testConcurrentWeakInternSetConstructor() {
        try {
            MethodType.ConcurrentWeakInternSet instance = new MethodType.ConcurrentWeakInternSet();
            if (instance == null) {
                throw new AssertionError("Constructor returned null instance");
            }
        } catch (Throwable t) {
            throw new AssertionError("Constructor threw an unexpected exception: " + t, t);
        }
    }
}
