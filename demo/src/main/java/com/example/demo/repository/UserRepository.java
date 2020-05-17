package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    /**
     * we are autowiring jdbc template
     * using the properties we have configured in application.properties spring
     * automatically detects and creates jdbc template object using the configuration
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * returns list of user names
     * @return usernameList
     */
    List<String> usernameList = new ArrayList<>();
    static String[] arr = {"test1 trial", "test2 trial", "test3 trial", "test4 trial", "test5 trial"};
    List<Object[]> splitUpNames = Arrays.asList(arr[0] ,arr[1], arr[2], arr[3], arr[4]).stream()
            .map(name -> name.split(" "))
            .collect(Collectors.toList());
    public List<String> getAllUserNames() {

        usernameList.addAll(jdbcTemplate.queryForList("SELECT first_name FROM user;", String.class));
        return usernameList;
    }

    public void insertAllUserNames() {
        jdbcTemplate.batchUpdate("INSERT INTO user(first_name, last_name) VALUES (?,?)", splitUpNames);
    }
}
