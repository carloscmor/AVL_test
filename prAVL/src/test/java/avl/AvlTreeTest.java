package avl;

import org.junit.jupiter.api.*;

import java.util.Comparator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.*;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 * Refactor made by:
 * @author Carlos Castaño Moreno
 * @author Daniel García Rodríguez
 * @author María Fernández Moreno
 * @author Nuria Rodríguez Tortosa
 */
public class AvlTreeTest {

    AvlTree<Integer> avlTree;
    Comparator<Integer> comparator;

    @BeforeEach
    public void setUp() {
        comparator = Comparator.comparingInt((Integer o) -> o);
        avlTree = new AvlTree<>(comparator);
    }

    @AfterEach
    public void tearDown() {
        avlTree = null;
        comparator = null;
    }

    @Nested
    @DisplayName("When calling avlIsEmpty() method")
    class EmptyMethodTest{
        @Test
        @DisplayName("it returns true if avlTree is empty")
        void testAvlIsEmpty(){
            assertTrue("This test should return that the avlTree is empty", avlTree.avlIsEmpty());
        }

        @Test
        @DisplayName("it returns false if avlTree is not empty")
        void testAvlIsNotEmpty(){
            avlTree.insertTop(new AvlNode<>(5));
            assertFalse("This test should return that the avlTree is not empty", avlTree.avlIsEmpty());
        }
    }

    @Nested
    @DisplayName("When inserting elements on avlTree")
    class InsertElementTest{
        @Test
        @DisplayName("the element top is inserted correctly")
        void testInsertTop() {
            avlTree.insert(4);
            AvlNode<Integer> node = avlTree.search(4);
            assertEquals("This test should return the only node in a tree is the top", node, avlTree.getTop());
            String tree = " | 4";
            assertEquals( "This test should return the only node inserted is the top", tree, avlTree.toString());
        }


    }



    @Test
    @DisplayName("Test that checks that the method compareNodes of a avlTree works correctly")
    public void testCompareNodes() {
        AvlNode<Integer> node1 = new AvlNode<>(4);
        AvlNode<Integer> node2 = new AvlNode<>(5);
        AvlNode<Integer> node3 = new AvlNode<>(5);

        assertEquals("This test should return that node1 has an inferior item than node2", -1, avlTree.compareNodes(node1, node2));
        assertEquals("This test should return that node3 has an inferior item than node1", 1, avlTree.compareNodes(node3, node1));
        assertEquals("This test should return that node2 has an equal item than node3", 0, avlTree.compareNodes(node2, node3));
    }


    @Test
    @DisplayName("Test that checks that when you insert the first element it becomes the top node")
    public void testInsertingTheFirstElement() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        assertEquals("This test should return that the first node is the top", node, avlTree.getTop());
    }

    @Nested
    @DisplayName("When inserting an existing value, it does nothing")
    class InsertExistingValue {

        @Test
        @DisplayName("checked with new method size()")
        public void testInsertingANodeWithAnItemThatWasAlreadyInTheTree(){
            AvlNode<Integer> node = new AvlNode<>(6);
            avlTree.insertAvlNode(node);

            int lengthBefore = avlTree.size();

            AvlNode<Integer> nodetwice = new AvlNode<>(6);
            avlTree.insertAvlNode(nodetwice);

            int lengthAfter = avlTree.size();

            assertThat(lengthAfter).isEqualTo(lengthBefore);
        }

        @Test
        @DisplayName("checked with method toString()")
        public void testInsertingANodeWithAnItemThatWasAlreadyInTheTreeWithToString() {
            AvlNode<Integer> node = new AvlNode<>(9);
            avlTree.insertAvlNode(node);
            AvlNode<Integer> nodetwice = new AvlNode<>(9);
            avlTree.insertAvlNode(nodetwice);

            String tree = " | 9";
            assertEquals("This test should return that node has an equal item to nodetwice", tree, avlTree.toString());
        }
    }

    @Test
    @DisplayName("searching a node with no top means null is returned")
    public void testWhenSearchingANodeWithNoTopTheResultIsNull(){
        AvlTree<Integer> tree = new AvlTree<>(comparator);
        AvlNode<Integer> node = new AvlNode<>(6);
        assertNull("This test should return that you can't search a node in the tree becuase the top node is null",tree.searchNode(node));

    }

    @Test
    @DisplayName("Check that inserts are made after top")
    public void testInsertingRightAndLeftElementsJustAfterTop() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeLeft = new AvlNode<>(4);
        AvlNode<Integer> nodeRight = new AvlNode<>(9);

        assertEquals("This test should return that nodeLeft has an inferior item than node", -1, avlTree.searchClosestNode(nodeLeft));
        assertEquals("This test should return that the closestNode of nodeLeft is node", node, nodeLeft.getClosestNode());
        assertEquals("This test should return that nodeRight has an superior item than node", +1, avlTree.searchClosestNode(nodeRight));
        assertEquals("This test should return that the closestNode of nodeRigth is node", node, nodeRight.getClosestNode());
        assertEquals("This test should return that node has an equal item than node", 0, avlTree.searchClosestNode(node));

        node.setLeft(nodeLeft);
        node.setRight(nodeRight);
        AvlNode<Integer> nodeRightLeft = new AvlNode<>(7);
        avlTree.searchClosestNode(nodeRightLeft);
        assertEquals("This test should return that nodeRightLeft has an inferior item than node", -1,
                avlTree.searchClosestNode(nodeRightLeft));
        assertEquals("This test should return that the closestNode of nodeRigthLeft is nodeRight", nodeRight, nodeRightLeft.getClosestNode());

        AvlNode<Integer> nodeLeftRight = new AvlNode<>(5);
        assertEquals("This test should return that nodeLeftRight has an inferior item than node", 1, avlTree.searchClosestNode(nodeLeftRight));
        assertEquals("This test should return that the closestNode of nodeLeftRight is nodeLeft", nodeLeft, nodeLeftRight.getClosestNode());

        String tree = " | 6 | 4 | 9";
        assertEquals("This test should return that the tree created is | 6 | 4 | 9", tree, avlTree.toString());
    }

    //TODO cambiar mensajes de los assertEquals
    @Test
    @DisplayName("Tests that checks that when inserting a node that is lower than the top one it is inserted in left side")
    public void testInsertingLeftElement() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeLeft = new AvlNode<>(4);
        avlTree.insertAvlNode(nodeLeft);

        assertEquals("This test should return that node is the parent", node, nodeLeft.getParent());
        assertEquals("This test should return that nodeLeft is the left node of node", nodeLeft, node.getLeft());

        String tree = " | 6 | 4";
        assertEquals("This test should return that the tree created is | 6 | 4", tree, avlTree.toString());
    }


    //TODO cambiar mensajes de los assertEquals
    @Test
    @DisplayName("Test than checks that when getting closest node it returns node's closest node correctly")
    public void testSearchClosestNode() {
        int result;
        AvlNode<Integer> node = new AvlNode<>(7);
        result = avlTree.searchClosestNode(node);
        assertEquals("This test should return that it has no closest node", 0, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        result = avlTree.searchClosestNode(node);
        assertEquals("This test should return that its closest node must be in the left side", -1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(9);
        result = avlTree.searchClosestNode(node);
        assertEquals("This test should return that its closest node must be in the right side", 1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(6);
        result = avlTree.searchClosestNode(node);
        assertEquals("This test should return that its closest node must be in the right side", 1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        result = avlTree.searchClosestNode(node);
        assertEquals("This test should return that its closest node must be in the left side", -1, result);
        avlTree.insertAvlNode(node);

        String tree = " | 7 | 4 | 6 | 9 | 8";
        assertEquals("testSearchClosestNode", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that you can insert a right element correctly")
    public void testInsertingRightElement() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeRight = new AvlNode<>(9);
        avlTree.insertAvlNode(nodeRight);

        assertEquals("This test should return right node's parent", node, nodeRight.getParent());
        assertEquals("This test should return node's right AvlNode", nodeRight, node.getRight());

        String tree = " | 6 | 9";
        assertEquals("This test should return  | 6 | 9 ", tree, avlTree.toString());
    }

    /**
     * Test adding 7 - 4 - 9 - 3 - 5
     */
    @Test
    @DisplayName("Test that checks a balanced tree height and if it is balanced")
    public void testHeightAndBalanceOfASimpleBalancedTree() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals("This test should return AvlNode's real height", 0, node1.getHeight());
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(4);
        avlTree.insertAvlNode(node2);
        assertEquals("This test should return AvlNode's real height", 0, node2.getHeight());
        assertEquals("This test should return AvlNode's real height", 1, node1.getHeight());
        assertEquals("This test should return that AvlNode is leaning to the left", -1, avlTree.getBalance(node1));
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(9);
        avlTree.insertAvlNode(node3);
        assertEquals("This test should return AvlNode's real height", 0, node3.getHeight());
        assertEquals("This test should return AvlNode's real height", 1, node1.getHeight());
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node1));
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node3));

        node4 = new AvlNode<>(3);
        avlTree.insertAvlNode(node4);
        assertEquals("This test should return AvlNode's real height", 0, node4.getHeight());
        assertEquals("This test should return AvlNode's real height", 1, node2.getHeight());
        assertEquals("This test should return AvlNode's real height", 2, node1.getHeight());
        assertEquals("This test should return that AvlNode is leaning to the left", -1, avlTree.getBalance(node2));
        assertEquals("This test should return that AvlNode is leaning to the left", -1, avlTree.getBalance(node1));
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node4));

        node5 = new AvlNode<>(5);
        avlTree.insertAvlNode(node5);
        assertEquals("This test should return AvlNode's real height", 0, node5.getHeight());
        assertEquals("This test should return AvlNode's real height", 1, node2.getHeight());
        assertEquals("This test should return AvlNode's real height", 2, node1.getHeight());
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node2));
        assertEquals("This test should return that AvlNode is leaning to the left", -1, avlTree.getBalance(node1));
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node5));

        String tree = " | 7 | 4 | 3 | 5 | 9";
        assertEquals("This test should return  | 7 | 4 | 3 | 5 | 9 ", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3
     */
    @Test
    @DisplayName("")
    public void testInsertingLeftLeftNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals("This test should return AvlNode's real height", 0, node1.getHeight());
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(4);
        avlTree.insertAvlNode(node2);
        assertEquals("This test should return AvlNode's real height", 0, node2.getHeight());
        assertEquals("This test should return that AvlNode is leaning to the right", 1, node1.getHeight());
        assertEquals("This test should return that AvlNode is leaning to the left", -1, avlTree.getBalance(node1));
        assertEquals("This test should return that AvlNode is balanced", 0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(3);
        avlTree.insertAvlNode(node3);
        assertEquals("This test should return AvlNode's top", node2, avlTree.getTop());
        assertEquals("This test should return AvlNode's left tree", node3, node2.getLeft());
        assertEquals("This test should return AvlNode's right tree", node1, node2.getRight());

        assertEquals("This test should return AvlNode's top height", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return AvlNode's left tree's height", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("This test should return that the height of the top node is 0", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("This thest should return that the height of the left child of node1 is -1", -1, avlTree.height(node1.getLeft()));
        assertEquals("This thest should return that the height of the right child of node1 is -1", -1, avlTree.height(node1.getRight()));
        assertEquals("This thest should return that the height of the left child of node3 is -1", -1, avlTree.height(node3.getLeft()));
        assertEquals("This thest should return that the height of the right child of node3 is -1", -1, avlTree.height(node3.getRight()));

        String tree = " | 4 | 3 | 7";
        assertEquals("This test should return that actual tree is | 4 | 3 | 7", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 10 - 14
     */
    @Test
    @DisplayName("")
    public void testInsertingRightRightNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals("This test should return that the height of node1 is 0", 0, node1.getHeight());
        assertEquals("This test should return that the node1 is balanced", 0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(10);
        avlTree.insertAvlNode(node2);
        assertEquals("This test should return that the height of node2 is 0", 0, node2.getHeight());
        assertEquals("This test should return that the height of node1 is 1", 1, node1.getHeight());
        assertEquals("This test should return that the node1 is leaning to the right", 1, avlTree.getBalance(node1));
        assertEquals("This test should return that the node2 is balanced", 0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(14);
        avlTree.insertAvlNode(node3);
        assertEquals("This test should return that node2 should be the top of the tree", node2, avlTree.getTop());
        assertEquals("This test should return that node1 should be the left child of node2", node1, node2.getLeft());
        assertEquals("This test should return that node3 should be the right child of node2", node3, node2.getRight());

        assertEquals("This test should return that the height of the top node is 0", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that the height of the left child of the top node is 0", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("This test should return that the height of the right child of the top node is 0", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("This thest should return that the height of the left child of node1 is -1", -1, avlTree.height(node1.getLeft()));
        assertEquals("This thest should return that the height of the lright child of node1 is -1", -1, avlTree.height(node1.getRight()));
        assertEquals("This thest should return that the height of the left child of node3 is -1", -1, avlTree.height(node3.getLeft()));
        assertEquals("This thest should return that the height of the right child of node3 is -1", -1, avlTree.height(node3.getRight()));

        String tree = " | 10 | 7 | 14";
        assertEquals("This test should return that actual tree is | 10 | 7 | 14", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     */
    @Test
    @DisplayName("Test that inserting 7,4,3,2,1 results on tree | 4 | 2 | 1 | 3 | 7 ")
    public void testInserting7_4_3_2_1() {
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

        assertEquals("This test should return that node2 should be the top of the tree", node2, avlTree.getTop());
        assertEquals("This test should return that node4 should be the left child of node2", node4, node2.getLeft());
        assertEquals("This test should return that node1 should be the right child of node2", node1, node2.getRight());
        assertEquals("This test should return that node5 should be the left child of node4", node5, node4.getLeft());
        assertEquals("This test should return that node3 should be the right child of node4", node3, node4.getRight());
        assertEquals("This test should return that the height of node1 is 0", 0, node1.getHeight());
        assertEquals("This test should return that the height of node2 is 2", 2, node2.getHeight());
        assertEquals("This test should return that the height of node4 is 1", 1, node4.getHeight());

        String tree = " | 4 | 2 | 1 | 3 | 7";
        assertEquals("This test should return that actual tree is | 4 | 2 | 1 | 3 | 7", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 8 - 9 - 10 - 11
     */
    @Test
    @DisplayName("Test that checks that you can insert numbers in numeric order the tree will keep balance")
    public void testInserting7_8_9_10_11() {
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

        assertEquals("This test should return that node2 should be the top of the tree", node2, avlTree.getTop());
        assertEquals("This test should return that node4 should be the right child of node2", node4, node2.getRight());
        assertEquals("This test should return that node1 should be the left child of node2", node1, node2.getLeft());
        assertEquals("This test should return that node5 should be the right child of node4", node5, node4.getRight());
        assertEquals("This test should return that node3 should be the left child of node4", node3, node4.getLeft());
        assertEquals("This test should return that the height of the top is 2", 2, avlTree.getTop().getHeight());
        assertEquals("This test should return that the height of node4 is 1", 1, node4.getHeight());
        assertEquals("This test should return that the height of node1 is 0", 0, node1.getHeight());

        String tree = " | 8 | 7 | 10 | 9 | 11";
        assertEquals("This test should return that actual tree is | 8 | 7 | 10 | 9 | 11", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 2 - 3
     */
    @Test
    @DisplayName("Test that checks that you can insert a left-right nodes and the tree will keep balance")
    public void testInsertingLeftRightNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(2);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(3);
        avlTree.insertAvlNode(node3);

        assertEquals("This test should return that node3 should be the top of the tree", node3, avlTree.getTop());
        assertEquals("This test should return that node2 should be at the left branch of node3", node2, node3.getLeft());
        assertEquals("This test should return that node1 should be at the right branch of node3", node1, node3.getRight());

        assertEquals("This test should return that the height of the top node is 1", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that the height of the left branch of the top is 0", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("This test should return that the height of the right branch of the top is 0", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("This test should return that the height of left child of node2 should be -1", -1, avlTree.height(node2.getLeft()));
        assertEquals("This test should return that the height of right child of node2 should be -1", -1, avlTree.height(node2.getRight()));
        assertEquals("This test should return that the height of left child of node1 should be -1", -1, avlTree.height(node1.getLeft()));
        assertEquals("This test should return that the height of right child of node1 should be -1", -1, avlTree.height(node1.getRight()));

        String tree = " | 3 | 2 | 7";
        assertEquals("This test should return that actual tree is | 3 | 2 | 7", tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 9 - 8
     */
    @Test
    @DisplayName("Test that checks that you can insert a rigth-left nodes and the tree will keep balance")
    public void testInsertingRightLeftNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(8);
        avlTree.insertAvlNode(node3);

        assertEquals("This test should return that node3 should be the top of the tree", node3, avlTree.getTop());
        assertEquals("This test should return that node1 should be at the left branch of node3", node1, node3.getLeft());
        assertEquals("This test should return that node2 should be at the right branch of node3", node2, node3.getRight());

        assertEquals("This test should return that top should have height 1", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that the left branch have height 0", 0,
                avlTree.getTop().getLeft().getHeight());
        assertEquals("This thest should return that the right branch should have height 0", 0,
                avlTree.getTop().getRight().getHeight());
        assertEquals("This test should return that the height of leaf left child of node2 should be -1", -1, avlTree.height(node2.getLeft()));
        assertEquals("This test should return that the height of leaf right child of node2 should be -1", -1, avlTree.height(node2.getRight()));
        assertEquals("This test should return that the height of leaf left child of node1 should be -1", -1, avlTree.height(node1.getLeft()));
        assertEquals("This test should return that the height of leaf right child of node1 should be -1", -1, avlTree.height(node1.getRight()));

        String tree = " | 8 | 7 | 9";
        assertEquals("This test should return that actual tree is | 8 | 7 | 9", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that the method search works correctly")
    public void testSearchNode() {
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

        assertEquals("This test should return that the node1 is the one with the item 7", node1, avlTree.search(7));
        assertEquals("This test should return that the node2 is the one with the item 9", node2, avlTree.search(9));
        assertEquals("This test should return that the node3 is the one with the item 8", node3, avlTree.search(8));
        assertEquals("This test should return that the node created with item 2, really has the item 2", 2,
                avlTree.searchNode(new AvlNode<>(2)).getItem());
        assertEquals("This test should return that the node4 is the one with the item 2", node4, avlTree.search(2));
        assertEquals("This test should return that the node5 is the one with the item 3", node5, avlTree.search(3));
        assertNull("This test should return that the node with the item 14 is null beacuase it wasn't created", avlTree.search(14));
        assertNull("This test should return that the node with the item 0 is null because it wasn't created", avlTree.search(0));

        String tree = " | 8 | 3 | 2 | 7 | 9";
        assertEquals("This test should return that actual tree is | 8 | 3 | 2 | 7 | 9", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Test that finds the successor")
    public void testFindSuccessor() {
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
        assertEquals("This test should return that the succesor of the node with item 8 is the node with item 10", avlTree.search(10), avlTree.findSuccessor(node));
        node = avlTree.search(10);
        assertEquals("This test should return that the succesor of the node with item 10 is the node with item 12", avlTree.search(12), avlTree.findSuccessor(node));
        node = avlTree.search(14);
        assertEquals("This test should return that the succesor of the node with item 14 is the node with item 20", avlTree.search(20), avlTree.findSuccessor(node));

        String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
        assertEquals( "This test should return that actual tree is | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24", tree, avlTree.toString());
    }

    @Test
    @DisplayName("Test that doesnt' find the succesor because it doesn't have one")
    public void findNoSuccesor(){
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        assertNull("Test that should return that the succesor of the top node is null" ,avlTree.findSuccessor(node));

    }

    @Test
    @DisplayName("Test that checks that you can't delete a node that is not in the tree")
    public void testDeletingANonExistingNode(){
        AvlNode<Integer> node1;
        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        assertThrows(NullPointerException.class, () -> avlTree.delete(8));
    }

    @Test
    @DisplayName("Test that checks that you can delete leaf nodes correctly")
    public void testDeletingLeafNodes() {
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
        assertEquals("This test should return that actual tree is | 7 | 2 | 3 | 9 | 8", tree, avlTree.toString());

        avlTree.delete(3); // right leaf node
        assertEquals("This test should return that the right node of node3 is null", null, node3.getRight());
        assertEquals("This test should return that the height of the node3 is 0", 0, node3.getHeight());
        assertEquals("This test should return that the height of the top node is 2", 2, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is | 7 | 2 | 9 | 8", " | 7 | 2 | 9 | 8", avlTree.toString());

        avlTree.delete(8); // left leaf node
        assertEquals("This test should return that the left node of node2 is null", null, node2.getLeft());
        assertEquals("This test should return that the height of the node2 is 0", 0, node2.getHeight());
        assertEquals("This test should return that the height of the top node is 1", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is | 7 | 2 | 9", " | 7 | 2 | 9", avlTree.toString());

        avlTree.delete(2); // left leaf node
        assertEquals("This test should return that the left node of node1 is null", null, node1.getLeft());
        assertEquals("This test should return that the height of the node1 is 1", 1, node1.getHeight());
        assertEquals("This test should return that actual tree is | 7 | 9", " | 7 | 9", avlTree.toString());

        avlTree.delete(9); // right leaf node
        assertEquals("This test should return that the right node of node1 is null", null, node1.getRight());
        assertEquals("This test should return that the height of the node1 is 0", 0, node1.getHeight());
        assertEquals("This test should return that actual tree is | 7", " | 7", avlTree.toString());

        avlTree.delete(7); // left leaf node
        assertEquals("This test should return that the top node is null", null, avlTree.getTop());
        assertEquals("This test should return that actual tree is empty", "", avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that you can delete leaf nodes correctly")
    public void testDeletingLeafNodesr() {
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
        assertEquals("This test should return that actual tree is | 7 | 2 | 3 | 9 | 8", tree, avlTree.toString());

        avlTree.delete(3); // right leaf node
        assertEquals("This test should return that the right node of node3 is null", null, node3.getRight());
        assertEquals("This test should return that the height of the node3 is 0", 0, node3.getHeight());
        assertEquals("This test should return that the height of the top node is 2", 2, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is | 7 | 2 | 9 | 8", " | 7 | 2 | 9 | 8", avlTree.toString());

        avlTree.delete(8); // left leaf node
        assertEquals("This test should return that the left node of node2 is null", null, node2.getLeft());
        assertEquals("This test should return that the height of the node2 is 0", 0, node2.getHeight());
        assertEquals("This test should return that the height of the top node is 1", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is | 7 | 2 | 9", " | 7 | 2 | 9", avlTree.toString());

        avlTree.delete(2); // left leaf node
        assertEquals("This test should return that the left node of node1 is null", null, node1.getLeft());
        assertEquals("This test should return that the height of the node1 is 1", 1, node1.getHeight());
        assertEquals("This test should return that actual tree is | 7 | 9", " | 7 | 9", avlTree.toString());

        avlTree.delete(7); // left leaf node
        assertEquals("This test should return that the right node of node1 is null", null, node1.getRight());
        assertEquals("This test should return that the height of the node1 is 0", 0, node1.getHeight());
        assertEquals("This test should return that actual tree is | 9", " | 9", avlTree.toString());

        avlTree.delete(9); // right leaf node
        assertEquals("This test should return that the top node is null", null, avlTree.getTop());
        assertEquals("This test should return that actual tree is empty", "", avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that you can delete nodes that only have one leaf")
    public void testDeletingNodesWithOneLeaf() {
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
        assertEquals("This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24", tree, avlTree.toString());

        avlTree.delete(2);
        assertEquals("This test should return that the item of node3 is the same as the left of node1", node3.getItem(), node1.getLeft().getItem());
        assertEquals("This test should return that the item of the right node of node3 is null", null, node3.getRight());
        assertEquals("This test should return that the height of right of node3 is 0", 0, node3.getHeight());
        assertEquals("This test should return that the height of the top is 2", 2, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24", " | 7 | 3 | 9 | 8", avlTree.toString());

        avlTree.delete(9);
        assertEquals("This test should return that the item of node2 is the same of the right node of node 1", node2.getItem(), node1.getRight().getItem());
        assertEquals("This test should return that the item of the left node of node2 is null", null, node2.getLeft());
        assertEquals("This test should return that the height of node2 is 0", 0, node2.getHeight());
        assertEquals("This test should return that the height of the top is 1", 1, avlTree.getTop().getHeight());
        assertEquals("This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24", " | 7 | 3 | 8", avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that you can delete nodes with two leaves correctly")
    public void testDeletingNodesWithTwoLeaves() {
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
        assertEquals("This test should return that the actual tree is | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24", expected, avlTree.toString());

        avlTree.delete(12);
        node = avlTree.search(8);
        assertEquals("This test should return that the item of the right node of node is 14", 14, node.getRight().getItem());
        assertEquals("This test should return that actual tree is | 20 | 8 | 4 | 14 | 10 | 22 | 24", " | 20 | 8 | 4 | 14 | 10 | 22 | 24",
                avlTree.toString());

        avlTree.delete(8);
        assertEquals("This test should return that the item of the left node of the top of the node is 10", 10, avlTree.getTop().getLeft().getItem());
        assertEquals("This test should return that actual tree is  | 20 | 10 | 4 | 14 | 22 | 24", " | 20 | 10 | 4 | 14 | 22 | 24",
                avlTree.toString());
    }

    @Test
    @DisplayName("Test that checks that you can delete nodes and the tree will keep balanced")
    public void testDeletingAndRebalancing() {
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

        assertEquals("This test should return that the height of top is 3", 3, avlTree.getTop().getHeight());

        avlTree.delete(22);
        assertEquals("This test should return that the item of top is 12", 12, avlTree.getTop().getItem());
        assertEquals("This test should return that the item of the left node of top is 8", avlTree.search(8), avlTree.getTop().getLeft());
        assertEquals("This test should return that the item of the right node of top is 20", avlTree.search(20), avlTree.getTop().getRight());
    }

    @Test
    @DisplayName("Test that checks that you can delete a top node correctly")
    public void testDeletingTopNode() {
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

        assertEquals("This test should return that the height of top is 3", 3, avlTree.getTop().getHeight());

        avlTree.delete(20);
        assertEquals("This test should return that the final tree is | 12 | 8 | 4 | 10 | 22 | 14 | 24", " | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
    }

    @Nested
    @DisplayName("When calling size() method of a tree")
    class TreeSizeTests{
        @Test
        @DisplayName("empty tree means it has 0 elements")
        public void emptyTreeSizeTest(){
            int expectedSize = 0;
            assertEquals("It should give 0", expectedSize, avlTree.size());
        }

        @Test
        @DisplayName("tree containing only root means it has 1 element")
        public void oneElementTreeSizeTest(){
            AvlNode<Integer> root = new AvlNode<>(2);
            avlTree.insertAvlNode(root);

            int expectedSize = 1;
            assertEquals("It should give 0", expectedSize,avlTree.size() );
        }

        @Test
        @DisplayName("it calculates right number of elements")
        public void sizeWorksFineWithMoreElements(){
            avlTree.insert(2);
            avlTree.insert(3);

            int expectedSize = 2;
            assertEquals("It should give 2",  expectedSize, avlTree.size());
        }
    }
}