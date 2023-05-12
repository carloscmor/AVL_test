package avl;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 *
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 * @version 2.1
 */
class AvlNodeTest {

    private AvlNode<Integer> node;

    @BeforeEach
    void setUp() {
        node = new AvlNode<>(5);
    }

    @AfterEach
    void tearDown() {
        node = null;
    }

    @Nested
    @DisplayName("Test that checks if a node has a left side or not")
    class leftSide {
        @Test
        @DisplayName("The node has left side")
        void hasLeft() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setLeft(node2);
            assertThat(node.hasLeft()).as("The node should have a left side");
        }

        @Test
        @DisplayName("The node does not have left side")
        void doesNotHaveLeft() {
            assertThat(node.hasLeft()).as("The node should not have a left side").isFalse();
        }

        @Test
        @DisplayName("The node has only a left child")
        void hasOnlyLeftChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setLeft(node2);
            assertThat(node.hasOnlyALeftChild()).as("The node should have a left side");
        }

        @Test
        @DisplayName("The node doesn't have only a left child")
        void hasBothSides() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            AvlNode<Integer> node3 = new AvlNode<>(3);
            node.setLeft(node3);
            assertThat(node.hasOnlyALeftChild()).as("The node should not have only a left side").isFalse();
        }

        @Test
        @DisplayName("The node has only a right child")
        void hasOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            assertThat(node.hasOnlyALeftChild()).as("The node should have only a left side").isFalse();
        }
    }

    @Nested
    @DisplayName("Test that checks if a node has a right side or not")
    class rightSide {
        @Test
        @DisplayName("The node has right side")
        void testHasRight() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            assertThat(node.hasRight()).as("The node should have a right side");
        }

        @Test
        @DisplayName("The node does not have right side")
        void testHasNoRight() {
            assertThat(node.hasRight()).isFalse().as("The node should not have a right side");
        }

        @Test
        @DisplayName("The node has only a right child")
        void hasOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            assertThat(node.hasOnlyARightChild()).as("The node should have only a right side");
        }

        @Test
        @DisplayName("The node doesn't have only a right child")
        void DosenthaveOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            AvlNode<Integer> node3 = new AvlNode<>(3);
            node.setLeft(node3);
            assertThat(node.hasOnlyARightChild()).as("The node should not have only a right side");
        }

        @Test
        @DisplayName("The node has only a left child")
        void hasOnlyLeftChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setLeft(node2);
            assertThat(node.hasOnlyARightChild()).as("The node should not have a right side only");
        }
    }

    @SuppressWarnings("MagicNumber")
    @Test
    @DisplayName("Test that checks that the method setHeight works correctly")
    void shouldSetHeight() {
        int expectedHeight = 1000213;
        node.setHeight(expectedHeight);
        assertThat(node.getHeight()).as("Height is different from expected.").isEqualTo(expectedHeight);
    }
}
