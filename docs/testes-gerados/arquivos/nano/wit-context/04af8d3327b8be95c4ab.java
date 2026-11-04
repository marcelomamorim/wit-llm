/*
 * @test
 * @summary Test for ServiceLoader.next() method to verify exception handling and normal behavior.
 * @run main ServiceLoaderWitupTest
 */

import java.util.ServiceLoader;
import java.util.NoSuchElementException;

public class ServiceLoaderWitupTest {

    public static void main(String[] args) {
        testNextWithNoProviders();
        testNextWithProvider();
    }

    static void testNextWithNoProviders() {
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                    @Override
                    public Object next() {
                        throw new NoSuchElementException();
                    }
                };
            }
        };
        try {
            loader.next();
            System.out.println("testNextWithNoProviders: FAILED - Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            System.out.println("testNextWithNoProviders: PASSED");
        } catch (Throwable t) {
            System.out.println("testNextWithNoProviders: FAILED - Unexpected exception: " + t);
        }
    }

    static void testNextWithProvider() {
        ServiceLoader<Object> loader = new ServiceLoader<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    boolean hasNextCalled = false;
                    @Override
                    public boolean hasNext() {
                        if (!hasNextCalled) {
                            hasNextCalled = true;
                            return true;
                        }
                        return false;
                    }
                    @Override
                    public Object next() {
                        if (hasNext()) {
                            return new Object();
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
        try {
            Object provider = loader.next();
            if (provider != null) {
                System.out.println("testNextWithProvider: PASSED");
            } else {
                System.out.println("testNextWithProvider: FAILED - Provider is null");
            }
        } catch (Throwable t) {
            System.out.println("testNextWithProvider: FAILED - Unexpected exception: " + t);
        }
    }
}
