import com.company.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class calculatorTest {
    //input validation check
        @Test(expected = IllegalArgumentException.class)
        //Input "fromPostal" in wrong format
        public void Test1() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "fromPostal" is null
        public void Test2() {
            Main calculator = new Main();
            calculator.isValidEntries("", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "toPostal" in wrong format
        public void Test3() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H", 10.5, 10.5, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "toPostal" is null
        public void Test4() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "", 10.5, 10.5, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "len" is 0
        public void Test5() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H3", 0.0, 10.5, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "wid" is 0
        public void Test6() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 0.0, 20.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "height" is 0
        public void Test7() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 0.0, 10.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "weight" is 0
        public void Test8() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 0.0, "xpress");
        }
        @Test(expected = IllegalArgumentException.class)
        //Input "type" does not match any option
        public void Test9() {
            Main calculator = new Main();
            calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "what?");
        }
        @Test
        //If all input are correct
        public void Test10() {
            Main calculator = new Main();
            boolean vadility = calculator.isValidEntries("H2X 2E2", "H3A 1H3", 10.5, 10.5, 20.0, 10.0, "xpress");
            assertEquals(true,vadility);
        }

    //test determineLocation()
        @Test
        //If postal code start with 'h'
        public void Test11() {
            Main calculator = new Main();
            assertEquals(Main.Location.EAST,calculator.determineLocation("H2X 2E2"));
        }
        @Test
        //If postal code start with 'h'
        public void Test12() {
            Main calculator = new Main();
            assertEquals(Main.Location.WEST,calculator.determineLocation("X2X 2E2"));
        }
        @Test(expected = IllegalArgumentException.class)
        //If postal code not in east or west
        public void Test13() {
            Main calculator = new Main();
            calculator.determineLocation("D2X 2E2");
        }
    //test determineType()
        @Test
        //If type belongs to standard
        public void Test14() {
            Main calculator = new Main();
            assertEquals(Main.Type.STANDARD,calculator.determineType(2.0, 3.0, 0.8, 0.3));
        }

}