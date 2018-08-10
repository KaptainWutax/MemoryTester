package kaptainwutax.memorytester.data;

import net.minecraft.client.Minecraft;

public class DataUnit<T> {

	private String name;
	private String type;
	private T defaultValue;
	private T value;
	
	public DataUnit(String name, String type, T defaultValue) {
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.value = this.defaultValue;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public T getDefaultValue() {
		return this.defaultValue;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "DataUnit{\"" + this.name + "\", " + this.defaultValue + ", " + this.value + "}";
	}
	
}
