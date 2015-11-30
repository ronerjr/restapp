package br.com.roner.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.roner.rest.model.User;
import br.com.roner.rest.service.UserService;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * User controller class
 *
 * @author Roner Dâmaso Junior
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Method to list all users registered on repository with pagination, is
	 * optional pass "page" or "size" on request body, if these parameters
	 * didn't be passed this method will assume that page equals 0 and size
	 * equals 20.
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Page<User>> listAllUsers(@RequestBody ObjectNode params) {
		int page = 0;
		int size = 20;
		try {
			if (params.has("page")) {
				page = params.get("page").asInt();
			}
			if (params.has("size")) {
				size = params.get("size").asInt();
			}
			Page<User> users = userService.findAll(page, size);
			if (users.getContent().isEmpty()) {
				return new ResponseEntity<Page<User>>(HttpStatus.NOT_FOUND);
			} else {
				return ResponseEntity.ok(users);
			}
		} catch (Exception e) {
			return new ResponseEntity<Page<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to find user by id, is mandatory pass an id on url to search on
	 * repository.
	 *
	 * @param userId
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> findUserById(@PathVariable Long userId) {
		try {
			User user = userService.findById(userId);
			if (user != null) {
				return ResponseEntity.ok(user);
			} else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to insert user, is mandatory pass an user object on request body.
	 *
	 * @param params
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> insertUser(@RequestBody ObjectNode params) {
		try {
			User user = userService.validateUser(params);
			if (user == null) {
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			userService.saveUser(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to update user, is mandatory pass an id on url and an user object
	 * on request body.
	 *
	 * @param params
	 * @param userId
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> updateUser(@RequestBody ObjectNode params, @PathVariable Long userId) {
		try {
			User userFound = userService.findById(userId);
			System.out.println(userFound);
			if (userFound == null) {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			User user = userService.validateUser(params);
			if (user == null) {
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			user.setId(userFound.getId());
			user = userService.saveUser(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to delete user, is mandatory pass an id on url.
	 *
	 * @param userId
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
		try {
			User userFound = userService.findById(userId);
			if (userFound == null) {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			userService.deleteUser(userFound);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
