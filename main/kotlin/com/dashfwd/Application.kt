package com.dashfwd

import mu.KLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling


/**
 * Example of creating proxy rules
 *
 * See also https://spring.io/guides/gs/gateway/
 */
@SpringBootApplication
@EnableScheduling
class Application {
    companion object : KLogging() {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()

            // Proxy /get to https://httpbin.org/get
            .route { p ->
                p
                    .path("/get")
                    .filters { f ->
                        f.addRequestHeader("Hello", "World")
                    }
                    .uri("https://httpbin.org")
            }
            // Proxy /wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg to
            //       https://upload.wikimedia.org/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg
            .route { p: PredicateSpec ->
                    p.path(*listOf("/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg").toTypedArray())
                        .uri("https://upload.wikimedia.org")
            }
            // Proxy /tree.jpg to
            //       https://upload.wikimedia.org/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg
            .route { p: PredicateSpec ->
                p.path("/tree.jpg")
                    .filters { f: GatewayFilterSpec ->
                        f.rewritePath(
                            "/(.*)",
                            "/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg"
                        )
                    }
                    .uri("https://upload.wikimedia.org")
            }
            // Proxy anything starting with /base64/{value} to https://httpbin.org/base64/{value}
            .route { p ->
                p
                    .path("/base64/**")
                    .uri("https://httpbin.org")
            }
            .build()
    }
}
