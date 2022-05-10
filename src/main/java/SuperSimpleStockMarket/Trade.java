package SuperSimpleStockMarket;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public class Trade {
    private final String stockSymbol;
    private final BigDecimal price;
    private final int quantity;
    private final BuySell buySell;
    private BigDecimal dividendYield;
    private BigDecimal peRatio;
    private boolean tradeProcessed = false;
    private Instant tradeTimestamp;
    private Stock stock;

    public int getQuantity() {
        return quantity;
    }
    public BigDecimal getDividendYield() {
        return dividendYield;
    }
    public BigDecimal getPeRatio() {
        return peRatio;
    }
    public Instant getTradeTimestamp() {
        return tradeTimestamp;
    }
    public Stock getStock() {
        return stock;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public Trade(String stockSymbol, BigDecimal price, int quantity, BuySell buySell) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.quantity = quantity;
        this.buySell = buySell;
    }

    public boolean processTrade() {
        if(StockMap.stockMap().containsKey(stockSymbol)) {
            stock = StockMap.stockMap().get(stockSymbol);
            dividendYield = calculateDividendYield();
            peRatio = calculatePeRatio();
            tradeProcessed = true;
            tradeTimestamp = Instant.now();
            return tradeProcessed;
        }
        else {
            String str = String.format("Stock Symbol: %s, is not in the reference data. Ignoring this trade.", stockSymbol);
            System.out.println(str);
            return tradeProcessed;
        }
    }

    private BigDecimal calculateDividendYield() {
        if(stock.getStockType() == StockType.COMMON) {
            return stock.getLastDividend().divide(price, 10, RoundingMode.HALF_DOWN);
        }
        else {
            return stock.getFixedDividend().multiply(stock.getParValue()).divide(price, 10, RoundingMode.HALF_DOWN);
        }
    }

    private BigDecimal calculatePeRatio() {
        return price.divide(stock.getLastDividend(), 10, RoundingMode.HALF_DOWN);
    }

    public String reportTrade() {
        if(tradeProcessed) {
            return String.format("Stock Symbol: %s, Quantity: %d, Buy/Sell: %s, Price: %s, Dividend Yield: %s, P/E ratio: %s, Trade timestamp %s", stockSymbol, quantity, buySell, price.toString(), dividendYield.toPlainString(), peRatio.toPlainString(), tradeTimestamp);
        }
        else {
            return String.format("Stock Symbol: %s, Quantity: %d, Buy/Sell: %s, Price: %s", stockSymbol, quantity, buySell, price.toString());
        }
    }
}
