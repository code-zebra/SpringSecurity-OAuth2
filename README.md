

* 认证与授权

  * 区分

    |      |         认证         |          授权          |
    | :--: | :------------------: | :--------------------: |
    |  1   | 确定用户所宣称的身份 | 确定用户可以访问的权限 |
    |  2   | 通过合法凭证校验用户 | 通过规则和策略校验访问 |
    |  3   |       早于授权       |      在认证后执行      |
    |  4   | 通过 ID tokens 实现  | 用 Access Tokens 实现  |

  

# OAuth

## OAuth & JWT

* `JWT是一种认证协议`
  * JWT提供了一种用于发布接入令牌（Access Token),并对发布的签名接入令牌进行验证的方法。 令牌（Token）本身包含了一系列声明，应用程序可以根据这些声明限制用户对资源的访问。

* `OAuth2是一种授权框架`

  * OAuth2是一种授权框架，提供了一套详细的授权机制。用户或应用可以通过公开的或私有的设置，授权第三方应用访问特定资源。

* 很多情况下，在讨论OAuth2的实现时，会把JSON Web Token作为一种认证机制使用。这也是为什么他们会经常一起出现。

* 简单来说：**应用场景不一样**

  * `OAuth2`用在使用`第三方账号登录`的情况(比如使用weibo, qq, github登录某个app)

  * `JWT`是用在`前后端分离`, 需要简单的对后台API进行保护时使用.(前后端分离无session, 频繁传用户密码不安全)

# OAuth 2.0

* OAuth：Open Authorization

* `简单说，OAuth 就是一种授权机制。数据的所有者告诉系统，同意授权第三方应用进入系统，获取这些数据。系统从而产生一个短期的进入令牌（token），用来代替密码，供第三方应用使用。`

* `快递员问题`：对于一个小区而言，快递员和小区住户都想获得进去小区的权限，但是快递员和住户的权限显然是不同的。让快递员能够自由进入小区，又不必知道小区居民的密码，而且他的唯一权限就是送货，其他需要密码的场合。

  > 于是，设计了一套授权机制。
  >
  > 第一步，门禁系统的密码输入器下面，增加一个按钮，叫做"获取授权"。快递员需要首先按这个按钮，去申请授权。
  >
  > 第二步，他按下按钮以后，屋主（也就是我）的手机就会跳出对话框：有人正在要求授权。系统还会显示该快递员的姓名、工号和所属的快递公司。
  >
  > 我确认请求属实，就点击按钮，告诉门禁系统，我同意给予他进入小区的授权。
  >
  > 第三步，门禁系统得到我的确认以后，向快递员显示一个进入小区的令牌（access token）。令牌就是类似密码的一串数字，只在短期内（比如七天）有效。
  >
  > 第四步，快递员向门禁系统输入令牌，进入小区。
  >
  > 有人可能会问，为什么不是远程为快递员开门，而要为他单独生成一个令牌？这是因为快递员可能每天都会来送货，第二天他还可以复用这个令牌。另外，有的小区有多重门禁，快递员可以使用同一个令牌通过它们。

  


## 令牌与密码

* 令牌（token）与密码（password）的作用是一样的，都可以进入系统，但是有三点差

  1. 令牌是短期的，到期会自动失效，用户自己无法修改。密码一般长期有效，用户不修改，就不会发生变化。

  2. 令牌可以被数据所有者撤销，会立即失效。以上例而言，屋主可以随时取消快递员的令牌。密码一般不允许被他人撤销。

  3. 令牌有权限范围（scope），比如只能进小区的二号门。对于网络服务来说，只读令牌就比读写令牌更安全。密码一般是完整权限。

* 保证了令牌既可以让第三方应用获得权限，同时又随时可控，不会危及系统安全。

* 只要知道了令牌，就能进入系统。系统一般不会再次确认身份，所以**令牌必须保密，泄漏令牌与泄漏密码的后果是一样的。** 这也是为什么令牌的有效期，一般都设置得很短的原因。

* OAuth 2.0 对于如何颁发令牌的细节，规定得非常详细。具体来说，一共分成四种授权类型（authorization grant），即四种颁发令牌的方式，适用于不同的互联网场景。

## OAuth2.0四种授权类型

* 授权码（authorization-code）
* 隐藏式（implicit）
* 密码式（password）：
* 客户端凭证（client credentials）
* 不管哪一种授权方式，第三方应用申请令牌之前，都必须先到系统备案，说明自己的身份，然后会拿到两个身份识别码：客户端 ID（client ID）和客户端密钥（client secret）。这是为了防止令牌被滥用，没有备案过的第三方应用，是不会拿到令牌的。

### 授权码（authorization-code）

* 最常用的流程，安全性也最高，适用于有后端的Web应用。授权码通过前端传送，令牌则存储在后端，所有与资源服务器的通信都在后端完成。这样的前后端分离，可以避免令牌泄漏。

* **步骤**

  1. A 网站提供一个链接，用户点击后就会跳转到 B 网站，授权用户数据给 A 网站使用。下面就是 A 网站跳转 B 网站的一个示意链接。

  > ```http
  > https://b.com/oauth/authorize?
  >   response_type=code&
  >   client_id=CLIENT_ID&
  >   redirect_uri=CALLBACK_URL&
  >   scope=read
  > 
  > 上面 URL 中，
  > response_type参数表示要求返回授权码（code），
  > client_id参数让 B 知道是谁在请求，
  > redirect_uri参数是 B 接受或拒绝请求后的跳转网址，
  > scope参数表示要求的授权范围（这里是只读）。
  > ```

  2. 用户跳转后，B 网站会要求用户登录，然后询问是否同意给予 A 网站授权。用户表示同意，这时 B 网站就会跳回`redirect_uri`参数指定的网址。跳转时，会传回一个`授权码`，就像下面这样。

  > ```http
  > https://a.com/callback?code=AUTHORIZATION_CODE
  > 
  > 上面 URL 中，code参数就是授权码。
  > ```

  3. A 网站拿到授权码以后，就可以在后端，向 B 网站请求令牌。

  > ```http
  > https://b.com/oauth/token?
  >  client_id=CLIENT_ID&
  >  client_secret=CLIENT_SECRET&
  >  grant_type=authorization_code&
  >  code=AUTHORIZATION_CODE&
  >  redirect_uri=CALLBACK_URL
  > 
  > 上面 URL 中，client_id参数和client_secret参数用来让 B 确认 A 的身份（client_secret参数是保密的，因此只能在后端发请求），grant_type参数的值是AUTHORIZATION_CODE，表示采用的授权方式是授权码，code参数是上一步拿到的授权码，redirect_uri参数是令牌颁发后的回调网址。
  > ```

  4. B 网站收到请求以后，就会颁发令牌。具体做法是向`redirect_uri`指定的网址，发送一段 JSON 数据。

  > ```json
  > {    
  >   "access_token":"ACCESS_TOKEN",
  >   "token_type":"bearer",
  >   "expires_in":2592000,
  >   "refresh_token":"REFRESH_TOKEN",
  >   "scope":"read",
  >   "uid":100101,
  >   "info":{...}
  > }
  >     
  > 上面 JSON 数据中，access_token字段就是令牌，A 网站在后端拿到了。
  > ```

* **图示**
  1. <img src="https://www.wangbase.com/blogimg/asset/201904/bg2019040902.jpg" alt="img" style="zoom: 67%;" />
  2. <img src="https://www.wangbase.com/blogimg/asset/201904/bg2019040907.jpg" alt="img" style="zoom:67%;" />
  3. <img src="https://www.wangbase.com/blogimg/asset/201904/bg2019040904.jpg" alt="img" style="zoom:67%;" />
  4. <img src="https://www.wangbase.com/blogimg/asset/201904/bg2019040905.jpg" alt="img" style="zoom:67%;" />

### 隐蔽式（implicit）

* 有些 Web 应用是纯前端应用，没有后端。这时就不能用上面的方式了，必须将令牌储存在前端。**RFC 6749 就规定了第二种方式，允许直接向前端颁发令牌。这种方式没有授权码这个中间步骤，所以称为（授权码）"隐藏式"（implicit）。**

* **步骤**

  1. A 网站提供一个链接，要求用户跳转到 B 网站，授权用户数据给 A 网站使用。

     > ```http
     > https://b.com/oauth/authorize?
     >   response_type=token&
     >   client_id=CLIENT_ID&
     >   redirect_uri=CALLBACK_URL&
     >   scope=read
     >   
     >   // 上面 URL 中，response_type参数为token，表示要求直接返回令牌。
     > ```

  2. 用户跳转到 B 网站，登录后同意给予 A 网站授权。这时，B 网站就会跳回`redirect_uri`参数指定的跳转网址，并且**把令牌作为 URL 参数，传给 A 网站。**

     > ```http
     > https://a.com/callback#token=ACCESS_TOKEN
     > 
     > // 上面 URL 中，token参数就是令牌，A 网站因此直接在前端拿到令牌。
     > ```

     > 注意，令牌的位置是 **URL 锚点**（fragment），而不是查询字符串（querystring），这是因为 OAuth 2.0 允许跳转网址是 HTTP 协议，因此存在"中间人攻击"的风险，而浏览器跳转时，锚点不会发到服务器，就减少了泄漏令牌的风险。
     >
     > 
     >
     > 这种方式把令牌直接传给前端，是很不安全的。因此，只能用于一些安全要求不高的场景，并且令牌的有效期必须非常短，通常就是会话期间（session）有效，浏览器关掉，令牌就失效了。

     

     <img src="https://www.wangbase.com/blogimg/asset/201904/bg2019040906.jpg" alt="img" style="zoom:67%;" />

### 密码式（password）

* `密码模式`（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。

* 在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。这通常用在用户对客户端高度信任的情况下，比如客户端是操作系统的一部分，或者由一个著名公司出品。而认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用这种模式。

* 步骤

  > （A）用户向客户端提供用户名和密码。
  >
  > （B）客户端将用户名和密码发给认证服务器，向后者请求令牌。
  >
  > （C）认证服务器确认无误后，向客户端提供访问令牌。

### 客户端模式（Client）

* 客户端模式（Client Credentials Grant）指`客户端以自己的名义`，而不是以用户的名义，向"服务提供商"进行认证。严格地说，客户端模式并不属于OAuth框架所要解决的问题。在这种模式中，用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实`不存在授权问题`。

* 步骤

  > （A）客户端向认证服务器进行身份认证，并要求一个访问令牌。
  >
  > （B）认证服务器确认无误后，向客户端提供访问令牌。

* 

# Spring Security OAuth2实战

* ==**本例中关于令牌的存储，以及客户端注册都是在内存中，实际生产过程中肯定是要存储到数据库**==

* **主要角色**

  * 授权服务器
  * 资源服务器（资源服务器与授权服务器经常放在同一个服务中，扮演不同的角色）
  * 资源所有者（理解为用户）
  * 第三方客户端

* [blog](https://www.cnblogs.com/cjsblog/p/9296361.html)

* [OAuth2授权码请求](https://www.cnblogs.com/cjsblog/p/9230990.html)

* [OAuth2资源服务器](https://www.cnblogs.com/cjsblog/p/9241217.html)

* [sql](https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql)

* **关键配置：**（粗略分为3个步骤）

  1. `配置Security`
  2. `配置授权服务器`
  3. `配置资源服务器`

  * 完成以上步骤，即可使用客户端访问资源服务器资源

## 获取授权码

1. `配置Security`

   >``` java 
   >@Configuration
   >@EnableWebSecurity
   >@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
   >public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   >
   >@Override
   >protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   >   super.configure(auth);
   >//    auth.inMemoryAuthentication().withUser("zhangsan").password("$2a$10$qsJ/Oy1RmUxFA.YtDT8RJ.Y2kU3U4z0jvd35YmiMOAPpD.nZUIRMC").roles("USER");
   >
   >}
   >
   >@Override
   >public void configure(WebSecurity web) throws Exception {
   >   super.configure(web);
   >}
   >
   >@Override
   >protected void configure(HttpSecurity http) throws Exception {
   >   super.configure(http);
   >}
   >
   >
   >@Bean
   >@Override
   >protected UserDetailsService userDetailsService() {
   >   User.UserBuilder builder = User.builder();
   >   UserDetails user = builder.username("zhangsan").password("$2a$10$GStfEJEyoSHiSxnoP3SbD.R8XRowP1QKOdi.N6/iFEwEJWTQqlSba").roles("USER").build();
   >   UserDetails admin = builder.username("lisi").password("$2a$10$GStfEJEyoSHiSxnoP3SbD.R8XRowP1QKOdi.N6/iFEwEJWTQqlSba").roles("USER", "ADMIN").build();
   >   return new InMemoryUserDetailsManager(user, admin);
   >}
   >
   >@Bean
   >public PasswordEncoder passwordEncoder() {
   >   return new BCryptPasswordEncoder();
   >}
   >
   >
   >public static void main(String[] args) {
   >   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
   >   System.out.println(bCryptPasswordEncoder.encode("123456"));
   >   System.out.println(bCryptPasswordEncoder.encode("12345678"));
   >}
   >}
   >```

2. `配置认证服务器`

   > ``` java
   > @Configuration
   > @EnableAuthorizationServer
   > public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
   > 
   >     @Autowired
   >     private DataSource dataSource;
   > 
   >     /**
   >      * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
   >      * /oauth/authorize端点也应该是安全的
   >      * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
   >      */
   >     @Override
   >     public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
   >         super.configure(security);
   >     }
   > 
   >     /**
   >      * 配置ClientDetailsService（客户端配置）
   >      * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，否则密码授权方式不可用。
   >      * 至少配置一个client，否则服务器将不会启动。
   >      */
   >     @Override
   >     public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
   > //        clients.jdbc(dataSource);
   > 
   >         // 内存中配置客户端
   >         clients.inMemory()
   >             	// 客户端名称
   >                 .withClient("client")
   >             	// 客户端密码
   >                 .secret("$2a$10$3nVkoLTDPUVLBHybUgsUtenrxDboDHgWtqQzKeLZjhjL1dE5sqmxy")
   >             	// 授权类型
   >                 .authorizedGrantTypes("authorization_code", "refresh_token")
   >                 .scopes("all")
   >             	// 授权成功后重定向路径
   >                 .redirectUris("http://www.baidu.com");
   >     }
   > 
   >     /**
   >      * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
   >      * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
   >      */
   >     @Override
   >     public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
   >         super.configure(endpoints);
   >     }
   > }
   > ```

   * 至此，完成了用户（资源所有者）和客户端的注册。**特别注意**，用户（资源所有者）的用户名和密码跟客户端的用户名和密码（client_id、client_secret）不是一套，它们是两个东西。
   * 在获取access_token之前需要`用户授权`，然后`返回一个code`，最后`用code换access_token`（此过程需要客户端提供client_secret）。在这个过程中涉及到三个服务器端点：授权端点、重定向端点、令牌端点

   

3. `获取授权码code：`

   > ``` shell
   > curl http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all
   > ```
   >
   > * 参数
   >   * response_type：必填。将其值设置为code表示如果成功的话将收到一个授权码。
   >   * client_id：必填，客户端标识
   >   * redirect_id：可选。重定向URI虽然不是必须的，但是你的服务应该会需要它。而且，这个URL必须和授权服务端注册的redirect_id一致。
   >   * scope：可选。请求可能会有一个或多个scope值。授权服务器会把客户端请求的范围(scope)展示给用户。
   >   * state：推荐。state参数用于应用存储特定的请求数据的可以防止CSRF攻击。授权服务器必须原封不动地将这个值返回给应用。

   

   > ```shell
   > # 响应：
   > ## 跳转到页面
   > https://www.baidu.com/?code=7FaznJ
   > ## code：授权码
   > ## state：请求时带的state参数
   > ```
   >
   > * 参数
   >   * `code`：授权码
   >   * `state`：请求时带的state参数

   

4. `获取access_token`

   * 授权服务器可以通过HTTP Basic Auth方式对客户端进行认证，也可以通过在请求中加一个client_secret参数来对客户端进行认证。不建议将客户端的secret直接作为参数放到client_secret中，而且这种方式下client_id和client_secret都是必填参数。
   * 有了授权码，就可以换取access_token

   ```shell
   ## 通过HTTP Basic Auth如何对客户端进行认证呢？客户端需要怎样传参呢？
   ## 需要在请求header中设置Authorization，它的值是Basic + 空格 + Base64加密后的client_id:secret
   ## 如： Authorization: Basic bXktY2xpZW50LTE6MTIzNDU2Nzg=
   
   "access_token":"a7c57532-22ef-4bed-87d2-014b7f550386"
   ```

## 获取资源

* `配置资源服务器`  ResourceServerConfigurerAdapter

  > ``` java
  > @Configuration
  > @EnableResourceServer
  > public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  > 
  >     @Override
  >     public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
  >         super.configure(resources);
  >     }
  > 
  >     /**
  >      * 用于配置对受保护的资源的访问规则
  >      * 默认情况下所有不在/oauth/**下的资源都是受保护的资源
  >      * {@link OAuth2WebSecurityExpressionHandler}
  >      */
  >     @Override
  >     public void configure(HttpSecurity http) throws Exception {
  >         http.requestMatchers().antMatchers("/test/**")
  >                 .and()
  >                 .authorizeRequests()
  >                 .anyRequest().authenticated();
  >     }
  > }
  > ```

* **获取授权码**：`http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=read`

* 配置

  > ``` json
  > USER: zhangsan
  > code=HcMCzR
  > {
  >     "access_token": "1bb53888-0b1e-46f1-b0ae-105d49072f02",
  >     "token_type": "bearer",
  >     "refresh_token": "0e3bc2b9-8334-4f3c-84e3-7ee4250f61f8",
  >     "expires_in": 43199,
  >     "scope": "read"
  > }
  > 
  > ADMIN、USER: lisi
  > code=rCGk9I
  > {
  >     "access_token": "3642a700-2bb7-477e-9687-afeaeee9e355",
  >     "token_type": "bearer",
  >     "refresh_token": "f101bb9a-d718-4942-874e-a0b3f2a2b371",
  >     "expires_in": 42591,
  >     "scope": "all"
  > }
  > 
  > {
  >     "access_token": "61d380e6-d5dc-4372-8f41-8a0a899649f3",
  >     "token_type": "bearer",
  >     "refresh_token": "2798a0c0-28af-4f8f-b970-47376d3561dd",
  >     "expires_in": 43199,
  >     "scope": "all"
  > }
  > ```
  >
  > * 访问ADMIN资源：`http://localhost:8080/test/sayHi?access_token=2dbc2a45-0db6-4bcf-9d3e-8557c642244e`

