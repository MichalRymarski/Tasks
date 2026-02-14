package michalr.tasks.data.role

import jakarta.persistence.*

@Entity
@Table(name = "privilege")
class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    var name: String = ""

    @ManyToMany(mappedBy = "privileges")
    var roles: MutableSet<Role> = mutableSetOf()
}