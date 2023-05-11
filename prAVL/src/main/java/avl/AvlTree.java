//  AvlTree.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

package avl;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13 Time: 15:51 Class implementing
 * Avl trees.
 * Refactor made by:
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 */
public class AvlTree<T> {
  AvlNode<T> top;
  Comparator<T> comparator;

  /**
   * Constructor
   * @param comparator the comparator for ordering the elements of the tree
   */
  public AvlTree(Comparator<T> comparator) {
    top = null;
    this.comparator = comparator;
  }

  /**
   * Method to insert a new node given its value.
   * @param item value of node to insert
   * @see #insertTop(AvlNode) 
   * @see #insertAvlNode(AvlNode) 
   */
  public void insert(T item) {
    AvlNode<T> node = new AvlNode<>(item);
    insertAvlNode(node);
  }

  /**
   * Method to insert a node into the Tree.
   * @param node node to insert
   * @see #insert(Object)
   * @see #searchClosestNode(AvlNode)
   * @see #insertNodeLeft(AvlNode) 
   * @see #insertNodeRight(AvlNode)
   */
  void insertAvlNode(AvlNode<T> node) {
    if (avlIsEmpty()) insertTop(node);
    else switch (searchClosestNode(node)) {
      case -1 -> insertNodeLeft(node);
      case +1 -> insertNodeRight(node);
      default -> {
      }
    }
  }

  /**
   * Method to search for a node whose item is given.
   * @param item item of the node to search for
   * @return the found node, or {@code null}
   * @see #searchNode(AvlNode)
   */
  public AvlNode<T> search(T item) {
    AvlNode<T> node = new AvlNode<>(item);
    return searchNode(node);
  }

  /**
   * Method to search for a given node.
   * @param targetNode the node to search for
   * @return the found node, or {@code null}
   * @see #search(Object)
   */
  AvlNode<T> searchNode(AvlNode<T> targetNode) {
    AvlNode<T> currentNode;
    AvlNode<T> result = null;

    currentNode = top;
    if (top != null) {
      boolean searchFinished;
      int comparison;
      searchFinished = false;
      while (!searchFinished) {
        comparison = compareNodes(targetNode, currentNode);
        if (comparison < 0) {
          if (currentNode.getLeft() != null) currentNode = currentNode.getLeft();
          else searchFinished = true;
        } else if (comparison > 0) {
          if (currentNode.getRight() != null) currentNode = currentNode.getRight();
          else searchFinished = true;
        } else {
          searchFinished = true;
          result = currentNode;
        }
      }
    }
    return result;
  }

  /**
   * This method calculates the size of the tree.
   * @return the size of the tree
   * @see #treeSizeRec(AvlNode)
   */
  public int size(){
    return this.treeSizeRec(this.top);
  }

  /**
   * This auxiliary method calculates the size of the subtree whose top is the given node.
   * @param node the top of the subtree
   * @return the size of the subtree
   * @see #size()
   */
  private int treeSizeRec(AvlNode<T> node){
    return node != null && (node.hasLeft() || node.hasRight()) ?
            1 + treeSizeRec(node.getLeft()) + treeSizeRec(node.getRight()) : 0;
  }

  /**
   * This method deletes the node whose item is given.
   * @param item item of the node to delete
   * @see #deleteNode(AvlNode)
   */
  public void delete(T item) {
    deleteNode(new AvlNode<>(item));
  }

  /**
   * This method deletes the given item.
   * @param node the node to delete
   * @see #delete(Object)
   */
   void deleteNode(AvlNode<T> node) {
    AvlNode<T> nodeFound;

    nodeFound = searchNode(node);
    if (nodeFound != null) {
      if (nodeFound.isLeaf()) deleteLeafNode(nodeFound);
      else if (nodeFound.hasOnlyALeftChild()) deleteNodeWithALeftChild(nodeFound);
      else if (nodeFound.hasOnlyARightChild()) deleteNodeWithARightChild(nodeFound);
      else { // has two children
        AvlNode<T> successor = findSuccessor(nodeFound);
        T tmp = successor.getItem();
        successor.setItem(nodeFound.getItem());
        nodeFound.setItem(tmp);
        if (successor.isLeaf()) deleteLeafNode(successor);
        else if (successor.hasOnlyALeftChild()) deleteNodeWithALeftChild(successor);
        else if (successor.hasOnlyARightChild()) deleteNodeWithARightChild(successor);
      }
    } else {
      throw new NullPointerException("No se puede eliminar el nodo porque no existe");
    }
  }

  /**
   * This auxiliary method deletes the given leaf node.
   * @implNote This implementation asumes the given node is a <i>leaf node</i>.
   * @param node the leaf node to delete
   * @see #deleteNode(AvlNode)
   */
  void deleteLeafNode(AvlNode<T> node) {
    if (node.hasParent()) {
      if (node.getParent().getLeft() == node) node.getParent().setLeft(null);
      else node.getParent().setRight(null);
      node.getParent().updateHeight();
      rebalance(node.getParent());
    } else top = null;
  }

  /**
   * This auxiliary method deletes the given node with a left child.
   * @implNote This implementation asumes the given node has a <i>left child</i>.
   * @param node the node to delete
   * @see #deleteNode(AvlNode)
   * @see #deleteNodeWithARightChild(AvlNode)
   */
  void deleteNodeWithALeftChild(AvlNode<T> node) {
    node.setItem(node.getLeft().getItem());
    node.setLeft(null);
    node.updateHeight();
    rebalance(node);
  }

  /**
   * This auxiliary method deletes the given node with a right child.
   * @implNote This implementation asumes the given node has a <i>right child</i>.
   * @param node the node to delete
   * @see #deleteNode(AvlNode)
   * @see #deleteNodeWithALeftChild(AvlNode)
   */
  void deleteNodeWithARightChild(AvlNode<T> node) {
    node.setItem(node.getRight().getItem());
    node.setRight(null);
    node.updateHeight();
    rebalance(node);
  }

  /**
   * Searches for the closest node of the node passed as argument
   * @param node the node to search the closest for
   * @return -1 if node has to be inserted in the left,
   * +1 if it must be inserted in the right, 0 otherwise
   */
  public int searchClosestNode(AvlNode<T> node) {
    AvlNode<T> currentNode;
    int result = 0;

    currentNode = top;
    if (top != null) {
      int comparison;
      boolean notFound = true;
      while (notFound) {
        comparison = compareNodes(node, currentNode);
        if (comparison < 0) {
          if (currentNode.hasLeft()) currentNode = currentNode.getLeft();
          else {
            notFound = false;
            node.setClosestNode(currentNode);
            result = -1;
          }
        } else if (comparison > 0) {
          if (currentNode.hasRight()) currentNode = currentNode.getRight();
          else {
            notFound = false;
            node.setClosestNode(currentNode);
            result = 1;
          }
        } else {
          notFound = false;
          node.setClosestNode(currentNode);
        }
      }
    }

    return result;
  }

  /**
   * Searches for the node which is the next in numeric order
   * @param node the node from which to search
   * @return the node which is the next in numeric order
   */
  public AvlNode<T> findSuccessor(AvlNode<T> node) {
    AvlNode<T> result;

    if (node.hasRight()) {
      AvlNode<T> tmp = node.getRight();
      while (tmp.hasLeft()) tmp = tmp.getLeft();
      result = tmp;
    } else {
      while (node.hasParent() && (node.getParent().getRight() == node)) node = node.getParent();
      result = node.getParent();
    }
    return result;
  }

  /**
   * Insert node in the left of its nearest node
   *
   * @param node REQUIRES: a previous call to {@link #searchClosestNode(AvlNode)}
   */
  public void insertNodeLeft(AvlNode<T> node) {
    node.getClosestNode().setLeft(node);
    node.setParent(node.getClosestNode());
    rebalance(node);
  }

  /**
   * Insert node in the right of its nearest node
   *
   * @param node <b>REQUIRES:</b> a previous call to {@link #searchClosestNode(AvlNode)}
   */
  public void insertNodeRight(AvlNode<T> node) {
    node.getClosestNode().setRight(node);
    node.setParent(node.getClosestNode());
    rebalance(node);
  }

  /**
   * This method compares the two given nodes.
   * @param node1 the first node
   * @param node2 the other node
   * @return the output of the comparison according to the comparators
   */
  public int compareNodes(AvlNode<T> node1, AvlNode<T> node2) {
    return comparator.compare(node1.getItem(), node2.getItem());
  }

  /**
   * method for rebalancing tree
   * @param node to rebalance
   */
  public void rebalance(AvlNode<T> node) {
    AvlNode<T> currentNode;
    boolean notFinished;

    currentNode = node;
    notFinished = true;

    while (notFinished) {
      if (getBalance(currentNode) == -2) {
        if (height(currentNode.getLeft().getLeft()) >= height(currentNode.getLeft().getRight()))
          leftRotation(currentNode);
        else doubleLeftRotation(currentNode);
      }

      if (getBalance(currentNode) == 2) {
        if (height(currentNode.getRight().getRight()) >= height(currentNode.getRight().getLeft()))
          rightRotation(currentNode);
        else doubleRightRotation(currentNode);
      }

      if (currentNode.hasParent()) {
        currentNode.getParent().updateHeight();
        currentNode = currentNode.getParent();
      } else {
        setTop(currentNode);
        notFinished = false;
      }
    }
  }

  /**
   * Auxiliary method: given a root node, it rotates one position to left side.
   * @param node to rotate
   */
  void leftRotation(AvlNode<T> node) {
    AvlNode<T> leftNode = node.getLeft();

    if (node.hasParent()) {
      leftNode.setParent(node.getParent());
      if (node.getParent().getLeft() == node) node.getParent().setLeft(leftNode);
      else node.getParent().setRight(leftNode);
    } else {
      setTop(leftNode);
    }

    node.setLeft(node.getLeft().getRight());
    leftNode.setRight(node);
    node.setParent(leftNode);

    node.updateHeight();
    leftNode.updateHeight();
  }

  /**
   * Auxiliary method given a root node, it rotates one position to the right side
   * @param node to rotate
   */
  void rightRotation(AvlNode<T> node) {
    AvlNode<T> rightNode = node.getRight();

    if (node.hasParent()) {
      rightNode.setParent(node.getParent());
      if (node.getParent().getRight() == node) node.getParent().setRight(rightNode);
      else node.getParent().setLeft(rightNode);
    } else {
      //noinspection SuspiciousNameCombination
      setTop(rightNode);
    }

    node.setRight(node.getRight().getLeft());
    rightNode.setLeft(node);
    node.setParent(rightNode);

    node.updateHeight();
    rightNode.updateHeight();
  }

  /**
   * Auxiliary method: calling {@link #leftRotation(AvlNode)} twice
   * @param node to rotate
   */
  void doubleLeftRotation(AvlNode<T> node) {
    AvlNode<T> leftNode = node.getLeft();

    rightRotation(leftNode);
    leftRotation(node);
  }

  /**
   * Auxiliary method given a root node, it rotates two positions to the right side
   * @param node to rotate. REQUIRES: a call to {@link #rightRotation(AvlNode) and #leftRotation(AvlNode)}
   */
  void doubleRightRotation(AvlNode<T> node) {
    AvlNode<T> rightNode = node.getRight();

    leftRotation(rightNode);
    rightRotation(node);
  }

  /**
   * This method checks the balance of the subtree.
   * @param node the top of the subtree
   * @return the value representing the balance (-1 if leaning to the left,
   * +1 if leaning to the right, and 0 if balanced
   */
  int getBalance(AvlNode<T> node) {
    int leftHeight;
    int rightHeight;

    leftHeight = node.hasLeft() ? node.getLeft().getHeight() : -1;
    rightHeight = node.hasRight() ? node.getRight().getHeight() : -1;

    return rightHeight - leftHeight;
  }

  /**
   * Checking if the avl is empty
   * @return true if top is null, false otherwise
   */
  public boolean avlIsEmpty() {
    return top == null;
  }

  /**
   * Insert top node
   * @param node the node to insert
   */
  public void insertTop(AvlNode<T> node) {
    top = node;
  }

  /**
   * Get top node
   * @return top
   */
  public AvlNode<T> getTop() {
    return top;
  }

  /**
   * Set top node
   * @param top the node to set as top
   */
  void setTop(AvlNode<T> top) {
    this.top = top;
    this.top.setParent(null);
  }

  /**
   * Get height of subtree
   * @param node the top of the subtree
   * @return the height if the node is not null, -1 otherwise
   */
  int height(AvlNode<T> node) {
    return node == null ? -1 : node.getHeight();
  }

  /**
   * The representation of the tree as a {@link String}
   * @return resulting string
   * @see #inOrder(AvlNode)
   */
  public String toString() {
    return inOrder(top);
  }

  /**
   * Auxiliary method to obtain the {@link String} representation of a subtree.
   * @param node the top of the subtree
   * @return the String representation
   */
  private String inOrder(AvlNode<T> node) {
    if (node == null) return "";
    else {
      String result = " | " + node.getItem();
      result += inOrder(node.getLeft());
      result += inOrder(node.getRight());
      return result;
    }
  }
}
