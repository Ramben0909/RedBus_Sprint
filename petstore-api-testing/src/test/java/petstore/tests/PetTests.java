package petstore.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import petstore.api.PetApi;
import petstore.base.BaseTest;
import petstore.models.Category;
import petstore.models.Pet;
import petstore.models.Tag;
import petstore.utils.PetTestData;

import java.util.Arrays;

public class PetTests extends BaseTest{

    private PetApi petApi;

    @BeforeClass
    public void init() {
    	petApi = new PetApi();
    }

    @Test(priority = 1)
    public void testAddNewPet() {
       
        Category category = new Category(PetTestData.CATEGORY_ID, PetTestData.CATEGORY_NAME);
        Tag tag = new Tag(PetTestData.TAG_ID, PetTestData.TAG_NAME);
        Pet petPayload = new Pet(PetTestData.PET_ID, category, PetTestData.INITIAL_PET_NAME, PetTestData.PHOTO_URLS, Arrays.asList(tag), PetTestData.INITIAL_STATUS);

       
        Response response = petApi.addNewPet(petPayload);
        response.then().log().all();

        // 3. Assert (Validate response)
        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getInt("id"), PetTestData.PET_ID, "Pet ID mismatch");
        Assert.assertEquals(response.jsonPath().getString("name"), PetTestData.INITIAL_PET_NAME, "Pet name mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), PetTestData.INITIAL_STATUS, "Pet status mismatch");
    }

    @Test(priority = 2)
    public void testGetPetById() {
        Response response = petApi.getPetById(PetTestData.PET_ID);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getInt("id"), PetTestData.PET_ID, "Pet ID mismatch");
        Assert.assertEquals(response.jsonPath().getString("name"), PetTestData.INITIAL_PET_NAME, "Pet name mismatch");
    }

    @Test(priority = 3)
    public void testUpdateExistingPet() {
        Category category = new Category(PetTestData.CATEGORY_ID, PetTestData.CATEGORY_NAME);
        Tag tag = new Tag(PetTestData.TAG_ID, PetTestData.TAG_NAME);
        Pet updatedPetPayload = new Pet(PetTestData.PET_ID, category, PetTestData.UPDATED_PET_NAME, PetTestData.PHOTO_URLS, Arrays.asList(tag), PetTestData.UPDATED_STATUS);

        Response response = petApi.updatePet(updatedPetPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getString("name"), PetTestData.UPDATED_PET_NAME, "Pet name mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), PetTestData.UPDATED_STATUS, "Pet status mismatch");
    }

    @Test(priority = 4)
    public void testDeletePet() {
        Response response = petApi.deletePet(PetTestData.PET_ID);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getInt("code"), 200, "API Code mismatch");
        Assert.assertEquals(response.jsonPath().getString("message"), String.valueOf(PetTestData.PET_ID), "Message mismatch");
    }
    
    @Test(priority = 5)
    public void testVerifyPetIsDeleted() {
        Response response = petApi.getPetById(PetTestData.PET_ID);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 404, "Expected a 404 Not Found");
        Assert.assertEquals(response.jsonPath().getString("message"), PetTestData.NOT_FOUND_MESSAGE, "Error message mismatch");
    }
}