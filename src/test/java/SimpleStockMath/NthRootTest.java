package SimpleStockMath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class NthRootTest {
    @Test
    public void testNthRoot() {
        Assertions.assertEquals(NthRoot.nthRoot(3, BigDecimal.valueOf(8)), new BigDecimal("2.0000000000"));
        Assertions.assertEquals(NthRoot.nthRoot(3, BigDecimal.valueOf(125)), new BigDecimal("5.0000000000"));
        Assertions.assertEquals(NthRoot.nthRoot(2, BigDecimal.valueOf(9)), new BigDecimal("3.0000000000"));
        Assertions.assertEquals(NthRoot.nthRoot(2, BigDecimal.valueOf(2)), new BigDecimal("1.4142135623"));
        Assertions.assertEquals(NthRoot.nthRoot(3, BigDecimal.valueOf(126)), new BigDecimal("5.0132979350"));
        Assertions.assertEquals(NthRoot.nthRoot(7, BigDecimal.valueOf(125879645)), new BigDecimal("5.0132979350"));
    }

}
