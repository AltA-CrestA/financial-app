package app.financial.lib.repository;

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import app.financial.lib.models.Income

@Repository
interface IncomeRepository : CrudRepository<Income, Long> {
}