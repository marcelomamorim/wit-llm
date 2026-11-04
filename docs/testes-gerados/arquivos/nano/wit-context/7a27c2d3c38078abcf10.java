/* @test    @summary Testa o método obtrudeException para comportamento esperado com entrada nula e não nula    @run main CompletableFutureWitupTest */

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWitupTest {

    public static void main(String[] args) {
        testObtrudeExceptionWithNull();
        testObtrudeExceptionWithValidThrowable();
    }

    private static void testObtrudeExceptionWithNull() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        try {
            cf.obtrudeException(null);
            System.out.println("testObtrudeExceptionWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testObtrudeExceptionWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testObtrudeExceptionWithNull: FAILED - Unexpected exception: " + t);
        }
    }

    private static void testObtrudeExceptionWithValidThrowable() {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Throwable t = new RuntimeException("Test exception");
        cf.obtrudeException(t);
        if (cf.isDone() && cf.getNow() instanceof java.util.concurrent.CompletableFuture.AltResult) {
            Object result = cf.getNow();
            if (result instanceof Throwable) {
                Throwable resultThrowable = (Throwable) result;
                if (resultThrowable == t) {
                    System.out.println("testObtrudeExceptionWithValidThrowable: PASSED");
                    return;
                }
            }
        }
        System.out.println("testObtrudeExceptionWithValidThrowable: FAILED - Unexpected state or result");
    }
}
