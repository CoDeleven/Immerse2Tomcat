《深入剖析Tomcat》第九章

# 概要
Catalina通过一个称为Session管理器的组件来管理建立的Session对象，该组件由Manager接口表示。
Session管理器有且仅要一个Context容器相关联，每当有一个请求进来，就返回一个有效的Session对象。

# StandardSession——标准的Session实现类
每个Session在同个Context里都不会重复（不保证不同Context），相关ID的设置在setId()方法内部。

每次调用access()都会记录一个最新的lastAccessedTime

当调用expire()时，会设置expiring为true，表示正在过期状态；设置valid为false，表示不可用。随后根据notify的属性，去激活监听器，最后设置expiring为false。

# StandardSessionFacade——Session的门面类
防止程序员将HttpSession向下转型为StandardSession

# Manager

## StandardManager
该Manager主要做了以下几件事
1. start(): 调用load()方法载入序列化文件（默认是SESSIONS.ser）, 启动线程
     * 新启动的线程主要执行Session有效性检查，检查手段是通过MaxInactiveInterval（最大非活跃间隔）。如果过期了就标记过期。
2. stop(): 调用unload()方法，将未过期的Session放入序列化文件里，然后将存入的session设置为过期
3. createSession(): 创建Session，该方法一般只被Container实现类调用

## PersistentManagerBase
该Manager主要做了以下几件事
1. start(): 启动线程
     * 新启动的线程主要执行了Session过期检查——processExpires()；Session持久化检查——processPersistenceChecks()；
     * 持久化检查主要做了这样几件事：
         * processMaxIdleSwaps: 将空闲时间最长的Session给保存出去
         * processMaxActiveSwaps: 将超出最大活跃数的Session给保存出去
         * processMaxIdleBackups: 将超出给定空闲时间的Session进行备份

## DistributedManager
该Manager主要做了以下几件事
1. start(): 获取收发的Cluster，启动本类的线程
     * 处理接收到的Session
     * 处理过期的Session
     * 进行持久化检查

# Store
该组件主要用于持久化Session，有FileStore和JDBCStore

# 总体Manager流程
当接收到请求时，Servlet容器先去获取Context容器，通过Context容器获取SessionManager。
由于SessionManager是作为Context的组件，通常在Context启动时，SessionManager就会被启动。
StandardManager通常会在start()里调用load方法，载入SESSION.ser文件
PersistentManagerBase等子类都是启一个线程去检查Session的各种状态，并即使替换和备份