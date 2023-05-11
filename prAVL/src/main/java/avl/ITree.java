package avl;

public interface ITree<T>{

    void insert(T item);

    AvlNode<T> getTop();

    boolean avlIsEmpty();

    void delete(T item);

    int size();

    AvlNode<T> search(T item);
}
