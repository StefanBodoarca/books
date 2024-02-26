package com.ro.repositories;

import com.ro.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final JdbcTemplate jdbc;

    public Account findAccountById(long id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        return jdbc.queryForObject(sql, new AccountRowMapper(), id);
    }

    public void changeAmount(long id, BigDecimal amount) {
        String sql = "update account set amount = ? where id = ?";
        jdbc.update(sql, amount, id);
    }

    public List<Account> findAllAccounts() {
        String sql = "select * from account";
        return jdbc.query(sql, new AccountRowMapper());
    }
}
