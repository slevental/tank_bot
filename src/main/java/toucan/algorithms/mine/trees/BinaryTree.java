package toucan.algorithms.mine.trees;

/**
 * Implementation of binary tree
 */
public class BinaryTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    public void put(K key, V value) {
        if (root != null) {
            put(root, key, value);
        } else {
            root = new Node<K, V>(key, value);
            incSize();
        }
    }

    private void put(Node<K, V> tree, K key, V value) {
        int compareResult = tree.getKey().compareTo(key);
        if (compareResult == 0) {
            tree.setValue(value);
        } else if (compareResult > 0) {
            if (tree.getLeft() == null) {
                tree.setLeft(new Node<K, V>(key, value));
                incSize();
            } else {
                put(tree.getLeft(), key, value);
            }
        } else if (compareResult < 0) {
            if (tree.getRight() == null) {
                tree.setRight(new Node<K, V>(key, value));
                incSize();
            } else {
                put(tree.getRight(), key, value);
            }
        }
    }

    private void incSize() {
        size++;
    }

    private void decSize() {
        size--;
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node<K, V> tree, K key) {
        int compareResult = tree.getKey().compareTo(key);
        if (compareResult == 0) {
            return tree.getValue();
        } else if (compareResult > 0) {
            if (tree.getLeft() == null) {
                return null;
            } else {
                return get(tree.getLeft(), key);
            }
        } else {
            if (tree.getRight() == null) {
                return null;
            } else {
                return get(tree.getRight(), key);
            }
        }
    }

    public void delete(K key) {
        delete(null, root, key, false);
    }

    private void delete(Node<K, V> parent, Node<K, V> tree, K key, boolean isLeft) {
        int compareResult = tree.getKey().compareTo(key);
        if (compareResult == 0) {
            if (tree.isLeaf()) {
                setNewChild(parent, null, isLeft);
            } else if (tree.hasOneChild()) {
                setNewChild(parent, tree.getOneChild(), isLeft);
            } else if (tree.hasBothChild()) {
                Node<K, V> leftChild = cutMostLeftElement(tree, tree.getRight(), isLeft);
                setNewChild(parent, leftChild, isLeft);
                if(!tree.getLeft().equals(leftChild)){
                    leftChild.setLeft(tree.getLeft());
                }
                leftChild.setRight(tree.getRight());
            } else {
                throw new IllegalStateException("Illegal node state : not a leaf, has not a one and not a two children");
            }
            decSize();
        } else if (compareResult > 0 && tree.getLeft() != null) {
            delete(tree, tree.getLeft(), key, true);
        } else if (compareResult < 0 && tree.getRight() != null) {
            delete(tree, tree.getRight(), key, false);
        }
    }

    private void setNewChild(Node<K, V> parent, Node<K, V> child, boolean isLeft) {
        if (parent == null) {
            root = child;
        } else {
            parent.setChild(child, isLeft);
        }
    }

    private Node<K, V> cutMostLeftElement(Node<K, V> parent, Node<K, V> tree, boolean isLeft) {
        if (tree.isLeaf()) {
            setNewChild(parent, null, isLeft);
            return tree;
        }else if (tree.hasOneChild() && tree.getLeft() == null) {
            setNewChild(parent, tree.getRight(), isLeft);
            return tree;
        }
        return cutMostLeftElement(tree,  tree.getLeft(), true);
    }

    private static class Node<K, V> {
        private K key;
        private V value;

        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasOneChild() {
            return (left != null && right == null) || (right != null && left == null);
        }

        public Node<K, V> getOneChild() {
            if (!hasOneChild()) {
                throw new IllegalStateException("Has more than one child");
            }
            return left == null ? right : left;
        }

        public boolean hasBothChild() {
            return left != null && right != null;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public void setLeft(Node<K, V> left) {
            this.left = left;
        }

        public void setChild(Node<K, V> child, boolean isLeft) {
            if (isLeft) {
                left = child;
            } else {
                right = child;
            }
        }

        public Node<K, V> getRight() {
            return right;
        }

        public void setRight(Node<K, V> right) {
            this.right = right;
        }
    }
}
