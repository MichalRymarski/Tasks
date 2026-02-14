package michalr.tasks.data.role

import jakarta.persistence.*

@Entity
@Table(name = "role")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    var name: String = ""
    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<TaskUser> = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privileges: MutableSet<Privilege> = mutableSetOf()
}