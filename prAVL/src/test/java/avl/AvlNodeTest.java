package avl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
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

  @Test
  public void testHasLeft() {
    assertFalse("testHasLeft", node.hasLeft());
    AvlNode<Integer> node2 = new AvlNode<>(6);
    node.setLeft(node2);
    assertTrue("testHasLeft", node.hasLeft());
  }

  @Test
  public void testHasRight() {
    assertFalse("testHasRight", node.hasRight());
    AvlNode<Integer> node2 = new AvlNode<>(6);
    node.setRight(node2);
    assertTrue("testHasRight", node.hasRight());
  }

  @Test
  public void shouldSetHeight() {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    assertEquals("Height is different from expected.", expectedHeight, node.getHeight());
  }

}
