Spring Security是为基于Spring的应用程序提供声明式安全保护的安全 性框架。Spring Security提供了完整的安全性解决方案，它能够在Web 请求级别和方法调用级别处理身份认证和授权。因为基于Spring框 架，所以Spring Security充分利用了依赖注入（dependency injection， DI）和面向切面的技术。

使用Servlet规范中的Filter保护Web请求并限制URL级别的访问。 Spring Security还能够使用Spring AOP保护方法调用——借助于对象代 理和使用通知，能够确保只有具备适当权限的用户才能访问安全保护 的方法。

Spring Security通过一个同步token的方式来实现CSRF防护的功能。它 将会拦截状态变化的请求（例如，非GET、HEAD、OPTIONS和 TRACE的请求）并检查CSRF token。如果请求中不包含CSRF token的 话，或者token不能与服务器端的token相匹配，请求将会失败，并抛 出CsrfException异常。