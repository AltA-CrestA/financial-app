package app.financial.lib.models

import app.financial.enums.StepCode
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "users")
open class User(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    open val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "step_code", nullable = true)
    open var stepCode: StepCode,
) {
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @JoinColumn(name = "user_id")
    @OneToMany(targetEntity = Income::class, fetch = FetchType.EAGER)
    open lateinit var incomeList: MutableList<Income>
}