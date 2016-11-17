package roms;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Immutable class for Money objects.
 * 
 * Money objects are decimal numbers. Internally, they are arbitrary precision
 * and arithmetic is exact. However, they always are displayed rounded to 2
 * decimal places. Rounding is round-to-even.
 * 
 * 
 * @author pbj
 *
 */
public class Money {
    private BigDecimal amount;
    
    public Money() {
        amount = BigDecimal.ZERO;
    }
    
    public Money(String str) {
        amount = new BigDecimal(str);
    }
    
    private Money(BigDecimal theAmount) {
        amount = theAmount; 
    }
       
    public Money add(Money m) {
        Money result = new Money(amount.add(m.amount));
        return result;
    }
    
    public Money multiply(int i) {
        BigDecimal multiplier = new BigDecimal(i);
        Money result = new Money(amount.multiply(multiplier));
        return result;
    }
    
    public Money addPercent(int percent) {
        BigDecimal multiplier = new BigDecimal(percent + 100).movePointLeft(2);
        Money result = new Money(amount.multiply(multiplier));
        return result;
    }
    
    public String toString() {
        BigDecimal roundedAmount = amount.setScale(2, RoundingMode.HALF_EVEN);
        return roundedAmount.toString();
    }
    
}
