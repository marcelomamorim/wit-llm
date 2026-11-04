/* @test    @summary Test default constructor of LongSummaryStatistics    @run main LongSummaryStatisticsWitupTest */

import java.util.LongSummaryStatistics;

public class LongSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        LongSummaryStatistics stats = new LongSummaryStatistics();
        assert stats.getCount() == 0L : "Count should be zero";
        assert stats.getSum() == 0L : "Sum should be zero";
        assert stats.getMin() == Long.MAX_VALUE : "Min should be Long.MAX_VALUE";
        assert stats.getMax() == Long.MIN_VALUE : "Max should be Long.MIN_VALUE";
        System.out.println("All assertions passed.");
    }
}
