package org.acc.task.topping.dao.repository

import org.acc.task.topping.dao.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<CustomerModel, String>