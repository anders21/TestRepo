package shoppingproject.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import shoppingproject.domain.Product;

/**
 *
 * @author jamal572
 */
@RunWith(Parameterized.class)
public class DaoTester {

    //Test database url
    private static final String dbTestUrl = "jdbc:h2:tcp://localhost:9420/project-testing;IFEXISTS=TRUE";

    //create instance of DAO to test DB CRUD methods
    private IDAO dbTest; //= new DbDao(dbTestUrl);

    //product one to test
    private Product product1;

    //product two to test
    private Product product2;

    public DaoTester(IDAO dbtest) {
        dbTest = dbtest;
    }

    @Parameterized.Parameters
    public static Collection<?> daoObjectsToTest() {
        return Arrays.asList(new Object[][]{
            {new ListDao()},
            {new DbDao(dbTestUrl)}
        });
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //initialize our test product1 with data
        this.product1 = new Product(111, "product1", "description1", "category1", 111.111, 111.111);

        //initialize our test product2 with data
        this.product2 = new Product(222, "product2", "description2", "category2", 222.222, 222.222);

        //save the products to database
        dbTest.addProduct(product1);
        dbTest.addProduct(product2);
    }

    @After
    public void tearDown() {
        //delete our two test products after testing has completed
        dbTest.deleteProduct(product1);
        dbTest.deleteProduct(product2);
    }

    @Test
    public void testDaoGetProductList() {
        //fetch all products in the database;
        Collection<Product> products = dbTest.getProductsList();

        //test that the two products added in setup exist after fetching them
        assertTrue("product1 should exist", products.contains(product1));
        assertTrue("product2 should exist", products.contains(product2));

        //ensure that we have only added the two test products
        assertEquals("Only two products should be in result: ", 2, products.size());

        //find product1 in our returned collection and ensure that all details
        //of the fetched product match the product details used to write
        for (Product product : products) {
            if (product.equals(product1)) {
                assertEquals(product1.getProductID(), product.getProductID());
                assertEquals(product1.getName(), product.getName());
                assertEquals(product1.getDescription(), product.getDescription());
                assertEquals(product1.getCategory(), product.getCategory());
                //note that 0.3 is used to round to 3 decimal places
                assertEquals(product1.getPrice(), product.getPrice(), 0.3);
                assertEquals(product1.getQuantityInStock(), product.getQuantityInStock(), 0.3);
            }
        }
    }

    @Test
    public void testDaoFindById() {
        //Fetch product from db using the local Product models get ID method
        Product testProduct1 = dbTest.getProduct(product1.getProductID());

        //Test local product1 Model against product1 stored in database
        /*
         Note that assertEquals uses equals interface and utilises products equals implemented version
         and this implementation only compares ID, so we should test against all of the product attributes
         */
        assertEquals("product1 should exist and be returned using getProduct", product1, testProduct1);

        //Testing against all product attributes
        assertEquals(product1.getName(), testProduct1.getName());
        assertEquals(product1.getDescription(), testProduct1.getDescription());
        assertEquals(product1.getCategory(), testProduct1.getCategory());
        //note that 0.3 is used to round to 3 decimal places
        assertEquals(product1.getPrice(), testProduct1.getPrice(), 0.3);
        assertEquals(product1.getQuantityInStock(), testProduct1.getQuantityInStock(), 0.3);

        //fetch product from database that does not exist using and test it fails
        Product testNoProduct = dbTest.getProduct(999);
        assertNull(testNoProduct);

    }
    //TODO getCategoryList(), updateProduct, Filter by category.

    @Test
    public void testGetCategoryList() {
        Collection<String> testProductCategoryList = dbTest.getCategoryList();

        //test that only two categories are returned
        assertEquals("Only two categories should be in result: ", 2, testProductCategoryList.size());

        //test that product1 category matches fetched category from database
        assertTrue("Ensure that category1 exists", testProductCategoryList.contains(product1.getCategory()));

        //test that product2 category matches fetched category from database
        assertTrue("Ensure that category2 exists", testProductCategoryList.contains(product2.getCategory()));
    }

    @Test
    public void testAddAndDeleteProduct() {
        //create an instance of Product to test add and delete methods
        Product saveProduct = new Product(123, "name", "description", "category", 123.123, 123.123);

        //add savedProduct to db
        dbTest.addProduct(saveProduct);

        //retrieve product after adding using productID
        Product retrievedProduct = dbTest.getProduct(123);

        assertEquals("Testing instance of test product against retrieved instance", saveProduct, retrievedProduct);

        //delete product using instance of saveProduct
        dbTest.deleteProduct(saveProduct);

        retrievedProduct = dbTest.getProduct(123);
        assertNull("Ensure the object deleted is deleted after trying to retrieve it", retrievedProduct);
    }

     @Test
    public void testUpdateProduct() {
        String updateProduct1Name = "productOne";
        String updateProduct1Description = "descriptionOne";

        //fetch product
        Product productToUpdate = dbTest.getProduct(product1.getProductID());

        //set the new product name
        productToUpdate.setName(updateProduct1Name);

        //set the new product description
        productToUpdate.setDescription(updateProduct1Description);

        //with new name and description update the product with updateProduct
        dbTest.updateProduct(productToUpdate);

        //fetch the product after an update and check the changes have persisted
        Product updatedProduct = dbTest.getProduct(product1.getProductID());

        //check name has change
        assertEquals("Checking that new name is not the same as old", 
                updateProduct1Name, updatedProduct.getName());

        //check description has changed
        assertEquals("Checking that new name is not the same as old",
                updateProduct1Description, updatedProduct.getDescription());
    }

     @Test
    public void testGetByCategory() {
        //Create local paramaters which are copies of our test product categories
        String testCategory1 = "category1";
        String testCategory2 = "category2";
        
        //Use the getByCategory dao method to fetch a collection of each category
        Collection<Product> testGetCategory1 = dbTest.getByCategory(testCategory1);
        Collection<Product> testGetCategory2 = dbTest.getByCategory(testCategory2);
        
        /*
        Since there are two different categories tha size of each collection 
        returned should 1 sinze there are two unique categories
        */
        assertEquals("Number of items for category1 should be:", 1, testGetCategory1.size());
        assertEquals("Number of items for category1 should be:", 1, testGetCategory2.size());
        
        //Test that the fetched products category matches our local product category 
        assertTrue("The product fetched should match our local one", testGetCategory1.contains(product1));
        assertTrue("The product fetched should match our local one", testGetCategory2.contains(product2));
        
        //we should also test that the returned products dont overlap into wrong categorys
        assertFalse("The product fetched should match our local one", testGetCategory1.contains(product2));
        assertFalse("The product fetched should match our local one", testGetCategory2.contains(product1));
    }
}
