package com.pkcwong.react;

import java.util.HashMap;
import java.util.Map;

public class Component<T> implements Reactivity {

	private T view;
	private Prop props;
	private State states;
	private Redux<T> refresher;

	/**
	 * Creates a new component
	 * @param view target view
	 */
	public Component(T view) {
		this.view = view;
		this.props = new Prop(this);
		this.states = new State(this);
	}

	/**
	 * Sets the initial prop of a component
	 * @param key key
	 * @param value ReactiveVar or any Object
	 * @return component instance
	 */
	public Component<T> prop(String key, Object value) {
		this.props.props.put(key, value);
		if (value instanceof ReactiveVar) {
			this.withTracker((ReactiveVar) value);
		}
		return this;
	}

	/**
	 * Sets the initial state of a component
	 * @param key key
	 * @param value any Object
	 * @return component instance
	 */
	public Component<T> state(String key, Object value) {
		this.states.states.put(key, value);
		return this;
	}

	/**
	 * Sets the render method and renders the component
	 * @param redux render method
	 * @return component instance
	 */
	public Component<T> build(Redux<T> redux) {
		this.refresher = redux;
		this.forceUpdate();
		return this;
	}

	/**
	 * Subscribes to a ReactiveVar
	 * @param var ReactiveVar
	 */
	@Override
	public void withTracker(ReactiveVar var) {
		var.withTracker(this);
	}

	/**
	 * Forces a component re-render
	 */
	@Override
	public void forceUpdate() {
		if (this.refresher != null) {
			this.refresher.render(this.view, this.props.decode(), this.states);
		}
	}

	public class Prop {

		private Component component;
		private Map<String, Object> props;

		private Prop(Component component) {
			this.component = component;
			this.props = new HashMap<>();
		}

		private Prop decode() {
			Prop temp = new Prop(this.component);
			for (Map.Entry<String, Object> entry : this.props.entrySet()) {
				if (entry.getValue() instanceof ReactiveVar) {
					temp.props.put(entry.getKey(), ((ReactiveVar) entry.getValue()).get());
				} else {
					temp.props.put(entry.getKey(), entry.getValue());
				}
			}
			return temp;
		}

		private void set(String key, Object value) {
			this.props.put(key, value);
			component.forceUpdate();
		}

		/**
		 * Gets the prop value
		 * @param key key
		 * @return Object value
		 */
		public Object get(String key) {
			return this.props.get(key);
		}

	}

	public class State {

		private Component component;
		private Map<String, Object> states;

		private State(Component component) {
			this.component = component;
			this.states = new HashMap<>();
		}

		/**
		 * Sets new state value
		 * @param key key
		 * @param value Object value
		 */
		public void set(String key, Object value) {
			this.states.put(key, value);
			component.forceUpdate();
		}

		/**
		 * Gets the state value
		 * @param key key
		 * @return Object Value
		 */
		public Object get(String key) {
			return this.states.get(key);
		}

	}

}
