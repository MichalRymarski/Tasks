package michalr.tasks.data.role

import jakarta.persistence.*

@Entity
@Table(name = "user")
class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null
    @Column(name = "first_name", nullable = false)
    var firstName: String = ""
    @Column(name = "last_name", nullable = false)
    var lastName: String = ""
    @Column(name = "email", nullable = false)
    var email: String = ""
    @Column(name = "password", nullable = false)
    var password: String = ""
    var enabled = false
    var tokenExpired = false

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles : MutableSet<Role> = mutableSetOf()
}