package org.acc.task.topping.dao.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class CustomerModel(

    @Id
    @Column(length = 50, unique = true, nullable = false)
    val email: String
) {
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "topping_like",
        joinColumns = [JoinColumn(name = "customer_id")],
        inverseJoinColumns = [JoinColumn(name = "topping_id")]
    )
    var likedToppings: Set<ToppingModel> = HashSet()
}
