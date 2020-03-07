package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.Point;
import pojo.User;

/**
  * 串型化类
  */
public class Ser {

   /**
    * @param obj
    * 可复用的串型化方法 Object->json
    * @return String json
    */
    public static String objTojson(Object obj){
        ObjectMapper objMapper = new ObjectMapper();
        String json=null;
        try {
            json = objMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
