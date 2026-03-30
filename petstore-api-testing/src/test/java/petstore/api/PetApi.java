package petstore.api;

import io.restassured.response.Response;
import petstore.models.Pet;

import static io.restassured.RestAssured.given;

public class PetApi {

    public Response addNewPet(Pet petPayload) {
        return given()
                .contentType("application/json")
                .body(petPayload)
               .when()
                .post("/pet");
    }

    public Response getPetById(int petId) {
        return given()
                .pathParam("petId", petId)
               .when()
                .get("/pet/{petId}");
    }

    public Response updatePet(Pet petPayload) {
        return given()
                .contentType("application/json")
                .body(petPayload)
               .when()
                .put("/pet");
    }

    public Response deletePet(int petId) {
        return given()
                .pathParam("petId", petId)
               .when()
                .delete("/pet/{petId}");
    }
}