package com.pkcwong.react;

public interface Redux<T> {

	/***
	 * Renders a component
	 * @param view target view
	 * @param prop props with ReactiveVar values decoded
	 * @param state states
	 */
	void render(T view, Component.Prop prop, Component.State state);

}
