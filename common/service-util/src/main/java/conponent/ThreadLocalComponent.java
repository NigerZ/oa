package conponent;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
public class ThreadLocalComponent {

    public static ThreadLocal<Long> user_id = new ThreadLocal<>();
    public static ThreadLocal<String> username = new ThreadLocal<>();


    public static void setUserId(Long val) {
        user_id.set(val);
    }

    public static void setUsername(String val) {
        username.set(val);
    }

    public static void removeUserId() {
        user_id.remove();
    }

    public static void removeUsername() {
        username.remove();
    }

    public static void remove() {
        user_id.remove();
        username.remove();
    }
}
