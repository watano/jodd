// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.petite;

import jodd.petite.scope.Scope;
import jodd.util.ArraysUtil;

/**
 * Petite bean definition and cache. Consist of bean data that defines a bean
 * and cache, that might not be initialized (if <code>null</code>).
 * To initialize cache, get the bean instance from container.
 */
public class BeanDefinition {

	public BeanDefinition(String name, Class type, Scope scope, WiringMode wiringMode) {
		this.name = name;
		this.type = type;
		this.scope = scope;
		this.wiringMode = wiringMode;
	}

	// finals
	protected final String name;		// bean name
	protected final Class type;			// bean type
	protected final Scope scope;  		// bean scope, may be null for beans that are not stored in scope but just wired
	protected final WiringMode wiringMode;	// wiring mode

	// cache
	protected CtorInjectionPoint ctor;
	protected PropertyInjectionPoint[] properties;
	protected SetInjectionPoint[] sets;
	protected MethodInjectionPoint[] methods;
	protected InitMethodPoint[] initMethods;
	protected String[] params;

	// ---------------------------------------------------------------- definition getters

	/**
	 * Returns bean name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns bean type.
	 */
	public Class getType() {
		return type;
	}

	/**
	 * Returns beans scope type.
	 */
	public Class<? extends Scope> getScope() {
		if (scope == null) {
			return null;
		}
		return scope.getClass();
	}

	/**
	 * Returns wiring mode.
	 */
	public WiringMode getWiringMode() {
		return wiringMode;
	}

	// ---------------------------------------------------------------- cache getters

	/**
	 * Returns constructor injection point.
	 */
	public CtorInjectionPoint getCtorInjectionPoint() {
		return ctor;
	}

	/**
	 * Returns property injection points.
	 */
	public PropertyInjectionPoint[] getPropertyInjectionPoints() {
		return properties;
	}

	/**
	 * Returns set injection points.
	 */
	public SetInjectionPoint[] getSetInjectionPoints() {
		return sets;
	}

	/**
	 * Returns method injection points.
	 */
	public MethodInjectionPoint[] getMethodInjectionPoints() {
		return methods;
	}

	/**
	 * Returns init method points.
	 */
	public InitMethodPoint[] getInitMethodPoints() {
		return initMethods;
	}

	/**
	 * Returns parameters.
	 */
	public String[] getParams() {
		return params;
	}

	// ---------------------------------------------------------------- scope delegates

	/**
	 * Delegates to {@link jodd.petite.scope.Scope#lookup(String)}. 
	 */
	protected Object scopeLookup() {
		return scope.lookup(name);
	}

	/**
	 * Delegates to {@link jodd.petite.scope.Scope#register(String, Object)}.
	 */
	protected void scopeRegister(Object object) {
		scope.register(name, object);
	}

	/**
	 * Delegates to {@link jodd.petite.scope.Scope#remove(String)}. 
	 */
	protected void scopeRemove() {
		scope.remove(name);
	}

	// ---------------------------------------------------------------- appends

	/**
	 * Adds property injection point.
	 */
	protected void addPropertyInjectionPoint(PropertyInjectionPoint pip) {
		if (properties == null) {
			properties = new PropertyInjectionPoint[1];
			properties[0] = pip;
		} else {
			properties = ArraysUtil.append(properties, pip);
		}
	}

	/**
	 * Adds set injection point.
	 */
	protected void addSetInjectionPoint(SetInjectionPoint sip) {
		if (sets == null) {
			sets = new SetInjectionPoint[1];
			sets[0] = sip;
		} else {
			sets = ArraysUtil.append(sets, sip);
		}
	}

	/**
	 * Adds method injection point.
	 */
	protected void addMethodInjectionPoint(MethodInjectionPoint mip) {
		if (methods == null) {
			methods = new MethodInjectionPoint[1];
			methods[0] = mip;
		} else {
			methods = ArraysUtil.append(methods, mip);
		}
	}

	/**
	 * Adds init methods.
	 */
	protected void addInitMethodPoints(InitMethodPoint[] methods) {
		if (initMethods == null) {
			initMethods = methods;
		} else {
			initMethods = ArraysUtil.join(initMethods, methods);
		}
	}

	// ---------------------------------------------------------------- toString

	@Override
	public String toString() {
		return "BeanDefinition{" +
				"name='" + name + '\'' +
				", type=" + type +
				", scope=" + scope +
				", wiring= " + wiringMode +
				'}';
	}
}