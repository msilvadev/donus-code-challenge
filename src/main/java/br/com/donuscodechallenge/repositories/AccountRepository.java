package br.com.donuscodechallenge.repositories;

import br.com.donuscodechallenge.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
