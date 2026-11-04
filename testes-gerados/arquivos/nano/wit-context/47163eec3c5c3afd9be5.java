/* @test    @summary Testa o método compareTo de java.lang.Runtime com entrada nula para revelar possível NullPointerException    @run main RuntimeWitupTest */

import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testCompareToWithNull();
    }

    public static void testCompareToWithNull() {
        Runtime runtime = Runtime.getRuntime();
        try {
            // Invoca compareTo com null para verificar se lança NullPointerException
            runtime.compareTo(null);
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Exceção esperada, teste passa
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            // Qualquer outra exceção, falha
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
