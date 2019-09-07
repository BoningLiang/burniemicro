package edu.auburn.bzl0048.metrobaidu;

import edu.auburn.bzl0048.metrobaidu.dao.PasswordDao;
import edu.auburn.bzl0048.metrobaidu.password.BzlPassword;
import edu.auburn.bzl0048.metrobaidu.vo.passwordVO.PasswordVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

public class PasswordApplication {

    public static List<PasswordVO> passwordVOList;
    public static PasswordVO resultPasswordVO;
    public static String resultPwd;

    public static void main(String[] args) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost/password");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("");

        DataSource dataSource = driverManagerDataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        PasswordDao passwordDao = new PasswordDao(jdbcTemplate);
        passwordVOList = passwordDao.getAll();
        passwordVOList.forEach(passwordVO -> {
            Runnable runnable = new BzlPassword(
                    passwordVO.getThreadName(),
                    passwordVO.getCounter(),
                    passwordVO.getMaxCounter(),
                    30,
                    5);
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }
}
