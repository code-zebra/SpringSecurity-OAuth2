package com.bgtech.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:19
 *
 * 配置认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
     * /oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 配置ClientDetailsService（客户端配置）
     * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，否则密码授权方式不可用。
     * 至少配置一个client，否则服务器将不会启动。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);

        // 内存中配置客户端
        clients.inMemory()
                .withClient("client")
                .secret("$2a$10$3nVkoLTDPUVLBHybUgsUtenrxDboDHgWtqQzKeLZjhjL1dE5sqmxy")
                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .scopes("all")
                .scopes("read", "write", "execute")
//                .redirectUris("http://localhost:8081/login/oauth2/code/callback");
                .redirectUris("http://www.baidu.com");
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    /**
     * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
     * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
//    }

    /**
     * 增加TokenStore，将Token存储到Redis中，否则存放在内存中的token每次重启都会丢失。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
    }
}
