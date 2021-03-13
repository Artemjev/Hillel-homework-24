package com.hillel.artemjev.homework24.util.jdbcquery;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
public class JdbcTemplate {
    private final DataSource dataSource;

    public <T> List<T> query(String sql, Object[] params, RowMapper<T> mapper) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            ResultSet rs = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                T o = mapper.map(rs);
                result.add(o);
            }
            connection.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    public <T> List<T> query(String sql, RowMapper<T> mapper) {
        return query(
                sql,
                new Object[]{},
                mapper
        );
    }

    public <T> T queryOne(String sql, Object[] params, RowMapper<T> mapper) {
        List<T> res = query(
                sql + " LIMIT 1",
                params,
                mapper
        );
        // хз что тут вместо null можно возвращать, если выборка пустая (думал Optional - не по условию задачи).
        return res.isEmpty() ? null : res.get(0);
    }

    public <T> T queryOne(String sql, RowMapper<T> mapper) {
        return queryOne(
                sql,
                new Object[]{},
                mapper
        );
    }

    public void update(String sql, Object[] params) {
        try {
            Connection connection = dataSource.getConnection();
            // можно было вынести 4 следующие строки в отдельный приватный метод getStatement(...) - не стал.
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.execute();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(String sql) {
        update(
                sql,
                new Object[]{}
        );
    }
}
