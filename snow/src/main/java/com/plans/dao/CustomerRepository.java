package com.plans.dao;

/**
 * Created by river on 2016/3/24.
 */

import java.util.List;

import com.plans.entity.Customer;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Query("Select * from journey where name=?0 allow filtering")
    List<Customer> findJourneyByName(String name);

    @Query("Select * from journey")
    List<Customer> findAllJourneys();
}
