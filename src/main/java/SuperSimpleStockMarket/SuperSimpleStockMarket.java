package SuperSimpleStockMarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import SimpleStockMath.NthRoot;
import lombok.extern.java.Log;

@Log
public class SuperSimpleStockMarket {
    private final int lookbackTime;
    private final ChronoUnit chronoUnit;

    public SuperSimpleStockMarket(int lookbackTime, ChronoUnit chronoUnit) {
        this.lookbackTime = lookbackTime;
        this.chronoUnit = chronoUnit;
    }

    private final List<Trade> processedTrades = Collections.synchronizedList(new ArrayList<>());
    private final List<Trade> unprocessedTrades = Collections.synchronizedList(new ArrayList<>());

    public void processTrade(Trade trade) {
        if (trade.processTrade()) {
            processedTrades.add(trade);
        } else {
            unprocessedTrades.add(trade);
        }
    }

    public BigDecimal calculateVolumeWeightedStockPrice() {
        synchronized (processedTrades) {
            Instant currentInstant = Instant.now();
            BigDecimal sigmaTradeTimesQuantity = BigDecimal.ZERO;
            int sigmaQuantity = 0;
            for (Trade trade:processedTrades) {
                if(trade.getTradeTimestamp().isAfter(currentInstant.minus(lookbackTime, chronoUnit))) {
                    sigmaTradeTimesQuantity = sigmaTradeTimesQuantity.add(trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
                    sigmaQuantity += trade.getQuantity();
                }
            }
            BigDecimal volumeWeightedStockPrice = BigDecimal.ZERO;
            if(sigmaQuantity > 0 ) {
                volumeWeightedStockPrice = sigmaTradeTimesQuantity.divide(BigDecimal.valueOf(sigmaQuantity), 10, RoundingMode.HALF_DOWN);
            }
            System.out.println("Volume Weighted Stock Price: " + volumeWeightedStockPrice.toPlainString());
            return volumeWeightedStockPrice;
        }
    }

    public BigDecimal calculateGeometricMean() {
        synchronized (processedTrades) {
            BigDecimal productOfPrices = processedTrades.stream().map(Trade::getPrice).toList().stream().reduce(BigDecimal.ONE, BigDecimal::multiply);
            BigDecimal geometricMean = NthRoot.nthRoot(processedTrades.size(), productOfPrices);
            System.out.println("Geometric Mean: " + geometricMean.toPlainString());
            return geometricMean;

        }
    }

    public void printTrades() {
        synchronized (processedTrades) {
            System.out.println("Processed Trades:");
            processedTrades.forEach(trade -> System.out.println("\t" + trade.reportTrade()));
        }
        synchronized (unprocessedTrades) {
            System.out.println("Unprocessed Trades:");
            unprocessedTrades.forEach(trade -> System.out.println("\t" + trade.reportTrade()));
        }
    }
}
