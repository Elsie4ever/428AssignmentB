import com.company.Main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.*;

import java.io.PrintStream;

import static org.junit.Assert.*;

public class calculatorTest {
    //input validation check
    @Test
    //If all input are correct
    public void NoArgsValidInputs() {
        Main calculator = new Main();
        boolean validity = calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
        assertEquals(true,validity);
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "fromPostal" is null
    public void EmptyFrom() {
        Main calculator = new Main();
        calculator.isValidEntries("", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "fromPostal" in wrong format
    public void InvalidFrom() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "toPostal" is null
    public void EmptyTo() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "", 10.5, 10.5, 20.0, 10.0, "xpress");
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "toPostal" in wrong format
    public void InvalidTo() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H", 10.5, 10.5, 20.0, 10.0, "xpress");
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "len" is 0
    public void ZeroLength() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 0.0, 10.5, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "len" is negative
    public void NegativeLength() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", -5.0, 10.5, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input not a number
    public void InvalidNumberLength(){
        try {
            String[] args = {"H2X 2E2", "H3A 1H3", "notnum", "5.0", "20.0", "10.0", "xpress"};
            Main calculator = new Main();
            calculator.main(args);
        }
        catch(IllegalArgumentException e){
            String message = "ERROR: Input format is incorrect.";
            assertEquals(message, e.getMessage());
            throw new IllegalArgumentException(message);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    //Input "wid" is 0
    public void ZeroWidth() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 0.0, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "wid" is negative
    public void NegativeWidth() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, -5.0, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "height" is 0
    public void ZeroHeight() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 0.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "height" is negative
    public void NegativeHeight() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, -20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "weight" is 0
    public void ZeroWeight() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 0.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "weight" is negative
    public void NegativeWeight() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, -10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "weight" is above 30kg
    public void Overweight() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 31.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "type" does not match any option
    public void InvalidPackageType() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "inexistent");
    }

    //test determineLocation()
    @Test
    //If postal code start with 'h'
    public void EastPostalCode() {
        Main calculator = new Main();
        assertEquals(Main.Location.EAST,calculator.determineLocation("H2X 2E2"));
    }
    @Test
    //If postal code start with 'x'
    public void WestPostalCode() {
        Main calculator = new Main();
        assertEquals(Main.Location.WEST,calculator.determineLocation("X2X 2E2"));
    }
    @Test(expected = IllegalArgumentException.class)
    //If postal code not in east or west
    public void InvalidPostalCode() {
        Main calculator = new Main();
        calculator.determineLocation("D2X 2E2");
    }
    //test determineType()
    @Test
    //If type belongs to standard
    public void StandardType() {
        Main calculator = new Main();
        assertEquals(Main.Type.STANDARD,calculator.determineType(2.0, 3.0, 0.8, 0.3));
    }
    @Test
    //If type belongs to letter
    public void LetterType() {
        Main calculator = new Main();
        assertEquals(Main.Type.LETTER,calculator.determineType(30.8, 20.1, 0.8, 0.3));
    }
    @Test
    //If type belongs to pack
    public void PackType() {
        Main calculator = new Main();
        assertEquals(Main.Type.PACK,calculator.determineType(35.5, 30.0, 2.5, 0.8));
    }
    @Test
    //If type belongs to oversize
    public void OversizeType() {
        Main calculator = new Main();
        assertEquals(Main.Type.OVERSIZE,calculator.determineType(35.5, 30.0, 2.5, 15.0));
    }
    @Test(expected = IllegalArgumentException.class)
    //If type belongs to nothing
    public void NullType() {
        Main calculator = new Main();
        calculator.determineType(50.0, 60.0, 100.0, 15.0);
    }
    //Not yet screenshot. Example test for output validation
    @Test
    public void RegularRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void RegularNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "regular");
        assertTrue(1.5 == rate);
    }
    @Test(expected = IllegalArgumentException.class)
    public void StandardPriorityOption() {
        Main calculator = new Main();
        calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "priority");
    }
    @Test(expected = IllegalArgumentException.class)
    public void StandardPriorityOptionNational() {
        Main calculator = new Main();
        calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "priority");
    }
    @Test
    public void XpressStandardRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "xpress");
        assertTrue(13.85 == rate);
    }
    @Test
    public void XpressStandardNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.STANDARD, 2.0, 3.0, 0.8, 0.3, "xpress");
        assertTrue(21.0 == rate);
    }
    @Test
    public void RegularLetterRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void RegularLetterRateNational() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void PriorityLetterRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "priority");
        assertTrue(27.9 == rate);
    }
    @Test
    public void PriorityLetterNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "priority");
        assertTrue(41.1 == rate);
    }
    @Test
    public void XpressLetterRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "xpress");
        assertTrue(15.3 == rate);
    }
    @Test
    public void XpressLetterNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.LETTER, 30.8, 20.1, 0.8, 0.3, "xpress");
        assertTrue(21.65 == rate);
    }
    @Test
    public void RegularPackRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void RegularPackNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void PriorityPackRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "priority");
        assertTrue(28.95 == rate);
    }
    @Test
    public void PriorityPackNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "priority");
        assertTrue(47.05 == rate);
    }
    @Test
    public void XpressPackRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.EAST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "xpress");
        assertTrue(18.8 == rate);
    }
    @Test
    public void XpressPackNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.PACK, 35.5, 30.0, 2.5, 0.8, "xpress");
        assertTrue(32.3 == rate);
    }
    @Test
    public void RegularOversizeRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.WEST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void RegularOversizeNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "regular");
        assertTrue(1.5 == rate);
    }
    @Test
    public void PriorityOversizeRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.WEST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "priority");
        assertTrue(28.95 == rate);
    }
    @Test
    public void PriorityOversizeNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "priority");
        assertTrue(47.05 == rate);
    }
    @Test
    public void XpressOversizeRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.WEST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "xpress");
        assertTrue(18.8 == rate);
    }
    @Test
    public void XpressOversizeNationalRate() {
        Main calculator = new Main();
        double rate = calculator.calculateRate(Main.Location.EAST, Main.Location.WEST, Main.Type.OVERSIZE, 35.5, 30.0, 2.5, 15.0, "xpress");
        assertTrue(32.3 == rate);
    }

}