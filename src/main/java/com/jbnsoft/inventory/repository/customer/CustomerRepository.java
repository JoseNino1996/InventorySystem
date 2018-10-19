package com.jbnsoft.inventory.repository.customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {


    Customer findByName(String name);

}
