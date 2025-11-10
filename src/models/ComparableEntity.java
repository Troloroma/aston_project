package src.models;

public interface ComparableEntity extends Comparable<ComparableEntity> {
	int getId();

	@Override
	default int compareTo(ComparableEntity other) {
		return EntityComparator.DEFAULT.compare(this, other);
	}
}