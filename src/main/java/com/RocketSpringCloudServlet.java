/** * Copyright &copy; 2012-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved. */
package com;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Web程序启动类
 *
 * @author gaowh
 * @date 2017-05-21 9:43
 */
public class RocketSpringCloudServlet extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RocketSpringCloudDriver.class);
    }

}
