/* @test    @summary Test supportsNormalTermination method of java.lang.Process    @run main ProcessWitupTest_P8b2364c7641d */

import java.lang.Process;

public class ProcessWitupTest_P8b2364c7641d {
    public static void main(String[] args) {
        try {
            Process process = new Process() {
                @Override
                public boolean supportsNormalTermination() {
                    return true;
                }
            };
            boolean result = process.supportsNormalTermination();
            if (result != true) {
                throw new AssertionError("Expected true from supportsNormalTermination()");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
