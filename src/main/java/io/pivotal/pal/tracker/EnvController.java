package io.pivotal.pal.tracker;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {

   private String port;
    private String mem_limit;
    private String index;
    private String addr;


    @GetMapping("/env")
    public Map<String,String> getEnv( ){
        Map<String, String> env =new HashMap<>();
    env.put("PORT",port);
    env.put("MEMORY_LIMIT",mem_limit);
    env.put("CF_INSTANCE_INDEX",index);
    env.put("CF_INSTANCE_ADDR",addr);
    return env;
    }

    public EnvController(@Value("${PORT:NOT SET}") String port, @Value("${MEMORY_LIMIT:NOT SET}") String mem_limit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String index,@Value("${CF_INSTANCE_ADDR:NOT SET}")String addr){
    this.port=port;
    this.mem_limit=mem_limit;
    this.index=index;
    this.addr=addr;
    }
}
