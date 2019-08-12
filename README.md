# SpringLearning

　　记录Spring框架下如何架构微服务。比如：MVC,Configuration Server,circuit breaks, intelligent routing,micro-proxy,control bus, ont-time tokens,leadership election, distributed sessions, cluster state等

　　并总结一些开发中的心得体会，比如：打log的想法是；异常处理想法;IDEA社区版使用心得等。


# Spring MVC

　　本章主要讲如何快速的使用spring boot快速搭建一个Web MVC服务。


# IDEA的方便性

　　本章主要讲在使用IDEA的时候，一些开箱即用的功能让我个人觉得眼前一亮．我没有使用商业版本，而是使用社区版本。比如：写法的一些智能提示，把更好的写法以建议的形式标出来;自带了CodeStyle检测，也可以自行加Eclipse的CodeStyle插件;调用方法时候显示表示你的参数是输给调用方法的哪一个参数;直接集成了版本控制（查看分支，比对等功能都直接开箱可用）;命令行（这样不必再开命令行做git相关操作，如果你个人喜欢通过命令行操作的话）;

　　从上可以看出IDEA把项目的所有开发周期所用到的工具都集中到了该开发环境中，所以不必来回转换；而智能提示给出的一些建议都是普适的，所以很通用。让大家写代码的时候也很容易边写边获取前人的各种经验。

下图展示了，git操作页（提交树、比较等）、终端操作页、调用参数提示、maven操作区等

![](pic/idea_benefit_one.png)



# IDEA中MavenJava

在使用IntelliJ IDEA中Maven编译过程中可能会报diamond operator is not supported in -source 1.5之类的错误，出现这样的原因是Maven插件的默认配置有问题。可以下pom.xml中加入如下配置解决

```
  <properties>
    <java.version>1.8</java.version>
    <maven.compiler.source>${java.version}</maven.compiler.source>
    <maven.compiler.target>${java.version}</maven.compiler.target>
  </properties>
```

然后按"Ctrl+Shift+Alt+S"打开项目配置，设置Modules的Language Level为"8"：

![](pic/idea_project_structure_javac_setting.png)

最后按"Ctrl+Alt+S"打开设置，搜索"Java Compiler"，将默认jdk和当前modual的jdk版本切换为1.8即可：

![](pic/idea_global_javac_setting.png)

# springshiro session获取方式

获取Session的方法定义在SessionManager接口中细节见如下代码

```java
public interface SessionManager {

    /**
     * Starts a new session based on the specified contextual initialization data, which can be used by the underlying
     * implementation to determine how exactly to create the internal Session instance.
     * <p/>
     * This method is mainly used in framework development, as the implementation will often relay the argument
     * to an underlying {@link SessionFactory} which could use the context to construct the internal Session
     * instance in a specific manner.  This allows pluggable {@link org.apache.shiro.session.Session Session} creation
     * logic by simply injecting a {@code SessionFactory} into the {@code SessionManager} instance.
     *
     * @param context the contextual initialization data that can be used by the implementation or underlying
     *                {@link SessionFactory} when instantiating the internal {@code Session} instance.
     * @return the newly created session.
     * @see SessionFactory#createSession(SessionContext)
     * @since 1.0
     */
    Session start(SessionContext context);

    /**
     * Retrieves the session corresponding to the specified contextual data (such as a session ID if applicable), or
     * {@code null} if no Session could be found.  If a session is found but invalid (stopped or expired), a
     * {@link SessionException} will be thrown.
     *
     * @param key the Session key to use to look-up the Session
     * @return the {@code Session} instance corresponding to the given lookup key or {@code null} if no session
     *         could be acquired.
     * @throws SessionException if a session was found but it was invalid (stopped/expired).
     * @since 1.0
     */
    Session getSession(SessionKey key) throws SessionException;
}


默认SessionManager
public class ServletContainerSessionManager implements WebSessionManager 


```


![](pic/shiroSessionGetFollow.png)

从左下红框处可以看出shiro获取session的完整流程，如果完成了登录，可以在左下解看到session的属性里有org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY的值为true这代表该用户已经通过了鉴权操作。

获取session的方法在下例代码的```context = resolveSession(context)```方法中，从方法注释里也能看出来其基本做用。

```java
public class DefaultSecurityManager extends SessionsSecurityManager {
 public Subject createSubject(SubjectContext subjectContext) {
        //create a copy so we don't modify the argument's backing map:
        SubjectContext context = copy(subjectContext);

        //ensure that the context has a SecurityManager instance, and if not, add one:
        context = ensureSecurityManager(context);

        //Resolve an associated Session (usually based on a referenced session ID), and place it in the context before
        //sending to the SubjectFactory.  The SubjectFactory should not need to know how to acquire sessions as the
        //process is often environment specific - better to shield the SF from these details:
        context = resolveSession(context);

        //Similarly, the SubjectFactory should not require any concept of RememberMe - translate that here first
        //if possible before handing off to the SubjectFactory:
        context = resolvePrincipals(context);

        Subject subject = doCreateSubject(context);

        //save this subject for future reference if necessary:
        //(this is needed here in case rememberMe principals were resolved and they need to be stored in the
        //session, so we don't constantly rehydrate the rememberMe PrincipalCollection on every operation).
        //Added in 1.2:
        save(subject);

      return subject;
    }
}
```

然后会去调用resolveContextSession(context)方法-->getSession(SessionKey key),而在getSession方法中会转去sessionManger获取Session，默认的shiro SessionManager是ServletContainerSessionManager

```java
protected SubjectContext resolveSession(SubjectContext context) {
        if (context.resolveSession() != null) {
            log.debug("Context already contains a session.  Returning.");
            return context;
        }
        try {
            //Context couldn't resolve it directly, let's see if we can since we have direct access to 
            //the session manager:
            Session session = resolveContextSession(context);
            if (session != null) {
                context.setSession(session);
            }
        } catch (InvalidSessionException e) {
            log.debug("Resolved SubjectContext context session is invalid.  Ignoring and creating an anonymous " +
                    "(session-less) Subject instance.", e);
        }
        return context;
    }

protected Session resolveContextSession(SubjectContext context) throws InvalidSessionException {
        SessionKey key = getSessionKey(context);
        if (key != null) {
            return getSession(key);
        }
        return null;
    }

public Session getSession(SessionKey key) throws SessionException {
        return this.sessionManager.getSession(key);
    }
```

ServletContainerSessionManager.getSession-->HttpSession httpSession = request.getSession(false);从而拿到Web session信息。

```java
public Session getSession(SessionKey key) throws SessionException {
        if (!WebUtils.isHttp(key)) {
            String msg = "SessionKey must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(key);

        Session session = null;

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            session = createSession(httpSession, request.getRemoteHost());
        }

        return session;
    }
```

以上流程都是默认流程，
public abstract class AbstractNativeSessionManager extends AbstractSessionManager implements NativeSessionManager, EventBusAware


DefaultWebSessionManager获取Session的基本流程如下（一般情况下在给DefaultWebSessionManager传入DAO层对像的时候会默认做applyCacheManagerToSessionDAO操作，即给DAO加缓存控制。工作流程见下面基本流程部分，相关代码见如下：

```java
public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
        applyCacheManagerToSessionDAO();
    }
```

获最Session的调用过程
```java
AbstractNativeSessionManager.getSession(SessionKey key)-->AbstractNativeSessionManager. lookupSession(SessionKey key) --> AbstractValidatingSessionManager.doGetSession(final SessionKey key)-->DefaultSessionManager.retrieveSession(SessionKey sessionKey)-->DefaultSessionManager.retrieveSessionFromDataSource(Serializable sessionId)

 protected Session retrieveSessionFromDataSource(Serializable sessionId) throws UnknownSessionException {
        return sessionDAO.readSession(sessionId);
    }


基本流是：getCachedSession获取实现了CacheManager接口的对像--->从<K, V> Cache<K, V> getCache(String name)方法中拿到缓存-->从缓存中获取session数据-->如果拿到直接反回否则从super.readSession方法，从SessionDAO中拿数据。

CachingSessionDAO{
public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session s = getCachedSession(sessionId);
        if (s == null) {
            s = super.readSession(sessionId);
        }
        return s;
    }

protected Session getCachedSession(Serializable sessionId) {
        Session cached = null;
        if (sessionId != null) {
            Cache<Serializable, Session> cache = getActiveSessionsCacheLazy();
            if (cache != null) {
                cached = getCachedSession(sessionId, cache);
            }
        }
        return cached;
    }
private Cache<Serializable, Session> getActiveSessionsCacheLazy() {
        if (this.activeSessions == null) {
            this.activeSessions = createActiveSessionsCache();
        }
        return activeSessions;
    }
protected Cache<Serializable, Session> createActiveSessionsCache() {
        Cache<Serializable, Session> cache = null;
        CacheManager mgr = getCacheManager();
        if (mgr != null) {
            String name = getActiveSessionsCacheName();
            cache = mgr.getCache(name);
        }
        return cache;
    }
}
getCacheManager返回的是实现了CacheManager接口的对像，比如自己实现的基于Redis的等
public interface CacheManager {
    <K, V> Cache<K, V> getCache(String var1) throws CacheException;
}
============================以下是调用super.readSession(sessionId)的调用关系======================================

public abstract class AbstractSessionDAO implements SessionDAO{
{
  public Session readSession(Serializable sessionId) throws UnknownSessionException {
          Session s = doReadSession(sessionId);
          if (s == null) {
              throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
          }
          return s;
      }
}

  #由实际实现子类完成功能
  protected abstract Session doReadSession(Serializable sessionId);
}
```

从上我们可以看出来，我们一般会通过继承CachingSessionDAO来实现自己的DAO对象，
SessionsSecurityManager