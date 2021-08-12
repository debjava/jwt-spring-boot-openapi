package com.ddlab.rnd.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ddlab.rnd.entity.User;
import com.ddlab.rnd.exception.NotFoundException;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	<S extends User> List<S> saveAll(Iterable<S> entities);

	<S extends User> S save(S entity);

	Optional<User> findById(ObjectId objectId);

	default User getById(ObjectId id) {
		Optional<User> optionalUser = findById(id);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException(User.class, id);
		}
		if (!optionalUser.get().isEnabled()) {
			throw new NotFoundException(User.class, id);
		}
		return optionalUser.get();
	}

	Optional<User> findByUsername(String username);

}
