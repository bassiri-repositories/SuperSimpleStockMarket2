package SuperSimpleStockMarket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TradeTest {
    @Test
    public void processTradeStockNotInReferenceData() {
        Trade trade = new Trade("COF", BigDecimal.valueOf(25), 1, BuySell.SELL);
        boolean processed = trade.processTrade();
        Assertions.assertFalse(processed);
    }

    @Test
    public void processTradePreferredStock() {

        Trade trade = new Trade("GIN", BigDecimal.valueOf(25), 1, BuySell.SELL);
        boolean processed = trade.processTrade();
        Stock stock = trade.getStock();

        Assertions.assertTrue(processed);
        Assertions.assertEquals(stock.getStockType(), StockType.PREFERRED);
        Assertions.assertEquals(stock.getFixedDividend(), new BigDecimal("0.02"));
        Assertions.assertEquals(stock.getParValue(), new BigDecimal("100"));
        Assertions.assertEquals(trade.getPrice(), new BigDecimal("25"));
        Assertions.assertEquals(new BigDecimal("0.0800000000"), trade.getDividendYield());
        Assertions.assertEquals(new BigDecimal("3.1250000000"), trade.getPeRatio());
    }

    @Test
    public void processTradeCommonStock() {
        Trade trade = new Trade("POP", BigDecimal.valueOf(250), 15, BuySell.BUY);
        boolean processed = trade.processTrade();
        Stock stock = trade.getStock();

        Assertions.assertTrue(processed);
        Assertions.assertEquals(stock.getStockType(), StockType.COMMON);
        Assertions.assertEquals(stock.getLastDividend(), new BigDecimal("8"));
        Assertions.assertEquals(stock.getParValue(), new BigDecimal("100"));
        Assertions.assertEquals(trade.getPrice(), new BigDecimal("250"));
        Assertions.assertEquals(new BigDecimal("0.0320000000"), trade.getDividendYield());
        Assertions.assertEquals(new BigDecimal("31.2500000000"), trade.getPeRatio());
    }
}
