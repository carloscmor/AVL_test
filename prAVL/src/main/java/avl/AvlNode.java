//  AvlNode
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//
//  Copyright (c) 2013 Antonio J. Nebro
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

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13 Time: 15:46
 * Refactor made by:
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 */
public class AvlNode<T> {

  private AvlNode<T> left;
  private AvlNode<T> right;
  private AvlNode<T> parent;

  private int height;

  private AvlNode<T> closestNode;

  private T item;

  /**
   * Constructor
   */
  public AvlNode(T item) {
    this.left = null;
    this.right = null;
    this.parent = null;
    this.closestNode = null;
    this.item = item;

    this.updateHeight();
  }

  /**
   * This method gets the left node
   * @return left
   */
  public AvlNode<T> getLeft() {
    return left;
  }

  /**
   * This method sets the left node
   * @param left
   */
  public void setLeft(AvlNode<T> left) {
    this.left = left;
  }

  /**
   * This method gets the parent node
   * @return parent
   */
  public AvlNode<T> getParent() {
    return parent;
  }

  /**
   * This method sets the parent node
   * @param parent
   */
  public void setParent(AvlNode<T> parent) {
    this.parent = parent;
  }

  /**
   * This method gets the right node
   * @return right
   */
  public AvlNode<T> getRight() {
    return right;
  }

  /**
   * This method sets the right node
   * @param right
   */
  public void setRight(AvlNode<T> right) {
    this.right = right;
  }

  /**
   * This method gets the item of the node
   * @return item
   */
  public T getItem() {
    return item;
  }

  /**
   * This method sets the item of the node
   * @param item
   */
  public void setItem(T item) {
    this.item = item;
  }

  /**
   * This method gets the height where the node is
   * @return height
   */
  public int getHeight() {
    return height;
  }

  /**
   * This method sets the height of the node
   * @param height
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * This method updates the height of a node, depending on current connections
   */
  public void updateHeight() {
    if (!hasLeft() && !hasRight()) height = 0;
    else if (!hasRight()) height = 1 + getLeft().getHeight();
    else if (!hasLeft()) height = 1 + getRight().getHeight();
    else height = 1 + Math.max(getLeft().getHeight(), getRight().getHeight());
  }

  /**
   * This method gets the closest node
   * @return closestNode
   */
  public AvlNode<T> getClosestNode() {
    return closestNode;
  }

  /**
   * This method sets the closest node
   * @param closestNode
   */
  public void setClosestNode(AvlNode<T> closestNode) {
    this.closestNode = closestNode;
  }

  /**
   * This method checks whether the node has a parent
   * @return parent != null
   */
  public boolean hasParent() {
    return parent != null;
  }

  /**
   * This method checks whether the node has a left child node or not
   * @return left != null
   */
  public boolean hasLeft() {
    return  left != null;
  }

  /**
   * This method checks whether the node has a right node
   * @return right != null
   */
  public boolean hasRight() {
    return right != null;
  }

  /**
   * This method checks wether the node is leaf or not
   * @return
   */
  public boolean isLeaf() {
    return (!hasLeft() && !hasRight());
  }

  /**
   * This method checks whether a node has only a left child
   * @return(hasLeft() && !hasRight())
   */
  public boolean hasOnlyALeftChild() {
    return (hasLeft() && !hasRight());
  }

  /**
   * This method checks whether a node has only a right child
   * @return(hasRight() && !hasLeft())
   */
  public boolean hasOnlyARightChild() {
    return (hasRight() && !hasLeft());
  }
}
