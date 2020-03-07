package elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    private static final Logger log = LoggerFactory.getLogger(Test.class);
    /*本机IP地址*/
    public final static String HOST="127.0.0.1";
    public final static int PORT=9300;
    /*节点名，安装好后默认的节点名*/
    public final static String CLUSTERNAME="logsearch";

    /*获取链接*/
    public static TransportClient getConnection()throws Exception{
        Settings settings= Settings.builder().put("client.transport.sniff", true)
                .put("cluster.name",CLUSTERNAME)
                .build();
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));

        return client;
    }

    /*添加数据*/
    public void add() throws Exception{
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("name","LYC")
                    .field("age",24)
                    .field("job","coder")
                    .endObject();
            String index = "data";   // 索引值
            String type ="person";   // 类型
            String id="1";           // id值
            TransportClient client = this.getConnection();
            IndexResponse iresp = client.prepareIndex(index, type,id).setSource(content).get();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*按字段获取数据*/
    public void get(String index,String type,String id) throws Exception{
        TransportClient client = this.getConnection();
        GetResponse result = client.prepareGet(index,type,id).get();
        System.out.println(result.getSourceAsString());
        System.out.println(result.getType());
        System.out.println(result.getVersion());
        System.err.println(result.getIndex());
        System.err.println(result.getId());

        client.close();
    }

    /*添加map数据*/
    public void addMap()throws Exception{
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("userName","LYC");
        map.put("sendDate",new Date());
        map.put("msg","Hello");
        TransportClient client=this.getConnection();
        IndexResponse response=client.prepareIndex("momo","msg","1").setSource(map).get();
        System.out.println("map索引名称:"+response.getIndex()+"\n map类型:"+response.getType()+"\n map文档ID:"+response.getId()+"\n当前实例状态:"+response.status());
    }


    public static void main(String[] args)throws Exception {
        Test t=new Test();
        t.add();
//        t.get("momo","msg","1");
    }
}