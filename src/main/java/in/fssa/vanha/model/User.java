package in.fssa.vanha.model;

public class User extends UserEntity {

	/**
	 * Compares this User to another UserEntity based on their IDs.
	 *
	 * This method compares the current User instance with another UserEntity object
	 * and returns a value indicating their relative order. The comparison is based
	 * on the IDs of the User objects. If the provided object is not a User
	 * instance, it is considered less than the current User.
	 *
	 * @param o The UserEntity to compare to.
	 * @return A negative integer, zero, or a positive integer as this User is less
	 *         than, equal to, or greater than the specified UserEntity.
	 */
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