package com.pkcwong.react;

import java.util.ArrayList;
import java.util.List;

public class ReactiveVar {

	private Object value;
	private List<Reactivity> trackers;

	public ReactiveVar() {
		this(null);
	}

	public ReactiveVar(Object value) {
		this.value = value;
		this.trackers = new ArrayList<>();
	}

	void withTracker(Reactivity tracker) {
		this.trackers.add(tracker);
		tracker.forceUpdate();
	}

	/**
	 * Sets the ReactiveVar value
	 * @param value value
	 */
	public void set(Object value) {
		this.value = value;
		for (Reactivity tracker : this.trackers) {
			tracker.forceUpdate();
		}
	}

	/**
	 * Gets the ReactiveVar value
	 * @return Object value
	 */
	public Object get() {
		return this.value;
	}

}
