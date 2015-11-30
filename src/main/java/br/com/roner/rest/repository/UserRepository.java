package br.com.roner.rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.roner.rest.model.User;

/**
 * Interface of user repository
 *
 * @author Roner Dâmaso Junior
 *
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
