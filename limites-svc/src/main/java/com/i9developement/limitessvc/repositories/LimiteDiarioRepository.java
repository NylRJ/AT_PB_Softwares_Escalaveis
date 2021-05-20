package com.i9developement.limitessvc.repositories;

import com.i9developement.limitessvc.domain.models.LimiteDiario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LimiteDiarioRepository extends CrudRepository<LimiteDiario, Long> {

    LimiteDiario findByAgenciaAndContaAndData(final Long agencia, final Long conta, final LocalDate data);
}
