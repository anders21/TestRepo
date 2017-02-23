/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingproject.gui;

import gui.helpers.SimpleListModel;
import java.util.Collection;
import java.util.TreeSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import static org.assertj.swing.core.matcher.DialogMatcher.withTitle;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import shoppingproject.dao.IDAO;
import shoppingproject.domain.Product;

/**
 *
 * @author jay
 */
public class ProductEditorTest {

    private IDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    private Product javaFood;
    private Product javaShirt;

    public ProductEditorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        //slow down the robot
        robot.settings().delayBetweenEvents(5);

        //add some categories to combo box for testing with
        Collection<String> categories = new TreeSet<>();
        categories.add("food");
        categories.add("cloths");
        
        //creat some virtual dao storage systems
        Collection<Product> products = new TreeSet<>();
        Collection<Product> filteredProducts = new TreeSet<>();
        
        //creat an instance of our product to add and then edit/update 
        javaFood = new Product(888, "JavaFood", "food for JVM", "food", 888.888, 888.888);
        javaShirt = new Product(111, "JavaShirt", "shirt for JVM", "cloths", 111.111, 111.111);

        products.add(javaFood);
        products.add(javaShirt);
        
        //used for the test of filtering by product category
        filteredProducts.add(javaFood);
        
        //create a mock for the dao using the IDAO inteface
        dao = Mockito.mock(IDAO.class);

        //stub the getCategoryList() and getProductsList() methods to return the test categories
        when(dao.getCategoryList()).thenReturn(categories);
        when(dao.getProductsList()).thenReturn(products);
        
        //stub the getProduct() for searching by ID
        when(dao.getProduct(javaFood.getProductID())).thenReturn(javaFood);
        
        //stub the getByCategory() for filtering by Category
        when(dao.getByCategory(javaFood.getCategory())).thenReturn(filteredProducts);
        
        // stub the deleteProduct method
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                products.remove(javaShirt);
                return null;
            }
        }).when(dao).deleteProduct(javaShirt);
    }

    @After
    public void tearDown() {
        //clean the fixture to avoid malformed tests with data not relevant to next test
        fixture.cleanUp();
    }
    
    @Test
    public void testViewAll(){
        //Create ProductEditor dialog using and use assertJ to control it
        ProductReport reportDialog = new ProductReport(null, true, dao);
        fixture = new DialogFixture(robot, reportDialog);
        fixture.show().requireVisible();
        
        //get the model
        SimpleListModel productListModel = (SimpleListModel) fixture.list("lstProducts").target().getModel();
        assertTrue("List only contains product filtered for: ", productListModel.contains(javaFood));
        assertTrue("List only contains product filtered for: ", productListModel.contains(javaShirt));
        assertEquals("List should onyl contain one item after filtering by category: ", 2, productListModel.getSize());
        
        verify(dao).getProductsList();
    }

    @Test
    public void testSaveProduct() {
        //Create ProductEditor dialog using and use assertJ to control it
        ProductEditor editorDialog = new ProductEditor(null, true, dao);
        fixture = new DialogFixture(robot, editorDialog);
        fixture.show().requireVisible();

        //use the robot to enter details into fields
        fixture.textBox("txtID").enterText("666");
        fixture.textBox("txtName").enterText("JavaFood");
        fixture.textBox("txtDescription").enterText("food for JVM");
        fixture.comboBox("cmbCategory").enterText("food");
        fixture.textBox("txtPrice").enterText("666.666");
        fixture.textBox("txtStock").enterText("666.666");

        //click the save button to save the product to our mock dao
        fixture.button("btnSave").click();

        //create and argument capture to access the passed Product from the mocked DAO
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);

        //check the that addProduct() method was called and capture the saved Product from the mocked DAO
        verify(dao).addProduct(productArgument.capture());

        //get the saved Product into a local instance for testing
        Product savedProduct = productArgument.getValue();

        assertEquals("Ensure the product ID was saved: ", new Integer(666), savedProduct.getProductID());
        assertEquals("Ensure the product Name was saved: ", "JavaFood", savedProduct.getName());
        assertEquals("Ensure the product description was saved: ", "food for JVM", savedProduct.getDescription());
        assertEquals("Ensure the product category was saved: ", "food", savedProduct.getCategory());
        //note that 0.3 is used to round to 3 decimal places
        assertEquals("Ensure the product price was saved: ", 666.666, savedProduct.getPrice(), 0.3);
        assertEquals("Ensure the product stock was saved: ", 666.666, savedProduct.getQuantityInStock(), 0.3);
    }


    @Test
    public void testDeleteProduct() {
        //create instance of our report Dialoge which will also create and instance of edit dialog
        ProductReport reportDialog = new ProductReport(null, true, dao);

        //creat a fixture dialog using and use assertJ to control it
        fixture = new DialogFixture(robot, reportDialog);
        fixture.show().requireVisible();

        //select the specificed item to edit and ensure it is selected before proceeding to click edit
        fixture.list("lstProducts").selectItem(javaShirt.toString());
        fixture.list("lstProducts").requireSelection(javaShirt.toString());

        //delete the selected product
        fixture.button("btnDelete").click();

        // ensure a confirmation dialog is displayed
        DialogFixture confirmDialog = fixture.dialog(withTitle("Select an Option").andShowing()).requireVisible();

        // click the Yes button on the confirmation dialog
        confirmDialog.button(withText("Yes")).click();

        //create and argument capture to access the passed Product from the mocked DAO
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);

        //check the that addProduct() method was called and capture the saved Product from the mocked DAO
        verify(dao).deleteProduct(productArgument.capture());

        //check that after deletion 
        // get the model
        SimpleListModel productListModel = (SimpleListModel) fixture.list("lstProducts").target().getModel();

        // check the contents
        assertFalse("List does not contain our deleted product", productListModel.contains(javaShirt));
        assertEquals("List should only contain 1 item after deleting two of the added items", 1, productListModel.getSize());
    }

    @Test
    public void testSearchProduct() {
        //create instance of our report Dialoge which will also create and instance of edit dialog
        ProductReport reportDialog = new ProductReport(null, true, dao);

        //creat a fixture dialog using and use assertJ to control it
        fixture = new DialogFixture(robot, reportDialog);
        fixture.show().requireVisible();

        //direct the robot to enter an ID into the search field
        fixture.textBox("txtSearch").enterText("888");

        //delete the selected product
        fixture.button("btnSearch").click();

        //get the model
        SimpleListModel productListModel = (SimpleListModel) fixture.list("lstProducts").target().getModel();
        assertTrue("List only contains product searched for: ", productListModel.contains(javaFood));
        assertEquals("List should onyl contain one item after searching by ID: ", 1, productListModel.getSize());
     
        //check the that addProduct() method was called and capture the saved Product from the mocked DAO
        verify(dao).getProduct(888);
    }
     
     @Test
     public void testFilterProduct(){
        //create instance of our report Dialoge which will also create and instance of edit dialog
        ProductReport reportDialog = new ProductReport(null, true, dao);

        //creat a fixture dialog using and use assertJ to control it
        fixture = new DialogFixture(robot, reportDialog);
        fixture.show().requireVisible();

        //direct the robot to enter an ID into the search field
        fixture.comboBox("cmbFilter").selectItem("food");
        fixture.comboBox("cmbFilter").requireSelection("food");

        //delete the selected product
        fixture.button("btnFilter").click();

        //create and argument capture to access the passed product search ID from the mocked DAO
        ArgumentCaptor<String> filterArgument = ArgumentCaptor.forClass(String.class);

        //check the that addProduct() method was called and capture the saved Product from the mocked DAO
        verify(dao).getByCategory(filterArgument.capture());

        String productFilterString = filterArgument.getValue();

        //get the model
        SimpleListModel productListModel = (SimpleListModel) fixture.list("lstProducts").target().getModel();
        assertTrue("List only contains product filtered for: ", productListModel.contains(javaFood));
        assertEquals("List should onyl contain one item after filtering by category: ", 1, productListModel.getSize());
        assertEquals("Ensure that the product filter string matches the returned product category", productFilterString, javaFood.getCategory());
     }
     
    @Test
    public void testEditProduct() {

        //create instance of our report Dialoge which will also create and instance of edit dialog
        ProductReport reportDialog = new ProductReport(null, true, dao);

        //creat a fixture dialog using and use assertJ to control it
        fixture = new DialogFixture(robot, reportDialog);
        fixture.show().requireVisible();

        //select the specificed item to edit and ensure it is selected before proceeding to click edit
        fixture.list("lstProducts").selectItem(javaFood.toString());
        fixture.list("lstProducts").requireSelection(javaFood.toString());

        //click the edit button
        fixture.button("btnEdit").click();

        //ensure that all the require text for the select item exists for making changes
        fixture.textBox("txtID").requireText("888");
        fixture.textBox("txtName").requireText("JavaFood");
        fixture.textBox("txtDescription").requireText("food for JVM");
        fixture.comboBox("cmbCategory").selectItem("food");
        fixture.comboBox("cmbCategory").requireSelection("food");
        fixture.textBox("txtPrice").requireText("888.89");
        fixture.textBox("txtStock").requireText("888.89");

        //the edit product dialog will appear, clear the existing product name and replace with new name
        fixture.textBox("txtName").deleteText();
        fixture.textBox("txtName").enterText("Java Food");

        //click the save button to save changes
        fixture.button("btnSave").click();

        //create and argument capture to access the passed Product from the mocked DAO
        ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);

        //check the that addProduct() method was called and capture the updated Product from the mocked DAO
        verify(dao).addProduct(productArgument.capture());

        //get the saved Product into a local instance for testing
        Product savedProduct = productArgument.getValue();

        assertEquals("Ensure that the name change has persisted: ", javaFood.getName(), savedProduct.getName());
    }

}
//Unable to find item matching the value 'productID: 888 name: JavaFood Price:888.888' among the JList contents [" productID: 888 name: JavaFood Price:888.888"]
//org.assertj.swing.exception.LocationUnavailableException
//	at org.assertj.swing.driver.JListDriver.failMatchingNotFound(JListDriver.java:829)
//	at org.assertj.swing.driver.JListDriver.checkItemFound(JListDriver.java:651)
//	at org.assertj.swing.driver.JListDriver.selectItem(JListDriver.java:214)
//	at org.assertj.swing.driver.JListDriver.selectItem(JListDriver.java:192)
//	at org.assertj.swing.fixture.JListFixture.selectItem(JListFixture.java:192)
//	at shoppingproject.gui.ProductEditorTest.testEditProduct(ProductEditorTest.java:127)
