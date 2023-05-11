package avl;

import org.junit.jupiter.api.*;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 * Refactor made by:
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 */
public class AvlNodeTest {

  private AvlNode<Integer> node;

  @BeforeEach
  public void setUp() {
    node = new AvlNode<>(5);
  }

  @AfterEach
  public void tearDown() {
    node = null;
  }

  @Nested
  @DisplayName("Test that checks if a node has a left side or not")
  class leftSide {
    @Test
    @DisplayName("The node has left side")
    public void hasLeft() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setLeft(node2);
      assertTrue("testHasLeft", node.hasLeft());
    }
    @Test
    @DisplayName("The node does not have left side")
    public void doesNotHaveLeft() {
      assertFalse("testHasLeft", node.hasLeft());
    }

    @Test
    @DisplayName("The node has only a left child")
    public void hasOnlyRightChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setLeft(node2);
      assertTrue("testHasRight", node.hasOnlyALeftChild());
    }

    @Test
    @DisplayName("The node doesn't have only a left child")
    public void DosenthaveOnlyLeftChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setRight(node2);
      AvlNode<Integer> node3 = new AvlNode<>(3);
      node.setLeft(node3);
      assertFalse("testHasRight", node.hasOnlyALeftChild());
    }

    @Test
    @DisplayName("The node has only a right child")
    public void hasOnlyLeftChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setRight(node2);
      assertFalse("testHasRight", node.hasOnlyALeftChild());
    }
  }

  @Nested
  @DisplayName("Test that checks if a node has a right side or not")
  class rightSide {
    @Test
    @DisplayName("The node has right side")
    public void testHasRight() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setRight(node2);
      assertTrue("testHasRight", node.hasRight());
    }

    @Test
    @DisplayName("The node does not have right side")
    //Tiene no right child y no left child
    public void testHasNoRight() {
      assertFalse("testHasRight", node.hasRight());
    }

    @Test
    @DisplayName("The node has only a right child")
    public void hasOnlyRightChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setRight(node2);
      assertTrue("testHasRight", node.hasOnlyARightChild());
    }

    @Test
    @DisplayName("The node doesn't have only a right child")
    public void DosenthaveOnlyRightChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setRight(node2);
      AvlNode<Integer> node3 = new AvlNode<>(3);
      node.setLeft(node3);
      assertFalse("testHasRight", node.hasOnlyARightChild());
    }

    @Test
    @DisplayName("The node has only a left child")
    public void hasOnlyLeftChild() {
      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setLeft(node2);
      assertFalse("testHasRight", node.hasOnlyARightChild());
    }
  }

  @Test
  @DisplayName("Test that checks that the method setHeight works correctly")
  public void shouldSetHeight() {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    assertEquals("Height is different from expected.", expectedHeight, node.getHeight());
  }
}
