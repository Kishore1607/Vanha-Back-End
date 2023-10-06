package in.fssa.vanha.model;

public class YourListDTO {

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "YourListDTO [image=" + image + ", name=" + name + ", productId=" + productId + "]";
	}

	String image;
	String name;
	String productId;

}
