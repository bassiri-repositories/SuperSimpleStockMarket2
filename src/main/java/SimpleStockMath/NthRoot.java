package SimpleStockMath;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
The options for obtaining the Nth root are described in https://www.baeldung.com/java-nth-root

They include:
1- use the 'double' data type and to calculate the Nth root, raise to the power of 1/N using Math libraries
2- use BigDecimal and implement the Newton-Raphson method

The 'double' data type looses precision, hence I opted for the BigDecimal in all values apart from those that are
obviously whole numbers (like quanity of shares)

The SCALE defines the number of decimal places to consider and is set to 10 here.

The logic in this class was copied from https://stackoverflow.com/questions/22695654/computing-the-nth-root-of-p-using-bigdecimals
It is covered by JUnit tests to verify its accuracy (NthRootTest.java)
*/

public class NthRoot {
    private static final int SCALE = 10;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;

    public static BigDecimal nthRoot(final int theRootToFind, final BigDecimal theNumberToGetRootOf) {
        return nthRoot(theRootToFind, theNumberToGetRootOf, BigDecimal.valueOf(.1).movePointLeft(SCALE));
    }

    private static BigDecimal nthRoot(final int theRootToFind, final BigDecimal theNumberToGetRootOf, final BigDecimal p) {

        if (theNumberToGetRootOf.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("nth root can only be calculated for positive numbers");
        }
        if (theNumberToGetRootOf.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        BigDecimal xPrev = theNumberToGetRootOf;
        BigDecimal x = theNumberToGetRootOf.divide(new BigDecimal(theRootToFind), SCALE, ROUNDING_MODE);  // starting "guessed" value...
        while (x.subtract(xPrev).abs().compareTo(p) > 0) {
            xPrev = x;
            x = BigDecimal.valueOf(theRootToFind - 1.0)
                    .multiply(x)
                    .add(theNumberToGetRootOf.divide(x.pow(theRootToFind - 1), SCALE, ROUNDING_MODE))
                    .divide(new BigDecimal(theRootToFind), SCALE, ROUNDING_MODE);
        }
        return x;
    }
}
