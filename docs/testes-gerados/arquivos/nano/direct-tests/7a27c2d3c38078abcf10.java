/* @test    @summary Test obtrudeException method of CompletableFuture    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeExceptionWithNull();
        testObtrudeExceptionWithThrowable();
    }

    private static void testObtrudeExceptionWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(null);
            System.out.println("testObtrudeExceptionWithNull: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testObtrudeExceptionWithNull: Passed");
        } catch (Throwable t) {
            System.out.println("testObtrudeExceptionWithNull: Failed - wrong exception: " + t);
        }
    }

    private static void testObtrudeExceptionWithThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable ex = new RuntimeException("Test exception");
        cf.obtrudeException(ex);
        if (cf.isDone()) {
            try {
                cf.get();
                System.out.println("testObtrudeExceptionWithThrowable: Failed - get() did not throw");
            } catch (Throwable t) {
                if (t.getCause() == ex || t.getCause().getClass() == RuntimeException.class) {
                    System.out.println("testObtrudeExceptionWithThrowable: Passed");
                } else {
                    System.out.println("testObtrudeExceptionWithThrowable: Failed - wrong cause: " + t);
                }
            }
        } else {
            System.out.println("testObtrudeExceptionWithThrowable: Failed - not done");
        }
    }
}
