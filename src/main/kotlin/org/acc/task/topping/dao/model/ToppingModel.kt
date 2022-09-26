package org.acc.task.topping.dao.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class ToppingModel(

    @Id
    @Column(length = 100, unique = true, nullable = false)
    val name: String
) {

    @ManyToMany(mappedBy = "likedToppings")
    var customers: Set<CustomerModel> = HashSet()

}
