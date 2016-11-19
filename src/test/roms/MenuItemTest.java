package roms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MenuItemTest {
    private MenuItem menuItem;
    @Before
    public void setUp() throws Exception {
        menuItem = new MenuItem("test", new Money("1.00"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("test", menuItem.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        menuItem.setDescription("another desc");
        assertEquals("another desc", menuItem.getDescription());
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals("1.00", menuItem.getPrice().toString());
    }

    @Test
    public void setPrice() throws Exception {
        menuItem.setPrice(new Money("2.00"));
        assertEquals("2.00", menuItem.getPrice().toString());
    }

}