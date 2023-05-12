package avl;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 * Refactor made by:
 *
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
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
            assertTrue(node.hasLeft(), "testHasLeft");
        }

        @Test
        @DisplayName("The node does not have left side")
        void doesNotHaveLeft() {
            assertFalse(node.hasLeft(), "testHasLeft");
        }

        @Test
        @DisplayName("The node has only a left child")
        void hasOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setLeft(node2);
            assertTrue(node.hasOnlyALeftChild(), "testHasRight");
        }

        @Test
        @DisplayName("The node doesn't have only a left child")
        void DosenthaveOnlyLeftChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            AvlNode<Integer> node3 = new AvlNode<>(3);
            node.setLeft(node3);
            assertFalse(node.hasOnlyALeftChild(), "testHasRight");
        }

        @Test
        @DisplayName("The node has only a right child")
        void hasOnlyLeftChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            assertFalse(node.hasOnlyALeftChild(), "testHasRight");
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
            assertTrue(node.hasRight(), "testHasRight");
        }

        @Test
        @DisplayName("The node does not have right side")
        //Tiene no right child y no left child
        void testHasNoRight() {
            assertFalse(node.hasRight(), "testHasRight");
        }

        @Test
        @DisplayName("The node has only a right child")
        void hasOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            assertTrue(node.hasOnlyARightChild(), "testHasRight");
        }

        @Test
        @DisplayName("The node doesn't have only a right child")
        void DosenthaveOnlyRightChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setRight(node2);
            AvlNode<Integer> node3 = new AvlNode<>(3);
            node.setLeft(node3);
            assertFalse(node.hasOnlyARightChild(), "testHasRight");
        }

        @Test
        @DisplayName("The node has only a left child")
        void hasOnlyLeftChild() {
            AvlNode<Integer> node2 = new AvlNode<>(6);
            node.setLeft(node2);
            assertFalse(node.hasOnlyARightChild(), "testHasRight");
        }
    }

    @SuppressWarnings("MagicNumber")
    @Test
    @DisplayName("Test that checks that the method setHeight works correctly")
    void shouldSetHeight() {
        int expectedHeight = 1000213;
        node.setHeight(expectedHeight);
        assertEquals(expectedHeight, node.getHeight(), "Height is different from expected.");
    }
}
