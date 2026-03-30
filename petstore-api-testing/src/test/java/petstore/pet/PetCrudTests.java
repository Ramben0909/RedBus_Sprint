package petstore.pet;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import petstore.base.BaseTest;
import petstore.models.Category;
import petstore.models.Pet;
import petstore.models.Tag;
import petstore.utils.PetTestData;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetCrudTests extends BaseTest {

    @Test(priority = 1)
    public void testAddNewPet() {
        Category category = new Category(PetTestData.CATEGORY_ID, PetTestData.CATEGORY_NAME);
        Tag tag = new Tag(PetTestData.TAG_ID, PetTestData.TAG_NAME);
        Pet petPayload = new Pet(PetTestData.PET_ID, category, PetTestData.INITIAL_PET_NAME, PetTestData.PHOTO_URLS, Arrays.asList(tag), PetTestData.INITIAL_STATUS);

        given()
            .contentType(ContentType.JSON)
            .body(petPayload) 
        .when()
            .post(PetTestData.PET_ENDPOINT)
        .then()
            .statusCode(200)
            .body("id", equalTo(PetTestData.PET_ID)) 
            .body("name", equalTo(PetTestData.INITIAL_PET_NAME))
            .body("status", equalTo(PetTestData.INITIAL_STATUS))
            .log().all();
    }

    @Test(priority = 2)
    public void testFindPetById() {
        given()
            .pathParam("petId", PetTestData.PET_ID)
        .when()
            .get(PetTestData.PET_BY_ID_ENDPOINT)
        .then()
            .statusCode(200)
            .body("id", equalTo(PetTestData.PET_ID))
            .body("name", equalTo(PetTestData.INITIAL_PET_NAME))
            .log().all();
    }

    @Test(priority = 3)
    public void testUpdateExistingPet() {
        Category category = new Category(PetTestData.CATEGORY_ID, PetTestData.CATEGORY_NAME);
        Tag tag = new Tag(PetTestData.TAG_ID, PetTestData.TAG_NAME);
        Pet updatedPetPayload = new Pet(PetTestData.PET_ID, category, PetTestData.UPDATED_PET_NAME, PetTestData.PHOTO_URLS, Arrays.asList(tag), PetTestData.UPDATED_STATUS);

        given()
            .contentType(ContentType.JSON)
            .body(updatedPetPayload)
        .when()
            .put(PetTestData.PET_ENDPOINT)
        .then()
            .statusCode(200)
            .body("name", equalTo(PetTestData.UPDATED_PET_NAME))
            .body("status", equalTo(PetTestData.UPDATED_STATUS))
            .log().all();
    }

    @Test(priority = 4)
    public void testDeletePet() {
        given()
            .pathParam("petId", PetTestData.PET_ID)
        .when()
            .delete(PetTestData.PET_BY_ID_ENDPOINT)
        .then()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("message", equalTo(String.valueOf(PetTestData.PET_ID))) 
            .log().all();
    }
    
    @Test(priority = 5)
    public void testVerifyPetIsDeleted() {
        given()
            .pathParam("petId", PetTestData.PET_ID)
        .when()
            .get(PetTestData.PET_BY_ID_ENDPOINT)
        .then()
            .statusCode(404)
            .body("message", equalTo(PetTestData.NOT_FOUND_MESSAGE))
            .log().all();
    }
}