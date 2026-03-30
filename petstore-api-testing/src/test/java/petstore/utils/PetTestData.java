package petstore.utils;

import java.util.Arrays;
import java.util.List;

public class PetTestData {
    public static final int PET_ID = 123; 
    public static final int CATEGORY_ID = 1;
    public static final int TAG_ID = 1;


    public static final String CATEGORY_NAME = "Dogs";
    public static final String TAG_NAME = "friendly";
    public static final String INITIAL_PET_NAME = "doggie";
    public static final String UPDATED_PET_NAME = "super-doggie";
    public static final String INITIAL_STATUS = "available";
    public static final String UPDATED_STATUS = "sold";
    public static final List<String> PHOTO_URLS = Arrays.asList("https://example.com/photo.jpg");


    public static final String NOT_FOUND_MESSAGE = "Pet not found";

 
    public static final String PET_ENDPOINT = "/pet";
    public static final String PET_BY_ID_ENDPOINT = "/pet/{petId}";
}