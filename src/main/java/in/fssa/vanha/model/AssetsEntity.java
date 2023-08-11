package in.fssa.vanha.model;

public abstract class AssetsEntity implements Comparable<AssetsEntity> {
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	int id;
	String value;
}
