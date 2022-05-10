package SuperSimpleStockMarket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

/*
To test the 15 minute window, the lookback time is made configurable
To show trades falling outside this lookback window, a smaller window (in seconds) is used with a sleep delay between
trade submissions
*/

public class SuperSimpleStockMarketTest {
    @Test
    public void testAllWithinLookbackTime() {
        runTheTest(15, ChronoUnit.MINUTES, 0,
                new BigDecimal("3.2608695652"), new BigDecimal("5.0000000000"));
    }

    @Test
    public void testNoneWithinLookbackTime()  {
        runTheTest(15, ChronoUnit.SECONDS, 10000,
                new BigDecimal("0"), new BigDecimal("5.0000000000"));
    }

    @Test
    public void testSomeWithinLookbackTime()  {
        runTheTest(35, ChronoUnit.SECONDS, 10000,
                new BigDecimal("7.5000000000"), new BigDecimal("5.0000000000"));
    }

    private void runTheTest(int lookbackTime, ChronoUnit chronoUnit, int sleepTime,
                            BigDecimal expectedWeightedStockPrice, BigDecimal expectedGeometricMean) {
        SuperSimpleStockMarket superSimpleStockMarket = new SuperSimpleStockMarket(lookbackTime, chronoUnit);
        processTrades(superSimpleStockMarket, giveMeListOfTrades(), sleepTime);
        superSimpleStockMarket.printTrades();
        BigDecimal weightedStockPrice = superSimpleStockMarket.calculateVolumeWeightedStockPrice();
        BigDecimal geometricMean = superSimpleStockMarket.calculateGeometricMean();
        Assertions.assertEquals(weightedStockPrice, expectedWeightedStockPrice);
        Assertions.assertEquals(geometricMean, expectedGeometricMean);
    }

    private void processTrades(SuperSimpleStockMarket superSimpleStockMarket, List<Trade> trades, int sleepTime)  {
        for (int i = 0; i < trades.size(); i++) {
            superSimpleStockMarket.processTrade(trades.get(i));
            sleep(sleepTime);
        }
    }

    private void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Trade> giveMeListOfTrades() {
        return List.of(
                new Trade("POP", BigDecimal.valueOf(1), 15, BuySell.BUY),
                new Trade("ALE", BigDecimal.valueOf(5), 7, BuySell.BUY),
                new Trade("GIN", BigDecimal.valueOf(25), 1, BuySell.SELL),
                new Trade("COF", BigDecimal.valueOf(25), 1, BuySell.SELL)
        );
    }
}
