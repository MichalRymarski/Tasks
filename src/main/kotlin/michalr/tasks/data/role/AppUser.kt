package michalr.tasks.data.role

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class AppUser {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_role",
        joinColumns = [JoinColumn(name = "app_user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles : MutableSet<Role> = mutableSetOf()
}