package in.fssa.vanha.model;

public abstract class UserEntity implements Comparable<UserEntity> {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifieddAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifieddAt) {
		this.modifiedAt = modifieddAt;
	}

	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", email=" + email + ", number=" + number + ", location=" + location + ", image=" + image + "]";
	}

	int id;
	String name;
	String email;
	String password;
	long number;
	String location;
	String image;
	boolean status = true;
	String createdAt;
	String modifiedAt;

}