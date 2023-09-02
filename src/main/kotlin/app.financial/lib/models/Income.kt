package app.financial.lib.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.sql.Timestamp

@Table
@Entity
open class Income (
    @Column(name = "amount", nullable = false)
    open var amount: BigDecimal,

    @Column(name = "type_id", nullable = false)
    open var typeId: Long = 1
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    open var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    open lateinit var user: User

    @Column(name = "description")
    open var description: String = ""

    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    open val updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
}