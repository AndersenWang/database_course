package org.example.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConstUtil {
    public static final int ORDER_CORS = -102;

    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_ROLE = "role";
}
