package edu.auburn.bzl0048.metro;

import cn.hutool.core.util.StrUtil;
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


    @Test
    public void testStrUtil() {
        System.out.println(StrUtil.isEmpty(" "));
        System.out.println(StrUtil.isBlank(" "));
        System.out.println(StrUtil.isBlank(null));
        System.out.println(StrUtil.isBlank(""));
    }
}
