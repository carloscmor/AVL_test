package avl;

import org.junit.jupiter.api.*;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Improved class made for testing AVL tree with extensions and the latest JUnit verxion (v5).
 *
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 * @version 2.2
 */
@SuppressWarnings("MagicNumber")
class AvlTreeTest {

    private AvlTree<Integer> avlTree;
    private Comparator<Integer> comparator;

    @BeforeEach
    void setUp() {
        comparator = Comparator.comparingInt((Integer o) -> o);
        avlTree = new AvlTree<>(comparator);
    }

    @AfterEach
    void tearDown() {
        avlTree = null;
        comparator = null;
    }

    @Nested
    @DisplayName("When calling avlIsEmpty() method")
    class EmptyMethodTest {
        @Test
        @DisplayName("it returns true if avlTree is empty")
        void testAvlIsEmpty() {
            assertTrue(avlTree.avlIsEmpty(), "This test should return that the avlTree is empty");
        }

        @Test
        @DisplayName("it returns false if avlTree is not empty")
        void testAvlIsNotEmpty() {
            avlTree.insertTop(new AvlNode<>(5));
            assertFalse(avlTree.avlIsEmpty(), "This test should return that the avlTree is not empty");
        }
    }

    @SuppressWarnings("ClassWithTooManyMethods")
    @Nested
    @DisplayName("When inserting elements on avlTree")
    class InsertTest {
        @Test
        @DisplayName("the element top is inserted correctly")
        void testInsertTop() {
            avlTree.insert(4);
            AvlNode<Integer> node = avlTree.search(4);
            assertEquals(node, avlTree.getTop(), "This test should return the only node in a tree is the top");
            String tree = " | 4";
            assertEquals(tree, avlTree.toString(), "This test should return the only node inserted is the top");
        }

        @Nested
        @DisplayName("When inserting an existing value, it does nothing")
        class InsertExistingValue {

            @Test
            @DisplayName("checked with new method size()")
            void testInsertingANodeWithAnItemThatWasAlreadyInTheTree() {
                AvlNode<Integer> node = new AvlNode<>(6);
                avlTree.insertAvlNode(node);

                int lengthBefore = avlTree.size();

                AvlNode<Integer> nodetwice = new AvlNode<>(6);
                avlTree.insertAvlNode(nodetwice);

                int lengthAfter = avlTree.size();

                assertEquals(lengthAfter, lengthBefore);
            }

            @Test
            @DisplayName("checked with method toString()")
            void testInsertingANodeWithAnItemThatWasAlreadyInTheTreeWithToString() {
                AvlNode<Integer> node = new AvlNode<>(9);
                avlTree.insertAvlNode(node);
                AvlNode<Integer> nodetwice = new AvlNode<>(9);
                avlTree.insertAvlNode(nodetwice);

                String tree = " | 9";
                assertEquals(tree, avlTree.toString(), "This test should return that node has an equal item to nodetwice");
            }
        }

        /**
         * Testing adding 7 - 9 - 8
         */
        @Test
        @DisplayName("Test that checks that you can insert a rigth-left nodes and the tree will keep balance")
        void testInsertingRightLeftNodeAndRebalance() {
            AvlNode<Integer> node1, node2, node3;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node2 = new AvlNode<>(9);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(8);
            avlTree.insertAvlNode(node3);

            assertEquals(node3, avlTree.getTop(), "This test should return that node3 should be the top of the tree");
            assertEquals(node1, node3.getLeft(), "This test should return that node1 should be at the left branch of node3");
            assertEquals(node2, node3.getRight(), "This test should return that node2 should be at the right branch of node3");

            assertEquals(1, avlTree.getTop().getHeight(), "This test should return that top should have height 1");
            assertEquals(0, avlTree.getTop().getLeft().getHeight(), "This test should return that the left branch have height 0");
            assertEquals(0, avlTree.getTop().getRight().getHeight(), "This thest should return that the right branch should have height 0");
            assertEquals(-1, avlTree.height(node2.getLeft()), "This test should return that the height of leaf left child of node2 should be -1");
            assertEquals(-1, avlTree.height(node2.getRight()), "This test should return that the height of leaf right child of node2 should be -1");
            assertEquals(-1, avlTree.height(node1.getLeft()), "This test should return that the height of leaf left child of node1 should be -1");
            assertEquals(-1, avlTree.height(node1.getRight()), "This test should return that the height of leaf right child of node1 should be -1");

            String tree = " | 8 | 7 | 9";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 8 | 7 | 9");
        }

        @Test
        @DisplayName("Test that checks that when you insert the first element it becomes the top node")
        void testInsertingTheFirstElement() {
            AvlNode<Integer> node = new AvlNode<>(6);
            avlTree.insertAvlNode(node);
            assertEquals(node, avlTree.getTop(), "This test should return that the first node is the top");
        }

        @Test
        @DisplayName("Check that inserts are made after top")
        void testInsertingRightAndLeftElementsJustAfterTop() {
            AvlNode<Integer> node = new AvlNode<>(6);
            avlTree.insertAvlNode(node);
            AvlNode<Integer> nodeLeft = new AvlNode<>(4);
            AvlNode<Integer> nodeRight = new AvlNode<>(9);

            assertEquals(-1, avlTree.searchClosestNode(nodeLeft), "This test should return that nodeLeft has an inferior item than node");
            assertEquals(node, nodeLeft.getClosestNode(), "This test should return that the closestNode of nodeLeft is node");
            assertEquals(+1, avlTree.searchClosestNode(nodeRight), "This test should return that nodeRight has an superior item than node");
            assertEquals(node, nodeRight.getClosestNode(), "This test should return that the closestNode of nodeRigth is node");
            assertEquals(0, avlTree.searchClosestNode(node), "This test should return that node has an equal item than node");

            node.setLeft(nodeLeft);
            node.setRight(nodeRight);
            AvlNode<Integer> nodeRightLeft = new AvlNode<>(7);
            avlTree.searchClosestNode(nodeRightLeft);
            assertEquals(-1, avlTree.searchClosestNode(nodeRightLeft), "This test should return that nodeRightLeft has an inferior item than node");
            assertEquals(nodeRight, nodeRightLeft.getClosestNode(), "This test should return that the closestNode of nodeRigthLeft is nodeRight");

            AvlNode<Integer> nodeLeftRight = new AvlNode<>(5);
            assertEquals(1, avlTree.searchClosestNode(nodeLeftRight), "This test should return that nodeLeftRight has an inferior item than node");
            assertEquals(nodeLeft, nodeLeftRight.getClosestNode(), "This test should return that the closestNode of nodeLeftRight is nodeLeft");

            String tree = " | 6 | 4 | 9";
            assertEquals(tree, avlTree.toString(), "This test should return that the tree created is | 6 | 4 | 9");
        }

        @Test
        @DisplayName("Test that checks that you can insert a right element correctly")
        void testInsertingRightElement() {
            AvlNode<Integer> node = new AvlNode<>(6);
            avlTree.insertAvlNode(node);
            AvlNode<Integer> nodeRight = new AvlNode<>(9);
            avlTree.insertAvlNode(nodeRight);

            assertEquals(node, nodeRight.getParent(), "This test should return right node's parent");
            assertEquals(nodeRight, node.getRight(), "This test should return node's right AvlNode");

            String tree = " | 6 | 9";
            assertEquals(tree, avlTree.toString(), "This test should return  | 6 | 9 ");
        }

        @Test
        @DisplayName("Tests that checks that when inserting a node that is lower than the top one it is inserted in left side")
        void testInsertingLeftElement() {
            AvlNode<Integer> node = new AvlNode<>(6);
            avlTree.insertAvlNode(node);
            AvlNode<Integer> nodeLeft = new AvlNode<>(4);
            avlTree.insertAvlNode(nodeLeft);

            assertEquals(node, nodeLeft.getParent(), "This test should return that node is the parent");
            assertEquals(nodeLeft, node.getLeft(), "This test should return that nodeLeft is the left node of node");

            String tree = " | 6 | 4";
            assertEquals(tree, avlTree.toString(), "This test should return that the tree created is | 6 | 4");
        }

        @Test
        @DisplayName("searching a node with no top means null is returned")
        void testWhenSearchingANodeWithNoTopTheResultIsNull() {
            AvlTree<Integer> tree = new AvlTree<>(comparator);
            AvlNode<Integer> node = new AvlNode<>(6);
            assertNull(tree.searchNode(node), "This test should return that you can't search a node in the tree becuase the top node is null");
        }

        /**
         * Testing adding 7 - 4 - 3
         */
        @Test
        @DisplayName("inserting two nodes on the left spine of the tree, means tree is rebalanced.")
        void testInsertingLeftLeftNodeAndRebalance() {
            AvlNode<Integer> node1, node2, node3;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);
            assertEquals(0, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(0, avlTree.getBalance(node1), "This test should return that AvlNode is balanced");

            node2 = new AvlNode<>(4);
            avlTree.insertAvlNode(node2);
            assertEquals(0, node2.getHeight(), "This test should return AvlNode's real height");
            assertEquals(1, node1.getHeight(), "This test should return that AvlNode is leaning to the right");
            assertEquals(-1, avlTree.getBalance(node1), "This test should return that AvlNode is leaning to the left");
            assertEquals(0, avlTree.getBalance(node2), "This test should return that AvlNode is balanced");

            node3 = new AvlNode<>(3);
            avlTree.insertAvlNode(node3);
            assertEquals(node2, avlTree.getTop(), "This test should return AvlNode's top");
            assertEquals(node3, node2.getLeft(), "This test should return AvlNode's left tree");
            assertEquals(node1, node2.getRight(), "This test should return AvlNode's right tree");

            assertEquals(1, avlTree.getTop().getHeight(), "This test should return AvlNode's top height");
            assertEquals(0, avlTree.getTop().getLeft().getHeight(), "This test should return AvlNode's left tree's height");
            assertEquals(0, avlTree.getTop().getRight().getHeight(), "This test should return that the height of the top node is 0");
            assertEquals(-1, avlTree.height(node1.getLeft()), "This thest should return that the height of the left child of node1 is -1");
            assertEquals(-1, avlTree.height(node1.getRight()), "This thest should return that the height of the right child of node1 is -1");
            assertEquals(-1, avlTree.height(node3.getLeft()), "This thest should return that the height of the left child of node3 is -1");
            assertEquals(-1, avlTree.height(node3.getRight()), "This thest should return that the height of the right child of node3 is -1");

            String tree = " | 4 | 3 | 7";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 4 | 3 | 7");
        }

        /**
         * Testing adding 7 - 10 - 14
         */
        @SuppressWarnings("MagicNumber")
        @Test
        @DisplayName("inserting two nodes on the right spine of the tree, means tree is rebalanced.")
        void testInsertingRightRightNodeAndRebalance() {
            AvlNode<Integer> node1, node2, node3;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);
            assertEquals(0, node1.getHeight(), "This test should return that the height of node1 is 0");
            assertEquals(0, avlTree.getBalance(node1), "This test should return that the node1 is balanced");

            node2 = new AvlNode<>(10);
            avlTree.insertAvlNode(node2);
            assertEquals(0, node2.getHeight(), "This test should return that the height of node2 is 0");
            assertEquals(1, node1.getHeight(), "This test should return that the height of node1 is 1");
            assertEquals(1, avlTree.getBalance(node1), "This test should return that the node1 is leaning to the right");
            assertEquals(0, avlTree.getBalance(node2), "This test should return that the node2 is balanced");

            node3 = new AvlNode<>(14);
            avlTree.insertAvlNode(node3);
            assertEquals(node2, avlTree.getTop(), "This test should return that node2 should be the top of the tree");
            assertEquals(node1, node2.getLeft(), "This test should return that node1 should be the left child of node2");
            assertEquals(node3, node2.getRight(), "This test should return that node3 should be the right child of node2");

            assertEquals(1, avlTree.getTop().getHeight(), "This test should return that the height of the top node is 0");
            assertEquals(0, avlTree.getTop().getLeft().getHeight(), "This test should return that the height of the left child of the top node is 0");
            assertEquals(0, avlTree.getTop().getRight().getHeight(), "This test should return that the height of the right child of the top node is 0");
            assertEquals(-1, avlTree.height(node1.getLeft()), "This thest should return that the height of the left child of node1 is -1");
            assertEquals(-1, avlTree.height(node1.getRight()), "This thest should return that the height of the lright child of node1 is -1");
            assertEquals(-1, avlTree.height(node3.getLeft()), "This thest should return that the height of the left child of node3 is -1");
            assertEquals(-1, avlTree.height(node3.getRight()), "This thest should return that the height of the right child of node3 is -1");
            String tree = " | 10 | 7 | 14";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 10 | 7 | 14");
        }

        /**
         * Testing adding 7 - 4 - 3 - 2 - 1
         */
        @Test
        @DisplayName("Test that inserting 7,4,3,2,1 results on tree | 4 | 2 | 1 | 3 | 7 ")
        void testInserting7_4_3_2_1() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            node2 = new AvlNode<>(4);
            node3 = new AvlNode<>(3);
            node4 = new AvlNode<>(2);
            node5 = new AvlNode<>(1);

            avlTree.insertAvlNode(node1);
            avlTree.insertAvlNode(node2);
            avlTree.insertAvlNode(node3);
            avlTree.insertAvlNode(node4);
            avlTree.insertAvlNode(node5);

            assertEquals(node2, avlTree.getTop(), "This test should return that node2 should be the top of the tree");
            assertEquals(node4, node2.getLeft(), "This test should return that node4 should be the left child of node2");
            assertEquals(node1, node2.getRight(), "This test should return that node1 should be the right child of node2");
            assertEquals(node5, node4.getLeft(), "This test should return that node5 should be the left child of node4");
            assertEquals(node3, node4.getRight(), "This test should return that node3 should be the right child of node4");
            assertEquals(0, node1.getHeight(), "This test should return that the height of node1 is 0");
            assertEquals(2, node2.getHeight(), "This test should return that the height of node2 is 2");
            assertEquals(1, node4.getHeight(), "This test should return that the height of node4 is 1");

            String tree = " | 4 | 2 | 1 | 3 | 7";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 4 | 2 | 1 | 3 | 7");
        }

        /**
         * Testing adding 7 - 8 - 9 - 10 - 11
         */
        @SuppressWarnings("MagicNumber")
        @Test
        @DisplayName("Test that checks that if you insert numbers in numeric order, the tree will keep balance")
        void testInserting7_8_9_10_11() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            node2 = new AvlNode<>(8);
            node3 = new AvlNode<>(9);
            node4 = new AvlNode<>(10);
            node5 = new AvlNode<>(11);

            avlTree.insertAvlNode(node1);
            avlTree.insertAvlNode(node2);
            avlTree.insertAvlNode(node3);
            avlTree.insertAvlNode(node4);
            avlTree.insertAvlNode(node5);

            assertEquals(node2, avlTree.getTop(), "This test should return that node2 should be the top of the tree");
            assertEquals(node4, node2.getRight(), "This test should return that node4 should be the right child of node2");
            assertEquals(node1, node2.getLeft(), "This test should return that node1 should be the left child of node2");
            assertEquals(node5, node4.getRight(), "This test should return that node5 should be the right child of node4");
            assertEquals(node3, node4.getLeft(), "This test should return that node3 should be the left child of node4");
            assertEquals(2, avlTree.getTop().getHeight(), "This test should return that the height of the top is 2");
            assertEquals(1, node4.getHeight(), "This test should return that the height of node4 is 1");
            assertEquals(0, node1.getHeight(), "This test should return that the height of node1 is 0");

            String tree = " | 8 | 7 | 10 | 9 | 11";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 8 | 7 | 10 | 9 | 11");
        }

        /**
         * Testing adding 7 - 2 - 3
         */
        @Test
        @DisplayName("Test that checks that you can insert a left-right nodes and the tree will keep balance")
        void testInsertingLeftRightNodeAndRebalance() {
            AvlNode<Integer> node1, node2, node3;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node2 = new AvlNode<>(2);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(3);
            avlTree.insertAvlNode(node3);

            assertEquals(node3, avlTree.getTop(), "This test should return that node3 should be the top of the tree");
            assertEquals(node2, node3.getLeft(), "This test should return that node2 should be at the left branch of node3");
            assertEquals(node1, node3.getRight(), "This test should return that node1 should be at the right branch of node3");

            assertEquals(1, avlTree.getTop().getHeight(), "This test should return that the height of the top node is 1");
            assertEquals(0, avlTree.getTop().getLeft().getHeight(), "This test should return that the height of the left branch of the top is 0");
            assertEquals(0, avlTree.getTop().getRight().getHeight(), "This test should return that the height of the right branch of the top is 0");
            assertEquals(-1, avlTree.height(node2.getLeft()), "This test should return that the height of left child of node2 should be -1");
            assertEquals(-1, avlTree.height(node2.getRight()), "This test should return that the height of right child of node2 should be -1");
            assertEquals(-1, avlTree.height(node1.getLeft()), "This test should return that the height of left child of node1 should be -1");
            assertEquals(-1, avlTree.height(node1.getRight()), "This test should return that the height of right child of node1 should be -1");

            String tree = " | 3 | 2 | 7";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 3 | 2 | 7");
        }
    }

    @SuppressWarnings("MagicNumber")
    @Nested
    @DisplayName("When deleting elements on avlTree")
    class DeleteTest {
        @Test
        @DisplayName("Test that checks that you can't delete a node that is not in the tree")
        void testDeletingANonExistingNode() {
            AvlNode<Integer> node1;
            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            assertThrows(NullPointerException.class, () -> avlTree.delete(8));
        }

        @Test
        @DisplayName("Test checking that you can delete the right child")
        void testDeletingOnlyRightChildLeafNodes() {
            AvlNode<Integer> node1, node2, node3, node4;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node4 = new AvlNode<>(3);
            avlTree.insertAvlNode(node4);

            node2 = new AvlNode<>(9);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(11);
            avlTree.insertAvlNode(node3);

            avlTree.delete(9);

            assertEquals(1, node1.getHeight(), "This test should return that the height of the top node is 1");
        }

        @Test
        @DisplayName("Test checking that you can delete the left child")
        void testDeletingOnlyLeftChildLeafNodes() {
            AvlNode<Integer> node1, node2, node3, node4;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node4 = new AvlNode<>(8);
            avlTree.insertAvlNode(node4);

            node2 = new AvlNode<>(5);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(2);
            avlTree.insertAvlNode(node3);

            avlTree.delete(5);

            assertEquals(1, node1.getHeight(), "This test should return that the height of the top node is 1");
        }

        @Test
        @DisplayName("Test that checks that you can delete leaf nodes correctly")
        void testDeletingLeafNodes() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node2 = new AvlNode<>(9);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(2);
            avlTree.insertAvlNode(node3);

            node4 = new AvlNode<>(8);
            avlTree.insertAvlNode(node4);

            node5 = new AvlNode<>(3);
            avlTree.insertAvlNode(node5);

            String tree = " | 7 | 2 | 3 | 9 | 8";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 7 | 2 | 3 | 9 | 8");

            avlTree.delete(3); // right leaf node
            assertNull(node3.getRight(), "This test should return that the right node of node3 is null");
            assertEquals(0, node3.getHeight(), "This test should return that the height of the node3 is 0");
            assertEquals(2, avlTree.getTop().getHeight(), "This test should return that the height of the top node is 2");
            assertEquals(" | 7 | 2 | 9 | 8", avlTree.toString(), "This test should return that actual tree is | 7 | 2 | 9 | 8");

            avlTree.delete(8); // left leaf node
            assertNull(node2.getLeft(), "This test should return that the left node of node2 is null");
            assertEquals(0, node2.getHeight(), "This test should return that the height of the node2 is 0");
            assertEquals(1, avlTree.getTop().getHeight(), "This test should return that the height of the top node is 1");
            assertEquals(" | 7 | 2 | 9", avlTree.toString(), "This test should return that actual tree is | 7 | 2 | 9");

            avlTree.delete(2); // left leaf node
            assertNull(node1.getLeft(), "This test should return that the left node of node1 is null");
            assertEquals(1, node1.getHeight(), "This test should return that the height of the node1 is 1");
            assertEquals(" | 7 | 9", avlTree.toString(), "This test should return that actual tree is | 7 | 9");

            avlTree.delete(7); // left leaf node
            assertNull(node1.getRight(), "This test should return that the right node of node1 is null");
            assertEquals(0, node1.getHeight(), "This test should return that the height of the node1 is 0");
            assertEquals(" | 9", avlTree.toString(), "This test should return that actual tree is | 9");

            avlTree.delete(9); // right leaf node
            assertNull(avlTree.getTop(), "This test should return that the top node is null");
            assertEquals("", avlTree.toString(), "This test should return that actual tree is empty");
        }

        @Test
        @DisplayName("Test that checks that you can delete nodes that only have one leaf")
        void testDeletingNodesWithOneLeaf() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node2 = new AvlNode<>(9);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(2);
            avlTree.insertAvlNode(node3);

            node4 = new AvlNode<>(8);
            avlTree.insertAvlNode(node4);

            node5 = new AvlNode<>(3);
            avlTree.insertAvlNode(node5);

            String tree = " | 7 | 2 | 3 | 9 | 8";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24");

            avlTree.delete(2);
            assertEquals(node3.getItem(), node1.getLeft().getItem(), "This test should return that the item of node3 is the same as the left of node1");
            assertNull(node3.getRight(), "This test should return that the item of the right node of node3 is null");
            assertEquals(0, node3.getHeight(), "This test should return that the height of right of node3 is 0");
            assertEquals(2, avlTree.getTop().getHeight(), "This test should return that the height of the top is 2");
            assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString(), "This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24");

            avlTree.delete(9);
            assertEquals(node2.getItem(), node1.getRight().getItem(), "This test should return that the item of node2 is the same of the right node of node 1");
            assertNull(node2.getLeft(), "This test should return that the item of the left node of node2 is null");
            assertEquals(0, node2.getHeight(), "This test should return that the height of node2 is 0");
            assertEquals(1, avlTree.getTop().getHeight(), "This test should return that the height of the top is 1");
            assertEquals(" | 7 | 3 | 8", avlTree.toString(), "This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24");
        }

        @Test
        @DisplayName("Test that checks that you can delete nodes with two leaves correctly")
        void testDeletingNodesWithTwoLeaves() {
            AvlNode<Integer> node;

            node = new AvlNode<>(20);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(8);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(22);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(4);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(12);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(24);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(10);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(14);
            avlTree.insertAvlNode(node);

            String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
            assertEquals(expected, avlTree.toString(), "This test should return that the actual tree is | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24");

            avlTree.delete(12);
            node = avlTree.search(8);
            assertEquals(14, node.getRight().getItem(), "This test should return that the item of the right node of node is 14");
            assertEquals(" | 20 | 8 | 4 | 14 | 10 | 22 | 24", avlTree.toString(), "This test should return that actual tree is | 20 | 8 | 4 | 14 | 10 | 22 | 24");

            avlTree.delete(8);
            assertEquals(10, avlTree.getTop().getLeft().getItem(), "This test should return that the item of the left node of the top of the node is 10");
            assertEquals(" | 20 | 10 | 4 | 14 | 22 | 24", avlTree.toString(), "This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24");
        }

        @Test
        @DisplayName("Test that checks that you can delete nodes and the tree will keep balanced")
        void testDeletingAndRebalancing() {
            AvlNode<Integer> node;

            node = new AvlNode<>(20);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(8);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(22);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(4);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(12);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(24);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(10);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(14);
            avlTree.insertAvlNode(node);

            assertEquals(3, avlTree.getTop().getHeight(), "This test should return that the height of top is 3");

            avlTree.delete(22);
            assertEquals(12, avlTree.getTop().getItem(), "This test should return that the item of top is 12");
            assertEquals(avlTree.search(8), avlTree.getTop().getLeft(), "This test should return that the item of the left node of top is 8");
            assertEquals(avlTree.search(20), avlTree.getTop().getRight(), "This test should return that the item of the right node of top is 20");
        }

        @Test
        @DisplayName("Test that checks that you can delete a top node correctly")
        void testDeletingTopNode() {
            AvlNode<Integer> node;

            node = new AvlNode<>(20);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(8);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(22);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(4);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(12);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(24);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(10);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(14);
            avlTree.insertAvlNode(node);

            assertEquals(3, avlTree.getTop().getHeight(), "This test should return that the height of top is 3");

            avlTree.delete(20);
            assertEquals(" | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString(), "This test should return that the final tree is | 12 | 8 | 4 | 10 | 22 | 14 | 24");
        }
    }

    @Nested
    @DisplayName("when doing manipulation operations on node trees")
    class OperationsOnNodesTest {
        @Test
        @DisplayName("Test that checks that the method search works correctly")
        void testSearchNode() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);

            node2 = new AvlNode<>(9);
            avlTree.insertAvlNode(node2);

            node3 = new AvlNode<>(8);
            avlTree.insertAvlNode(node3);

            node4 = new AvlNode<>(2);
            avlTree.insertAvlNode(node4);

            node5 = new AvlNode<>(3);
            avlTree.insertAvlNode(node5);

            assertEquals(node1, avlTree.search(7), "This test should return that the node1 is the one with the item 7");
            assertEquals(node2, avlTree.search(9), "This test should return that the node2 is the one with the item 9");
            assertEquals(node3, avlTree.search(8), "This test should return that the node3 is the one with the item 8");
            assertEquals(2, avlTree.searchNode(new AvlNode<>(2)).getItem(), "This test should return that the node created with item 2, really has the item 2");
            assertEquals(node4, avlTree.search(2), "This test should return that the node4 is the one with the item 2");
            assertEquals(node5, avlTree.search(3), "This test should return that the node5 is the one with the item 3");
            assertNull(avlTree.search(14), "This test should return that the node with the item 14 is null beacuase it wasn't created");
            assertNull(avlTree.search(0), "This test should return that the node with the item 0 is null because it wasn't created");

            String tree = " | 8 | 3 | 2 | 7 | 9";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 8 | 3 | 2 | 7 | 9");
        }

        @Test
        @DisplayName("Test that finds the successor")
        void testFindSuccessor() {
            AvlNode<Integer> node;

            node = new AvlNode<>(20);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(8);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(22);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(4);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(12);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(24);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(10);
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(14);
            avlTree.insertAvlNode(node);

            node = avlTree.search(8);
            assertEquals(avlTree.search(10), avlTree.findSuccessor(node), "This test should return that the succesor of the node with item 8 is the node with item 10");
            node = avlTree.search(10);
            assertEquals(avlTree.search(12), avlTree.findSuccessor(node), "This test should return that the succesor of the node with item 10 is the node with item 12");
            node = avlTree.search(14);
            assertEquals(avlTree.search(20), avlTree.findSuccessor(node), "This test should return that the succesor of the node with item 14 is the node with item 20");

            String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
            assertEquals(tree, avlTree.toString(), "This test should return that actual tree is | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24");
        }

        @Test
        @DisplayName("Test that doesn't find the succesor because it doesn't have one")
        void findNoSuccesor() {
            AvlNode<Integer> node;

            node = new AvlNode<>(20);
            avlTree.insertAvlNode(node);

            assertNull(avlTree.findSuccessor(node), "Test that should return that the succesor of the top node is null");
        }

        @Test
        @DisplayName("Test that checks that the method compareNodes of a avlTree works correctly")
        void testCompareNodes() {
            AvlNode<Integer> node1 = new AvlNode<>(4);
            AvlNode<Integer> node2 = new AvlNode<>(5);
            AvlNode<Integer> node3 = new AvlNode<>(5);

            assertEquals(-1, avlTree.compareNodes(node1, node2), "This test should return that node1 has an inferior item than node2");
            assertEquals(1, avlTree.compareNodes(node3, node1), "This test should return that node3 has an inferior item than node1");
            assertEquals(0, avlTree.compareNodes(node2, node3), "This test should return that node2 has an equal item than node3");
        }

        @Test
        @DisplayName("Test than checks that when getting closest node it returns node's closest node correctly")
        void testSearchClosestNode() {
            int result;
            AvlNode<Integer> node = new AvlNode<>(7);
            result = avlTree.searchClosestNode(node);
            assertEquals(0, result, "This test should return that it has no closest node");
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(4);
            result = avlTree.searchClosestNode(node);
            assertEquals(-1, result, "This test should return that its closest node must be in the left side");
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(9);
            result = avlTree.searchClosestNode(node);
            assertEquals(1, result, "This test should return that its closest node must be in the right side");
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(6);
            result = avlTree.searchClosestNode(node);
            assertEquals(1, result, "This test should return that its closest node must be in the right side");
            avlTree.insertAvlNode(node);

            node = new AvlNode<>(8);
            result = avlTree.searchClosestNode(node);
            assertEquals(-1, result, "This test should return that its closest node must be in the left side");
            avlTree.insertAvlNode(node);

            String tree = " | 7 | 4 | 6 | 9 | 8";
            assertEquals(tree, avlTree.toString(), "The tree should be  | 7 | 4 | 6 | 9 | 8");
        }

        /**
         * Test adding 7 - 4 - 9 - 3 - 5
         */
        @Test
        @DisplayName("Test that checks a balanced tree height and if it is balanced")
        void testHeightAndBalanceOfASimpleBalancedTree() {
            AvlNode<Integer> node1, node2, node3, node4, node5;

            node1 = new AvlNode<>(7);
            avlTree.insertAvlNode(node1);
            assertEquals(0, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(0, avlTree.getBalance(node1), "This test should return that AvlNode is balanced");

            node2 = new AvlNode<>(4);
            avlTree.insertAvlNode(node2);
            assertEquals(0, node2.getHeight(), "This test should return AvlNode's real height");
            assertEquals(1, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(-1, avlTree.getBalance(node1), "This test should return that AvlNode is leaning to the left");
            assertEquals(0, avlTree.getBalance(node2), "This test should return that AvlNode is balanced");

            node3 = new AvlNode<>(9);
            avlTree.insertAvlNode(node3);
            assertEquals(0, node3.getHeight(), "This test should return AvlNode's real height");
            assertEquals(1, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(0, avlTree.getBalance(node1), "This test should return that AvlNode is balanced");
            assertEquals(0, avlTree.getBalance(node3), "This test should return that AvlNode is balanced");

            node4 = new AvlNode<>(3);
            avlTree.insertAvlNode(node4);
            assertEquals(0, node4.getHeight(), "This test should return AvlNode's real height");
            assertEquals(1, node2.getHeight(), "This test should return AvlNode's real height");
            assertEquals(2, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(-1, avlTree.getBalance(node2), "This test should return that AvlNode is leaning to the left");
            assertEquals(-1, avlTree.getBalance(node1), "This test should return that AvlNode is leaning to the left");
            assertEquals(0, avlTree.getBalance(node4), "This test should return that AvlNode is balanced");

            node5 = new AvlNode<>(5);
            avlTree.insertAvlNode(node5);
            assertEquals(0, node5.getHeight(), "This test should return AvlNode's real height");
            assertEquals(1, node2.getHeight(), "This test should return AvlNode's real height");
            assertEquals(2, node1.getHeight(), "This test should return AvlNode's real height");
            assertEquals(0, avlTree.getBalance(node2), "This test should return that AvlNode is balanced");
            assertEquals(-1, avlTree.getBalance(node1), "This test should return that AvlNode is leaning to the left");
            assertEquals(0, avlTree.getBalance(node5), "This test should return that AvlNode is balanced");

            String tree = " | 7 | 4 | 3 | 5 | 9";
            assertEquals(tree, avlTree.toString(), "This test should return  | 7 | 4 | 3 | 5 | 9 ");
        }
    }

    @Nested
    @DisplayName("When calling size() method of a tree")
    class TreeSizeTests {
        @Test
        @DisplayName("empty tree means it has 0 elements")
        void emptyTreeSizeTest() {
            int expectedSize = 0;
            assertEquals(expectedSize, avlTree.size(), "It should give 0");
        }

        @Test
        @DisplayName("tree containing only root means it has 1 element")
        void oneElementTreeSizeTest() {
            AvlNode<Integer> root = new AvlNode<>(2);
            avlTree.insertAvlNode(root);

            int expectedSize = 1;
            assertEquals(expectedSize, avlTree.size(), "It should give 0");
        }

        @Test
        @DisplayName("it calculates right number of elements")
        void sizeWorksFineWithMoreElements() {
            avlTree.insert(2);
            avlTree.insert(3);

            int expectedSize = 2;
            assertEquals(expectedSize, avlTree.size(), "It should give 2");
        }
    }
}