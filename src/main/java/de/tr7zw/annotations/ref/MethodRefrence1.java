package de.tr7zw.annotations.ref;

import java.io.Serializable;

public interface MethodRefrence1<T> extends Serializable {
	void callable(T obj);
}