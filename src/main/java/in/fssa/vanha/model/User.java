package in.fssa.vanha.model;

public class User extends UserEntity {

	@Override
	public int compareTo(UserEntity o) {
		// TODO Auto-generated method stub
		if (o instanceof User) {
			User otherUser = (User) o;
			return Integer.compare(this.getId(), otherUser.getId());
		}
		return -1;
	}

}