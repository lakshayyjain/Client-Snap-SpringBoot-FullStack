package com.lakshayjain.Customers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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
                SELECT id, name, email, password, age, gender FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomer(Integer id) {
        var sql = """
                SELECT id, name, email, password, age, gender FROM customer
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
                INSERT INTO customer(name, email, password, age, gender)
                VALUES(?,?,?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                new Object[]{
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPassword(),
                        customer.getAge(),
                        customer.getGender().name()  // explicit string representation of enum
                },
                new int[]{
                        Types.VARCHAR,  // name
                        Types.VARCHAR,  // email
                        Types.VARCHAR,
                        Types.INTEGER,  // age
                        Types.VARCHAR   // gender explicitly specified as VARCHAR
                }
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
                UPDATE customer SET name = ?, email = ?, age = ?, gender = ? WHERE id = ?
                """;
        jdbcTemplate.update(
                sql,
                new Object[]{
                        update.getName(),
                        update.getEmail(),
                        update.getAge(),
                        update.getGender().name(),  // explicitly convert enum to string
                        update.getId()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.INTEGER,
                        Types.VARCHAR, // explicit type for enum
                        Types.INTEGER
                }
        );

    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {
        var sql = """
                SELECT id, name, email, password, age, gender FROM customer
                WHERE email = ?
                """;
        // we should not do this when we have sql queries with us ::
//        for(Customer customer : jdbcTemplate.query(sql, customerRowMapper)){
//            if(customer.getId().equals(id)){
//                return Optional.of(customer);
//            }
//        }
        return jdbcTemplate.query(sql,customerRowMapper, email)
                .stream()
                .findFirst();    }

}
