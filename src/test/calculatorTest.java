import com.company.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class calculatorTest {
    //input validation check
    @Test(expected = IllegalArgumentException.class)
    //Input "fromPostal" in wrong format
    public void InvalidFrom() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "fromPostal" is null
    public void EmptyFrom() {
        Main calculator = new Main();
        calculator.isValidEntries("", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "toPostal" in wrong format
    public void InvalidTo() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H", 10.5, 10.5, 20.0, 10.0, "xpress");
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "toPostal" is null
    public void EmptyTo() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "", 10.5, 10.5, 20.0, 10.0, "xpress");
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
        String[] args = {"H2X 2E2", "H3A 1H3", "notnum", "5.0", "20.0", "10.0", "xpress"};
        Main calculator = new Main();
        calculator.main(args);
    }
    @Test(expected = IllegalArgumentException.class)
    //Input not a number
    public void InvalidNumberWidth(){
        String[] args = {"H2X 2E2", "H3A 1H3", "10.5", "notnum", "20.0", "10.0", "xpress"};
        Main calculator = new Main();
        calculator.main(args);
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
    //Input not a number
    public void InvalidNumberHeight(){
        String[] args = {"H2X 2E2", "H3A 1H3", "10.5", "10.5", "notnum", "10.0", "xpress"};
        Main calculator = new Main();
        calculator.main(args);
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
    //Input not a number
    public void InvalidNumberWeight(){
        String[] args = {"H2X 2E2", "H3A 1H3", "10.5", "10.5", "20.0", "notnum", "xpress"};
        Main calculator = new Main();
        calculator.main(args);
    }
    @Test(expected = IllegalArgumentException.class)
    //Input "type" does not match any option
    public void InvalidType() {
        Main calculator = new Main();
        calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "inexistent");
    }
    @Test
    //If all input are correct
    public void ValidInputs() {
        Main calculator = new Main();
        boolean validity = calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
        assertEquals(true,validity);
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
    //If type belongs to letter
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

}