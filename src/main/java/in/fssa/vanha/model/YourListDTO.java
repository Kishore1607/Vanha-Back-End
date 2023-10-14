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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "YourListDTO [image=" + image + ", name=" + name + ", productId=" + productId + ", status=" + status
				+ "]";
	}

	String image;
	String name;
	String productId;
	String status;

}
