package dao.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBJDBC{

    public static void MongoConnect(){
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("myPosition");
            System.out.println("Connect to database successfully");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
   public static void main( String args[] ){
      try{   
       // 连接到 mongodb 服务
          MongoClient mongoClient = new MongoClient("localhost", 27017);
         // 连接到数据库
          MongoDatabase mongoDatabase = mongoClient.getDatabase("myPosition");
          System.out.println("Connect to database successfully");

        
      }catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     }
   }
}