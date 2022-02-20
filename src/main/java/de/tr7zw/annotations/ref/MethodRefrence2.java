package de.tr7zw.annotations.ref;

import java.io.Serializable;

public interface MethodRefrence2<T, Z> extends Serializable {
	void callable(T obj, Z obj2);
}