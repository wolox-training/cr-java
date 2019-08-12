package com.wolox.training.constants;

public class SwaggerMessages {
    public static final  String findSuccess = "Successfully found resource";
    public static final  String createSuccess = "Successfully created resource";
    public static final  String updateSuccess = "Successfully updated resource";
    public static final  String deleteSuccess = "Successfully deleted resources";
    public static final  String linkSuccess = "Successfully linked resources";
    public static final  String unlinkSuccess = "Successfully unlinked resources";


    public static final  String badRequest = "The resource could not be retrieved for bad request";
    public static final  String notFound = "The resource you were trying to reach is not found";
    public static final  String internalServerError = "The resource could not be retrieved for internal server error";

    private SwaggerMessages(){
        throw new AssertionError();
    }
}
