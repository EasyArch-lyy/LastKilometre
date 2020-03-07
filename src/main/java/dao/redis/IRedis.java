package dao.redis;

import pojo.Point;

import java.util.Map;

public interface IRedis {

   /**
    * @param id,channelId
    * 记录用户<id,channelId>
    */
    void setChannel(String id,String channelId);

    /**
     * @param id 用户名
     * 查询缓存中id对应的channelId
     * @return channelId或null
     */
    String getChannel(String id);

    /**
     * @param point
     * 存入redis以Point串型化后json字符串
     */
    void setPoint(String pid, String point);

    /**
     * @param pid
     * 小区名拼接自增数作查询pid,返回String类型结果point json
     * @return String point
     */
    String getPoint(String pid);

    boolean setp(String phone, String code);

    String getp(String phone);

    boolean setu(String uname, String pass);

    String getu(String uname, String pass);

    void setinformation(String Bphone, Map map);

//    Map getinformation(String Bphone);

    boolean provp(String phone);

    void putp(String phone, String uname);

    boolean provu(String uname);

    void putu(String uname, String Bphone);

    String get(String name);
}
