package br.com.roner.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.roner.rest.model.User;
import br.com.roner.rest.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * User service class
 *
 * @author Roner Dâmaso Junior
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	/**
	 * Method to find all users
	 *
	 * @param page
	 * @param size
	 * @return {@link Page}
	 */
	public Page<User> findAll(int page, int size) {
		return repository.findAll(new PageRequest(page, size));
	}

	/**
	 * Method to find user by id
	 *
	 * @param userId
	 * @return {@link User}
	 */
	public User findById(Long userId) {
		return repository.findOne(userId);
	}

	/**
	 * Method to save user
	 *
	 * @param userId
	 * @return {@link User}
	 */
	public User saveUser(User user) {
		return repository.save(user);
	}

	/**
	 * Method to delete user
	 *
	 * @param user
	 *            {@link User}
	 */
	public void deleteUser(User user) {
		repository.delete(user.getId());
	}

	/**
	 * Method to validate user passed on request body, if it doesn't, returns
	 * null.
	 *
	 * @param params
	 * @return {@link User}
	 * @throws Exception
	 */
	public User validateUser(ObjectNode params) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		if (params.has("user")) {
			user = mapper.convertValue(params.get("user"), User.class);
		}
		if (StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getEmail())) {
			return null;
		}
		return user;
	}
}
