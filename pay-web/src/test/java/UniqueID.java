import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class UniqueID {
    static {

    }
    public static void main(String[] args) {
        String next = ObjectId.next();
        System.out.println("objectID "+next);
        String objectId = IdUtil.objectId();
        System.out.println("objectID "+objectId);
        Snowflake snowflake = IdUtil.createSnowflake(1,1);
        long id = snowflake.nextId();
        System.out.println("雪花ID "+id);


    }
}
