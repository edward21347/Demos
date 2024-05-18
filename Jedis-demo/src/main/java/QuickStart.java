import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickStart {
    private static Jedis jedis;
    public static void main(String[] args) {
        setUp();
        //testString();
        testHash();
        tearDown();
    }

    public static void setUp(){
        //建立连接
        jedis = new Jedis("192.168.37.128",6379);
        //设置密码
        //jedis.auth("123");
        //选择哪个库
        jedis.select(0);
    }

    public static void testString(){
        //jedis命令与redis原生命令一致
        String res = jedis.set("name", "肖旺");
        System.out.println(res);
        System.out.println(jedis.get("name"));

    }

    public static void testHash(){
        Map<String, String> map = new HashMap<>();
        map.put("name","肖旺");
        map.put("age","30");
        jedis.hmset("lol:player:1",map);
        map.put("name","卢本伟");
        map.put("age","29");
        jedis.hmset("lol:player:2",map);
        List<String> name = jedis.hmget("lol:player:2", "name","age");
        System.out.println(name);
        name = jedis.hmget("lol:player:1", "name","age");
        System.out.println(name);
        Map<String, String> map1 = jedis.hgetAll("lol:player:1");
        System.out.println(map1);
    }

    public static void tearDown()
    {
        //释放资源
        if (jedis!=null){
            jedis.close();
        }
    }

}
