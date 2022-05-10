import SuperSimpleStockMarket.SuperSimpleStockMarket;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import SuperSimpleStockMarket.Trade;
import SuperSimpleStockMarket.BuySell;

/*
This class is for arbitrary testing.
The focused tests are added to JUnit directories under src/test/java
 */
public class MainMethodClass {
    public static void main(String[] args) throws InterruptedException {
        SuperSimpleStockMarket superSimpleStockMarket = new SuperSimpleStockMarket(15, ChronoUnit.MINUTES);
        List<Trade> trades = List.of(
                new Trade("POP", BigDecimal.valueOf(1), 1, BuySell.BUY),
                new Trade("ALE", BigDecimal.valueOf(5), 1, BuySell.BUY),
                new Trade("GIN", BigDecimal.valueOf(25), 1, BuySell.SELL)
        );


        for(int i = 0; i< trades.size(); i++) {
            superSimpleStockMarket.processTrade(trades.get(i));
            //Thread.sleep(10000);
        }


        superSimpleStockMarket.printTrades();
        superSimpleStockMarket.calculateVolumeWeightedStockPrice();
        superSimpleStockMarket.calculateGeometricMean();
    }
}
