/**
 * 
 */
package roms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author pbj
 *
 */
public class MoneyTest {
    
    @Test
    public void testZero() {
        Money zero = new Money();
        assertEquals("0.00", zero.toString());
    }

    @Test
    public void testNegative() {
        Money neg = new Money("-1.00");
        assertEquals("-1.00", neg.toString());
    }

    /**
     * Tests formatting of padding and truncation
     */
    @Test
    public void testFormat() {
        Money m1 = new Money("0.127");
        assertEquals("0.13", m1.toString());
        Money m2 = new Money("3.1");
        assertEquals("3.10", m2.toString());
    }

    /**
     * Tests half-even rounding
     */
    @Test
    public void testRounding() {
        //Rounds up when odd
        Money m1 = new Money("1.055");
        assertEquals("1.06", m1.toString());

        //Rounds down when even
        Money m2 = new Money("1.025");
        assertEquals("1.02", m2.toString());

        //Rounds down when odd (and negative)
        Money mneg = new Money("-1.055");
        assertEquals("-1.06", mneg.toString());

        //Rounds down when even (and negative)
        Money mneg2 = new Money("-1.025");
        assertEquals("-1.02", mneg2.toString());
    }

    @Test
    public void testAdd() {
        Money zero = new Money().add(new Money());
        assertEquals("0.00", zero.toString());
        Money m1 = new Money("3.14").add(new Money("3.14"));
        assertEquals("6.28", m1.toString());
    }

    @Test
    public void testMultiply() {
        Money zero = new Money().multiply(0);
        assertEquals("0.00", zero.toString());
        Money m1 = new Money("5.30").multiply(5);
        assertEquals("26.50", m1.toString());
        Money m2 = new Money("5.30").multiply(-5);
        assertEquals("-26.50", m2.toString());
    }

    @Test
    public void testAddPercent() {
        Money m1 = new Money("10.00").addPercent(10);
        assertEquals("11.00", m1.toString());
        Money m2 = new Money("3.14").addPercent(0);
        assertEquals("3.14", m2.toString());
    }


}
