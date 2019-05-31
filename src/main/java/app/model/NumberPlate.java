package app.model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumberPlate {
    private static final String LETTERS = "АВЕКМНОРCТУХ";
    private static final String NUMBER_PLATE_PATTERN =
            "[" + LETTERS + "][0-9]{3}[" + LETTERS + "]{2} 116 RUS";

    private int[] lettersIndex = new int[3];
    private int digits;


    public NumberPlate() {
    }

    public String getNext() {
        if (digits < 999) {
            digits++;
        } else {
            digits = 0;
            int position = lettersIndex.length - 1;

            do {
                if (lettersIndex[position] == LETTERS.length() - 1) {
                    lettersIndex[position] = 0;
                    position--;
                } else {
                    lettersIndex[position]++;
                    break;
                }
            } while (position >= 0);
        }

        return getNumberPlate();
    }

    public String getRandom() {
        Random random = new Random();
        digits = random.nextInt(1000);

        for (int i = 0; i < lettersIndex.length; i++) {
            lettersIndex[i] = random.nextInt(LETTERS.length());
        }

        return getNumberPlate();
    }

    public String getNumberPlate() {
        return new StringBuilder()
                .append(LETTERS.charAt(lettersIndex[0]))
                .append(String.format("%03d", digits))
                .append(LETTERS.charAt(lettersIndex[1]))
                .append(LETTERS.charAt(lettersIndex[2]))
                .append(" 116 RUS")
                .toString();
    }

    public void setNumberPlate(String str) {
        if (str == null || !str.matches(NUMBER_PLATE_PATTERN)) {
            throw new IllegalArgumentException("String has wrong format");
        }

        digits = Integer.parseInt(str.substring(1, 4));

        lettersIndex[0] = LETTERS.indexOf(str.charAt(0));
        lettersIndex[1] = LETTERS.indexOf(str.charAt(4));
        lettersIndex[2] = LETTERS.indexOf(str.charAt(5));
    }
}