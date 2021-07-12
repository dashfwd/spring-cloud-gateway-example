package com.dashfwd.filters

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Mono
import java.net.URI

/**
 * Example of using redirects
 */
@Configuration
class RedirectFilter {
    companion object: KLogging()

    @Bean
    fun redirectFilterRules(): WebFilter {
        return WebFilter { exchange, chain ->
            val response = exchange.response

            val uriPath = exchange.request.uri.path
            if (uriPath == "/" || uriPath == "/tree") {
                response.statusCode = HttpStatus.MOVED_PERMANENTLY
                response.headers.location = URI("tree.jpg")
                Mono.empty()
            }
            else {
                // No redirect, continue normal processing
                chain.filter(exchange)
            }
        }
    }
}