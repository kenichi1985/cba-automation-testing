package apiTest;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class Request {

    final String API_KEY = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJsdWtpZTk5X2tleSIsImVtYWlsIjoid2VuZ2xvb25ndGFpQHlhaG9vLmNvbSIsImlhdCI6MTY1NDUwMzQ2NSwiZXhwIjoxNjU0NzYyNjY1fQ.KfzWRYdHSyu0H8H25AsyRPhdw249BVJtBk8ZEWrO6RlLbVb2GFHLuSCB9BN3ssI7B0Ak6wxPrljvOVclv_yuWg";
    final String BASE_URI = "https://supervillain.herokuapp.com/";

    Map<String, String> requestHeaders;

    public int verifyToken(){

        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("accept", "application/json");

        Response response = given().headers(requestHeaders).get("/auth/verifytoken").thenReturn();

        int statusCode= response.statusCode();

        System.out.println(response.getBody().asString());
        
        System.out.println("Status Code: "+statusCode);

        return statusCode;

    }

    public int registerUser(String username, String password){
        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("accept", "*/*");

        JSONObject body = new JSONObject();

        body.put("username", username);
        body.put("password", password);
        
        //System.out.println("JSON: "+body.toJSONString());

        Response response = given().headers(requestHeaders).body(body.toJSONString()).post("/auth/user/register").thenReturn();

        int statusCode= response.statusCode();

        System.out.println(response.getBody().asString());

        System.out.println("Status Code: "+statusCode);

        return statusCode;


    }


    public int loginUser(String username, String password){
        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("accept", "application/json");

        JSONObject body = new JSONObject();

        body.put("username", username);
        body.put("password", password);
        
        //System.out.println("JSON: "+body.toJSONString());

        Response response = given().headers(requestHeaders).body(body.toJSONString()).post("/auth/user/login").thenReturn();

        int statusCode= response.statusCode();

        System.out.println(response.getBody().asString());

        System.out.println("Status Code: "+statusCode);

        return statusCode;

    }

    public int getUsers() throws ParseException{

        //int score=0;
        // int strLoc=0;
        // boolean condition = true;
        // String scoreStr="";
        // String scoreString ="";

        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("accept", "application/json");

        Response response = given().headers(requestHeaders).get("/v1/user").thenReturn();

        int statusCode= response.statusCode();

        //String bodyStr = response.getBody().asString().replaceAll("[^a-zA-Z0-9]+","").trim();

        //if(statusCode==200){

            // do{

            //     strLoc = bodyStr.indexOf(username);
    
            //     scoreStr=bodyStr.substring(strLoc+username.length(), strLoc+username.length()+5);
            //     System.out.println("score str: "+scoreStr);
    
            //     if(scoreStr.equalsIgnoreCase("score")){
            //         condition=false;
    
    
            //         bodyStr= bodyStr.substring(strLoc+username.length()+5, bodyStr.length());
    
            //         scoreString= bodyStr.substring(0, bodyStr.indexOf("username"));
            //         //System.out.println("my score: "+scoreString);
            //     }
            //     else{
            //         bodyStr = bodyStr.substring(strLoc+username.length()+5, bodyStr.length());
            //         //System.out.println(bodyStr);
            //     }
    
    
            // }while(condition);
            
            //score=Integer.parseInt(scoreString);
            
        //}
    
       

        //int[] result = {statusCode, score};

        return statusCode;

    }


    public int addUser(String username, int score){
        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("accept", "*/*");

        JSONObject body = new JSONObject();

        body.put("username", username);
        body.put("score", score);
        
        //System.out.println("JSON: "+body.toJSONString());

        Response response = given().headers(requestHeaders).body(body.toJSONString()).post("/v1/user").thenReturn();

        int statusCode= response.statusCode();

        System.out.println(response.getBody().asString());

        System.out.println("Status Code: "+statusCode);

        return statusCode;

    }

    public int updateUser(String username, int newScore){

        baseURI=BASE_URI;

        requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", API_KEY);
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("accept", "*/*");

        JSONObject body = new JSONObject();

        body.put("username", username);
        body.put("score", newScore);
        

        Response response = given().headers(requestHeaders).body(body.toJSONString()).put("/v1/user").thenReturn();

        int statusCode= response.statusCode();

        System.out.println(response.getBody().asString());

        System.out.println("Status Code: "+statusCode);

        return statusCode;

    }


     






    // public TimeStamp timeStampConversion(String ){


    //     return null;

    // }


    
}
