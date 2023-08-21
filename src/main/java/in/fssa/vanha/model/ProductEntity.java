package in.fssa.vanha.model;

public abstract class ProductEntity implements Comparable<ProductEntity>{ 
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String  i) {
		this.category = i;
	}
	public int getUsedPeriod() {
		return usedPeriod;
	}
	public void setUsedPeriod(int used_period) {
		this.usedPeriod = used_period;
	}
	public String getUsedDuration() {
		return usedDuration;
	}
	public void setUsedDuration(String used_duration) {
		this.usedDuration = used_duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int seller_id) {
		this.sellerId = seller_id;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}	
	public String getSellerUnique() {
		return userUnique;
	}
	public void setSellerUnique(String userUnique) {
		this.userUnique = userUnique;
	}

	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", productId=" + productId + ", category=" + category + ", usedPeriod="
				+ usedPeriod + ", usedDuration=" + usedDuration + ", description=" + description + ", name=" + name
				+ ", price=" + price + ", minPrice=" + minPrice + ", sellerId=" + sellerId + ", createdAt=" + createdAt 
				+ ", modifiedAt=" + modifiedAt + "]";
	}
	int id;
    String productId;
    String category;
    int usedPeriod;
    String usedDuration;
    String description;
    String name;
    int price;
    int minPrice;
    int sellerId;
    char status;
    String createdAt;
    String modifiedAt; 
    String userUnique;    

}
