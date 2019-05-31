package app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberPlateTest {

    NumberPlate numberPlate;

    @BeforeEach
    void init() {
        numberPlate = new NumberPlate();
    }

    @Nested
    @DisplayName("PatternTest")
    class NumberPlatePatternTest {
        @Test
        void testAlphabet() {
            assertAll("Letters in serial number should be from the Russian alphabet",
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("A333BE 116 RUS")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("K333MH 116 RUS")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("O333PC 116 RUS")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("T333YX 116 RUS"))
            );
        }

        @Test
        public void testLettersCase() {
            assertAll("Not supported lower case characters",
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("с333но 116 RUS")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("С333НО 116 rus"))
            );
        }

        @Test
        public void testNumberReduction() {
            assertThrows(IllegalArgumentException.class,
                    () -> numberPlate.setNumberPlate("А0АА 116 RUS"),
                    "Should contain 3 digits without reduction");
        }

        @Test
        public void testValidLetters() {
            assertAll("Should contain valid letters in serial number",
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("Б111МН 116 RUS")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> numberPlate.setNumberPlate("П111КЛ 116 RUS"))
            );

        }

        @Test
        public void testRegionCodeExistence() {
            assertThrows(IllegalArgumentException.class,
                    () -> numberPlate.setNumberPlate("А111ВЕ"),
                    "Region code is missing");
        }

        @Test
        public void testRegionCodeValid() {
            assertThrows(IllegalArgumentException.class,
                    () -> numberPlate.setNumberPlate("А111ВЕ 16 RUS"),
                    "Region code is wrong (should be 116 RUS)");
        }

        @Test
        public void testCharactersOrder() {
            assertThrows(IllegalArgumentException.class,
                    () -> numberPlate.setNumberPlate("АВ111Е 116 RUS"),
                    "Wrong letters order");
        }
    }


    @Test
    @DisplayName("ParsingTest")
    public void testParsingNumberPlate() {
        String number = "В999КМ 116 RUS";
        numberPlate.setNumberPlate(number);

        assertEquals(number, numberPlate.getNumberPlate(),
                "Result is not equal to the original");
    }

    @Nested
    class GetNextTest {
        @Test
        public void testSimpleCase() {
            numberPlate.setNumberPlate("А111АА 116 RUS");
            assertEquals("А112АА 116 RUS", numberPlate.getNext(),
                    "Resulting value is not next value");
        }

        @Test
        public void testCaseWhenDigitsHasMaximumValue() {
            numberPlate.setNumberPlate("А999АА 116 RUS");
            assertEquals("А000АВ 116 RUS", numberPlate.getNext(),
                    "Resulting value is not next value");
        }

        @Test
        public void testCaseWhenLetterHasMaximumValue() {
            numberPlate.setNumberPlate("А999АХ 116 RUS");
            assertEquals("А000ВА 116 RUS", numberPlate.getNext(),
                    "Resulting value is not next value");
        }

        @Test
        public void testCaseWhenNumberPlateIsMaximum() {
            numberPlate.setNumberPlate("Х999ХХ 116 RUS");
            assertEquals("А000АА 116 RUS", numberPlate.getNext(),
                    "Resulting value is not next value");
        }

        @Test
        public void testNextAfterRandom() {
            String randomNumber = numberPlate.getRandom();
            String nextAfterRandomNumber = numberPlate.getNext();
            numberPlate.setNumberPlate(randomNumber);

            assertEquals(numberPlate.getNext(), nextAfterRandomNumber,
                    "Should be return next after random value");
        }
    }
}