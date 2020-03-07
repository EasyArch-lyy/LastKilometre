package mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import util.MongoDBUtil;

public class MongoCRUD {

    private static MongoDatabase mongoDatabase=MongoDBUtil.getConnect();

   /**
    * 创建集合
    * @param ScopeName 地点范围名
    */
    public void connectCollection(String ScopeName){
        //这里的 "test" 表示集合的名字，如果指定的集合不存在，mongoDB将会在第一次插入文档时创建集合。
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection(ScopeName);
    }

    //插入数据
//   /**
//    * @param documents Document集合
//    */
//    @Test
//    public void insertArray(Document[]documents){
//        //获取数据库连接对象
//        MongoDatabase mongoDatabase=MongoDBUtil.getConnect();
//        //获取集合
//        MongoCollection<Document> collection = mongoDatabase.getCollection("test");
//        for(Document d:documents){
//            //插入一个文档
//            collection.insertOne(d);
//        }
//    }

    @Test
    public void insertOne(){
//        MongoDatabase mongoDatabase=MongoDBUtil.getConnect();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Document document = new Document("name","李四")
                .append("sex", "男")
                .append("age", 15);
        collection.insertOne(document);
    }
    //删除与筛选器匹配的单个文档,使用MongoCollection对象的deleteOne()方法,
    //该方法接收一个数据类型为Bson的对象作为过滤器筛选出需要删除的文档。
    //然后删除第一个。为了便于创建过滤器对象,JDBC驱动程序提供了Filters类。
    //删除与筛选器匹配的单个文档
    @Test
    public void deleteOne(){
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //申明删除条件
        org.bson.conversions.Bson filter = Filters.eq("name","张三");
        //删除与筛选器匹配的单个文档
        collection.deleteOne(filter);
    }

    //使用MongoCollection对象的find()方法,该方法有多个重载方法,可以使用不带参数的find()方法查询集合中的所有文档,也可以通过传递一个Bson类型的过滤器查询符合条件的文档。这几个重载方法均返回一个FindIterable类型的对象,可通过该对象遍历出查询到的所有文档
    //查找集合中的所有文档
    @Test
    public void findList(){
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

   /**
    * 指定查询过滤器查询
    */
    @Test
    public void FilterfindAge(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //指定查询过滤器
        Bson filter = Filters.eq("age", 18);
        //指定查询过滤器查询
        FindIterable findIterable = collection.find(filter);
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    //first()方法取出查询到的第一个文档
    @Test
    public void findTest() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        //取出查询到的第一个文档
        Document document = (Document) findIterable.first();
        //打印输出
        System.out.println(document);
    }

}

