package app.financial.lib.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import app.financial.lib.models.User

@Repository
interface UserRepository : CrudRepository<User, Long>