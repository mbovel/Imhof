/**
 * @author Matteo Besançon (245826)
 */
package ch.epfl.imhof.osm;

import java.util.List;

import ch.epfl.imhof.Attributes;

public final class OSMRelation extends OSMEntity {
	private final List<Member> members;

	public OSMRelation(long id, List<Member> members, Attributes attributes) {
		super(id, attributes);
		this.members = members;
	}

	public List<Member> mebers() {
		return members;
	}

	public final static class Member {
		private final Type type;
		private final String role;
		private final OSMEntity member;

		public Member(Type type, String role, OSMEntity member) {
			this.type = type;
			this.role = role;
			this.member = member;
		}

		public Type type() {
			return type;
		}

		public String role() {
			return role;
		}

		public OSMEntity member() {
			return member;
		}

		public enum Type {
			NODE, WAY, RELATION
		}
	}

	public final static class Builder extends OSMEntity.Builder {
		private List<Member> members;

		public Builder(long id) {
			super(id);
		}

		public void addMember(Member.Type type, String role, OSMEntity newMember) {
			members.add(new Member(type, role, newMember));
		}

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
