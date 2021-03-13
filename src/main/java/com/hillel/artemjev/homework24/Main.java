package com.hillel.artemjev.homework24;

import com.hillel.artemjev.homework24.entities.User;
import com.hillel.artemjev.homework24.util.jdbcquery.BeanPropertyRowMapper;
import com.hillel.artemjev.homework24.util.jdbcquery.JdbcTemplate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String dsn = "jdbc:postgresql://localhost:5432/contactbook";
        String user = "postgres";
        String password = "0000";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dsn);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(8);
        config.setMinimumIdle(4);
        DataSource dataSource = new HikariDataSource(config);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //----------------------------------------------------------------------------
        jdbcTemplate.update(
                "INSERT INTO users(login, password, date_born) VALUES(?,?,?)",
                new Object[]{
                        "Login-1",
                        "password-1",
                        LocalDate.of(2001, 1, 1)
                }
        );
        jdbcTemplate.update(
                "INSERT INTO users(login, password, date_born) VALUES(?,?,?)",
                new Object[]{
                        "Login-2",
                        "password-2",
                        LocalDate.of(2003, 3, 3)
                }
        );
        jdbcTemplate.update(
                "INSERT INTO users(login, password, date_born) VALUES(?,?,?)",
                new Object[]{
                        "Login-3",
                        "password-3",
                        LocalDate.of(2003, 3, 3)
                }
        );
        printAllRows("INSERTED", jdbcTemplate);

        //----------------------------------------------------------------------------
        List<User> users = jdbcTemplate.query(
                "SELECT id, login, password, date_born FROM users",
                new BeanPropertyRowMapper<User>(User.class)
        );
        users.forEach(System.out::println);
        System.out.println("----------------------------------------------------------");

        //----------------------------------------------------------------------------
        users = jdbcTemplate.query(
                "SELECT id, login, password, date_born FROM users WHERE date_born=?",
                new Object[]{LocalDate.of(2003, 3, 3)},
                new BeanPropertyRowMapper<User>(User.class)
        );
        users.forEach(System.out::println);
        System.out.println("----------------------------------------------------------");

        //----------------------------------------------------------------------------
        User u = jdbcTemplate.queryOne(
                "SELECT id, login, password, date_born FROM users WHERE date_born=?",
                new Object[]{LocalDate.of(2003, 3, 3)},
                new BeanPropertyRowMapper<User>(User.class)
        );
        System.out.println(u);
        System.out.println("----------------------------------------------------------");

        //----------------------------------------------------------------------------
        u = jdbcTemplate.queryOne(
                "SELECT id, login, password, date_born FROM users",
                new BeanPropertyRowMapper<User>(User.class)
        );
        System.out.println(u);
        System.out.println("----------------------------------------------------------");

        //----------------------------------------------------------------------------
        jdbcTemplate.update(
                "UPDATE users SET login=?, date_born=? WHERE login=? AND date_born=?",
                new Object[]{
                        "Login-222",
                        LocalDate.of(2002, 2, 2),
                        "Login-2",
                        LocalDate.of(2003, 3, 3)
                }
        );
        printAllRows("UPDATED", jdbcTemplate);

        //----------------------------------------------------------------------------
        jdbcTemplate.update(
                "DELETE FROM users WHERE login=? AND date_born=?",
                new Object[]{
                        "Login-1",
                        LocalDate.of(2000, 1, 1)
                }
        );
        printAllRows("DELETED", jdbcTemplate);

        //----------------------------------------------------------------------------
        jdbcTemplate.update(
                "DELETE FROM users"
        );
        printAllRows("DELETED ALL", jdbcTemplate);
    }

    private static void printAllRows(String title, JdbcTemplate jdbcTemplate) {
        System.out.println(title);
        jdbcTemplate.query(
                "SELECT id, login, password, date_born FROM users",
                new BeanPropertyRowMapper<User>(User.class)
        ).forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
    }
}
