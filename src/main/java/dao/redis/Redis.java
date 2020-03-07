package dao.redis;

import pojo.Point;
import redis.clients.jedis.Jedis;

import java.util.Map;

public enum  Redis implements IRedis{
    jediss;
    private Jedis jedis;
    Redis(){
         jedis = new Jedis("39.97.119.183");
    }

    @Override
    public void setChannel(String id, String channelId) {
        if(jedis.get(id)==null){
            jedis.set(id,channelId);
        }
    }

    @Override
    public String getChannel(String id) {
        if (jedis.get(id)!=null){
            return jedis.get(id);
        }
        return null;
    }

    @Override
    public void setPoint(String pid, String point) {
        if(jedis.get(pid)==null){
            jedis.set(pid,point,"nx","ex",2000);
        }
    }

    @Override
    public String getPoint(String pid) {
        if (jedis.get(pid)!=null){
            return jedis.get(pid);
        }
        return null;
    }

    @Override
    public  boolean setp(String phone, String code){
         if(jedis.set(phone,code).equals("OK")){
             jedis.expire(phone, 120);
             return true;
         }
        return false;
    }

    @Override
    public  String getp(String phone){
        if(jedis.get(phone)!=null){
            return jedis.get(phone);
        }
        return "验证码错误或过期!!!";

    }

    @Override
    public  boolean setu(String uname, String pass){
        if(jedis.set(uname,pass).equals("OK")){
            return true;
        }
        return false;
    }

    @Override
    public  String getu(String uname, String pass){
        if(jedis.get(uname).equals(pass)){
            return "ok";
        }
        return "密码错误!!!";
    }

    @Override
    public void setinformation(String Bphone, Map map){
        jedis.hmset(Bphone,map);
        jedis.expire(Bphone,604800);
    }

//    @Override
//    public Map getinformation(String Bphone){
//        Map map=jedis.hgetAll(Bphone);
//        if(map==null||map.size()==0){
//            //ICeate iCeate=new Create();
//            //map=iCeate.uselectAll("user",Bphone);
//            try {
//                map=new Mymybatis().selset(Bphone);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(map!=null&&map.size()!=0){
//                setinformation(Bphone,map);
//            }else {
//                map.put("rs","没有完善信息");
//            }
//        }
//
//    return map;
//
//    }

    @Override
    public boolean provp(String phone) {
        if(jedis.get(phone+"1")!=null){
            return false;
        }
        return true;
    }

    @Override
    public void putp(String phone,String uname) {
        jedis.set(phone+"1",uname);
    }

    @Override
    public boolean provu(String uname) {
        if(jedis.get(uname+"#")!=null){
            return false;
        }
        return true;
    }

    @Override
    public void putu(String uname,String phone) {
        jedis.set(uname+"#",phone);
    }

    @Override
    public String get(String uname){
        return jedis.get(uname+"#");
    }


}
