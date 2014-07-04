package com.pwnscone.pewpew.util;

import java.util.ArrayList;

public class Pool<E> extends ArrayList<E> {
	public final Class clazz;
	public int fill = 0;

	public Pool(Class clazz) {
		this.clazz = clazz;
	}

	public E add() {
		if (fill == size()) {
			try {
				add((E) clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		((Poolable) get(fill)).index = fill++;
		return (E) get(fill - 1);
	}

	public void remove(Poolable removed) {
		E oldLast = this.get(fill - 1);
		set(removed.index, oldLast);
		set(fill - 1, (E) removed);
		((Poolable) oldLast).index = (removed).index;
		removed.index = fill - 1;
		fill--;
	}
}
