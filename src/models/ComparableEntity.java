package src.models;

public interface ComparableEntity extends Comparable<ComparableEntity> {
    @Override
    default int compareTo(ComparableEntity other) {
        return EntityComparator.DEFAULT.compare(this, other);
    }
}