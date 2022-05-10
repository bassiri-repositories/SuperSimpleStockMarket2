package SuperSimpleStockMarket;

import java.math.BigDecimal;

public class Stock {
    public StockType getStockType() {
        return stockType;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    private final StockType stockType;
    private final BigDecimal lastDividend;
    private final BigDecimal fixedDividend;
    private final BigDecimal parValue;
    private final String stockSymbol;

    public Stock(String stockSymbol, StockType stockType, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
        this.stockSymbol = stockSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }
}

