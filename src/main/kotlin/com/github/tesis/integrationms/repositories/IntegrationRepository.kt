package com.github.tesis.integrationms.repositories

import com.github.tesis.integrationms.domain.entities.Integration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface IntegrationRepository : JpaRepository<Integration, Long> {
    fun getByUserCode(userCode: UUID): List<Integration>

    fun getByCodeAndUserCode(code: UUID, userCode: UUID): Optional<Integration>

    @Modifying
    @Query("delete from Integration t where t.code = :code")
    fun deleteByCode(code: UUID)
}
