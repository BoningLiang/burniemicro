package edu.auburn.bzl0048.metrobaidu.dao;


import edu.auburn.bzl0048.metrobaidu.vo.passwordVO.PasswordVO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

public class PasswordDao {

    private JdbcTemplate jdbcTemplate;

    public PasswordDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(PasswordVO passwordVO) {
        String sql = "INSERT INTO mycounter(id,thread_name,counter) VALUES(?,?,?,?)";
        return jdbcTemplate.update(sql, passwordVO.getId(), passwordVO.getThreadName(), passwordVO.getCounter(), passwordVO.getMaxCounter());
    }

    public int update(PasswordVO passwordVO) {
        String sql = "UPDATE mycounter SET counter=? WHERE thread_name=?";
        return jdbcTemplate.update(sql, passwordVO.getCounter(), passwordVO.getThreadName());
    }

    public PasswordVO getByThreadName(String threadName) {
        String sql = "SELECT id, thread_name, counter, max_counter FROM mycounter WHERE thread_name=?";
        return jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNumber)->{
            PasswordVO passwordVO = new PasswordVO();
            passwordVO.setId(rs.getInt("id"));
            passwordVO.setThreadName(rs.getString("thread_name"));
            passwordVO.setCounter(rs.getInt("counter"));
            passwordVO.setMaxCounter(rs.getInt("max_counter"));
            return passwordVO;
        }, threadName);
    }

    public List<PasswordVO> getAll() {
        String sql = "select id, thread_name, counter, max_counter from mycounter";
        List<PasswordVO> passwordVOList = jdbcTemplate.query(sql, (resultSet, rowNumber)->{
            PasswordVO passwordVO = new PasswordVO();
            passwordVO.setId(resultSet.getInt("id"));
            passwordVO.setThreadName(resultSet.getString("thread_name"));
            passwordVO.setCounter(resultSet.getInt("counter"));
            passwordVO.setMaxCounter(resultSet.getInt("max_counter"));
            return passwordVO;
        });
        return passwordVOList;
    }

}
