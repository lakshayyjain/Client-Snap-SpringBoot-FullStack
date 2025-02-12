package com.lakshayjain.Customers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomer() {
        var sql = """
                SELECT id, name, email, age FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomer(Integer id) {
        var sql = """
                SELECT id, name, email, age FROM customer
                WHERE id = ?
                """;
        // we should not do this when we have sql queries with us ::
//        for(Customer customer : jdbcTemplate.query(sql, customerRowMapper)){
//            if(customer.getId().equals(id)){
//                return Optional.of(customer);
//            }
//        }
        return jdbcTemplate.query(sql,customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );

        System.out.println("jdbcTemplate.update = " + result); // this will give the number of rows in our DATABASE
    }

    @Override
    public boolean IsEmailAlreadyExists(String email) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomer(Integer id) {
        var sql = """
                DELETE FROM customer WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql,id);
        System.out.println("jdbcTemplate.update = " + result) ;
    }

    @Override
    public boolean IsExistsById(Integer id) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer update) {
        var sql = """
                UPDATE customer SET name = ?, email = ?, age = ? WHERE id = ?
                """;
        jdbcTemplate.update(sql,update.getName(),update.getEmail(),update.getAge(),update.getId());
    }
}
