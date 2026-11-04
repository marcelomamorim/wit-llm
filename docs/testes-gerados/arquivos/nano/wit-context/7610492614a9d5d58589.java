/* @test    @summary Teste do método release() de SystemModuleFinders para verificar comportamento com ByteBuffer nulo    @run main SystemModuleFindersWitupTest */

import java.nio.ByteBuffer;
import jdk.internal.module.SystemModuleFinders;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        testReleaseWithNullBuffer();
        testReleaseWithValidBuffer();
    }

    private static void testReleaseWithNullBuffer() {
        try {
            SystemModuleFinders.release(null);
            System.out.println("FAIL: Esperado NullPointerException ao passar null");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException lançado como esperado ao passar null");
        } catch (Throwable t) {
            System.out.println("FAIL: Exceção inesperada ao passar null: " + t);
        }
    }

    private static void testReleaseWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            SystemModuleFinders.release(buffer);
            System.out.println("PASS: Método release() executado com ByteBuffer válido sem lançar exceções");
        } catch (Throwable t) {
            System.out.println("FAIL: Exceção inesperada ao passar ByteBuffer válido: " + t);
        }
    }
}
