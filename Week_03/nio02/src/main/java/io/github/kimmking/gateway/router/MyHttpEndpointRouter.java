package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * MyHttpEndpointRouter
 * 随机路由
 *
 * @author YangQi
 */
public class MyHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        return endpoints.get(new Random().nextInt(endpoints.size()));
    }
}
