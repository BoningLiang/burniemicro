package io.github.burnieliang.metro;

import org.junit.Test;

public class ParseIntTest {


    @Test
    public void test() {
        String testStr = "";
        long time;
        try {
            time = Integer.parseInt(testStr);
            System.out.println(time);
        } catch (Exception e) {
            time = 1000;
        }
        System.out.println(time);

    }
}
