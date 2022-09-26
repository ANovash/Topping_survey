package org.acc.task.topping.dao.repository

import org.acc.task.topping.dao.model.ToppingModel
import org.springframework.data.repository.CrudRepository

interface ToppingRepository : CrudRepository<ToppingModel, String>