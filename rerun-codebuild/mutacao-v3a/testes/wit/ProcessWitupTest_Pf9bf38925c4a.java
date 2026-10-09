/*
 * @test
 * @summary Test for Process.toHandle() method to verify behavior.
 * @run main ProcessWitupTest_Pf9bf38925c4a
 */

import java.lang.Process;

public class ProcessWitupTest_Pf9bf38925c4a {

    public static void main(String[] args) {
        testToHandleThrowsUnsupportedOperationException();
        testToHandleNormalBehavior();
    }

    private static void testToHandleThrowsUnsupportedOperationException() {
        Process process = new DummyProcess();
        try {
            process.toHandle();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testToHandleNormalBehavior() {
        // Como o método atual lança exceção, esta parte é apenas para ilustrar o comportamento esperado quando não lança.
        // Se no futuro o método for alterado para comportamentos diferentes, este teste pode ser ajustado.
        // Aqui, criamos um Process que não lança exceção para simular esse cenário.
        Process process = new DummyProcessWithoutException();
        try {
            ProcessHandle handle = process.toHandle();
            System.out.println("PASS: toHandle() retornou: " + handle);
        } catch (UnsupportedOperationException e) {
            System.out.println("INFO: Expected exception for current implementation.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    // Classe dummy que simula um Process que lança UnsupportedOperationException
    static class DummyProcess extends Process {
        @Override
        public ProcessHandle toHandle() {
            throw new UnsupportedOperationException(this.getClass().getName() + ".toHandle() not supported");
        }
        @Override
        public OutputStream getOutputStream() { return null; }
        @Override
        public InputStream getInputStream() { return null; }
        @Override
        public InputStream getErrorStream() { return null; }
        @Override
        public int waitFor() { return 0; }
        @Override
        public int exitValue() { return 0; }
        @Override
        public void destroy() { }
    }

    // Classe dummy que simula um Process que não lança exceção
    static class DummyProcessWithoutException extends Process {
        @Override
        public ProcessHandle toHandle() {
            return ProcessHandle.current();
        }
        @Override
        public OutputStream getOutputStream() { return null; }
        @Override
        public InputStream getInputStream() { return null; }
        @Override
        public InputStream getErrorStream() { return null; }
        @Override
        public int waitFor() { return 0; }
        @Override
        public int exitValue() { return 0; }
        @Override
        public void destroy() { }
    }
}
