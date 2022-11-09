package controller;

/**
 * Encapsulates a request as an object, thereby letting us parameterize other
 * objects with different requests, queue or log requests, and support undoable
 * operations
 */
public interface Command<E> {
    /**
     * The method that will invoke a method to perform
     * an action.
     * 
     * @return the type declared by the child.
     */
    E execute();

    /**
     * The method that will undo the executed action, if any.
     * 
     * @throws UnsupportedOperationException if called without an implementation.
     */
    default void undo() {
        throw new UnsupportedOperationException();
    }
}
