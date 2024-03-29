package com.ddlab.rnd.exception;

import org.bson.types.ObjectId;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4700628269616582047L;

	public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> clazz, long id) {
        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, String id) {
        super(String.format("Entity %s with id %s not found", clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, ObjectId id) {
        super(String.format("Entity %s with id %s not found", clazz.getSimpleName(), id.toString()));
    }

}
