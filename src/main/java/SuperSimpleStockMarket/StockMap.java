package SuperSimpleStockMarket;

import SuperSimpleStockMarket.StockType;

import java.math.BigDecimal;
import java.util.Map;

public class StockMap {
    public static Map<String, Stock> stockMap() {
        return Map.of ("TEA", new Stock("TEA", StockType.COMMON, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(100)),
                "POP", new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8), BigDecimal.valueOf(0), BigDecimal.valueOf(100)),
                "ALE", new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), BigDecimal.valueOf(0), BigDecimal.valueOf(60)),
                "GIN", new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100)),
                "JOE", new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13), BigDecimal.valueOf(0), BigDecimal.valueOf(250)));
    }
}
