package com.pkcwong.react;

public interface Reactivity {

	void withTracker(ReactiveVar var);
	void forceUpdate();

}
