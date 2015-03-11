package ch.epfl.imhof.osm;

import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Represents an <code>OSMRelation</code> with an identits (<code>id</code>), an
 * {@link Attributes} and a {@link List} of {@link OSMRelation.Member}.
 * 
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMRelation extends OSMEntity {
	private final List<Member> members;

	/**
	 * Construct a new <code>OSMRelation</code> given its identification,
	 * <code>atributes</code> and <code>members</code>.
	 * 
	 * @param id
	 *            <code>OSMRelation</code>'s identification.
	 * @param members
	 *            <code>OSMRelation</code>'s members ({@link OSMRelation.Member}
	 *            ).
	 * @param attributes
	 *            <code>OSMRelation</code>'s attributes ({@link Attributes}).
	 */
	public OSMRelation(long id, List<Member> members, Attributes attributes) {
		super(id, attributes);
		this.members = members;
	}

	/**
	 * Returns a <code>List</code> containing all the <code>Member</code> in the
	 * <code>OSMRelation</code>.
	 * 
	 * @return the field <code>members</code>.
	 */
	public List<Member> members() {
		return members;
	}

	/**
	 * A class that represents a member of an {@link OSMRelation}. Each member
	 * is an {@link OSMEntity} whiths a type and a role.
	 * 
	 * @author Matteo Besançon (245826)
	 *
	 */
	public final static class Member {
		private final Type type;
		private final String role;
		private final OSMEntity member;

		/**
		 * Constructs a new <code>Member</code> instance given its type, role
		 * and an <code>OSMEntity</code>.
		 * 
		 * @param type
		 *            <code>Member</code>'s type.
		 * 
		 * @param role
		 *            <code>Member</code>'s role.
		 * @param member
		 *            <code>Member</code>'s <code>OSMEntity</code>.
		 */
		public Member(Type type, String role, OSMEntity member) {
			this.type = type;
			this.role = role;
			this.member = member;
		}

		/**
		 * Returns the type of a <code>Member</code>.
		 * 
		 * @return the <code>Member</code>'s field <code>type</code>.
		 */
		public Type type() {
			return type;
		}

		/**
		 * Returns the role of a <code>Member</code>.
		 * 
		 * @return the <code>Member</code>'s field <code>role</code>.
		 */
		public String role() {
			return role;
		}

		/**
		 * Returns the <code>OSMEntity</code> of a <code>Member</code>.
		 * 
		 * @return the <code>Member</code>'s field <code>member</code>.
		 */
		public OSMEntity member() {
			return member;
		}

		/**
		 * An enumeration of all the posible <code>type</code> fot
		 * {@link Members}.
		 * 
		 * @author Matteo Besançon (245826)
		 *
		 */
		public enum Type {
			NODE, WAY, RELATION
		}
	}

	/**
	 * A class that helps in the construction of {@link OSMRelation}.
	 * 
	 * @author Matteo Besançon (245826)
	 *
	 */
	public final static class Builder extends OSMEntity.Builder {
		private List<Member> members;

		/**
		 * Constructs an <code>OSMRelation.Builder</code> with the
		 * <code>id</code> of the futuree <code>OSMRelation</code>.
		 * 
		 * @param id
		 *            the future <code>OSMRelation</code>'s identification.
		 */
		public Builder(long id) {
			super(id);
		}

		/**
		 * Adds a new Member to the <code>OSMRelation</code>.
		 * 
		 * @param type
		 *            new <code>member</code>'s type.
		 * @param role
		 *            new <code>member</code>'s role ({@link String}).
		 * @param newMember
		 *            <code>member</code>'s <code>OSMRelation</code>.
		 */
		public void addMember(Member.Type type, String role, OSMEntity newMember) {
			members.add(new Member(type, role, newMember));
		}

		/**
		 * Constructs a new <code>OSMRelation</code> instance using the data
		 * provided by the <code>OSMRelation.Builder</code>.
		 * 
		 * @return the new <code>OSMRelation</code>.
		 * 
		 * @throws IllegalStateException
		 *             if the <code>OSMRelation</code> is not complete (
		 *             <code>incomplete = true</code>).
		 */
		public OSMRelation build() throws IllegalStateException {
			if (incomplete) {
				throw new IllegalStateException(
						"The OSMRelation is not complete yet.");
			}

			return new OSMRelation(this.id, this.members,
					this.attributes.build());
		}

	}

}
