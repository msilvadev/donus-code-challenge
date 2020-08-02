package br.com.donuscodechallenge.repositories;

import br.com.donuscodechallenge.entities.AccountHistoric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHistoricRepository extends CrudRepository<AccountHistoric, Long> {
}
