package com.rodcell.server.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerConfig {

    public final static String LISTENER_IP = "listenerIp";
    public final static String LISTENER_PORT = "listenerPort";
    public final static String LISTENER_NAME = "listenerName";
    public final static String THREAD_POOL_NAME = "threadPoolName";
    public final static String CORE_THREAD_POOL_SIZE = "coreThreadPoolSize";
    public final static String MAX_THREAD_POOL_SIZE = "maxThreadPoolSize";
    public final static String QUEUE_LIMIT = "queueLimit";
    public final static String SELECTOR_RUNNERS = "selectorRunners";
    public final static String STANDALONE = "standalone";

    public final static String TRANSACTION_TIMEOUT = "transactionTimeout";

    public final static String API_LIST = "apiList"; 

    public final static String REST_BASE_URI = "restBaseUri";
    public final static String REST_RESOURCE_PATH = "restResourcePath";

    public final static String IO_STRATEGY = "IOStrategy";

    public final static String KEEP_ALIVE = "keepAlive";
    public final static String TCP_NO_DELAY = "tcpNoDelay";

    public final static String COMPRESSION_MODE = "compressionMode";
    public final static String COMPRESSION_MIN_SIZE = "compressionMinSize";
    public final static String COMPRESSABLE_MINE_TYPES = "compressableMimeTypes";

    public final static String IS_ACCESS_LOG = "isAccessLog";
    public final static String ACCESS_LOG_PATH = "accessLogPath";

    public final static String INDEX_PATH = "indexPath";
    public final static String DATA_PATH = "dataPath";

    public static final String SERVER_RESOURCE_NAME = "server-info";

    public static final String SERVER_NAME = "serverName";
    public static final String SERVER_VERSION = "serverVersion";

    private static ResourceBundle serverResourceBundle;

//    static {
//        serverResourceBundle = SERVER_RESOURCE_NAME;//ResourceBundle.getBundle(SERVER_RESOURCE_NAME);
//    }

    public static String getValue(String key) {
        return serverResourceBundle==null?null:serverResourceBundle.getString(key);
    }

    public static String getListenerIp() {
        String listenerIp = getValue(ServerConfig.LISTENER_IP);
        if (listenerIp != null && listenerIp.length() > 0 && listenerIp.split(".").length == 4) {
            return listenerIp;
        } else {
            return "0.0.0.0";
        }
    }

    public static int getListenerPort() {
        String listenerPort = getValue(ServerConfig.LISTENER_PORT);
        int port = 8090;
        if (listenerPort != null && listenerPort.length() > 0 ) {
            try {
                port = Integer.parseInt(listenerPort);
                if(port <= 1024) {
                    port = 8090;
                }
            } catch (NumberFormatException nfe) {
                port = 8090;
            }
        } 
        return port;
    }

    public static int getCompressionMinSize() {
        String compressionMinSize = getValue(ServerConfig.COMPRESSION_MIN_SIZE);
        int size = 1;
        if (compressionMinSize != null && compressionMinSize.length() > 0 ) {
            try {
                size = Integer.parseInt(compressionMinSize);
            } catch (NumberFormatException nfe) {
                size = 1;
            }
        } 
        return size;
    }

    public static String getListenerName() {
        String listenerName = getValue(ServerConfig.LISTENER_NAME);
        if (listenerName != null && listenerName.length() > 0 ) {
            return listenerName;
        } else {
            return "Juaby-Listener";
        }
    }

    public static String getThreadPoolName() {
        String threadPoolName = getValue(ServerConfig.THREAD_POOL_NAME);
        if (threadPoolName != null && threadPoolName.length() > 0 ) {
            return threadPoolName;
        } else {
            return "Juaby-Pool";
        }
    }

    public static String getServerName() {
        String serverName = getValue(ServerConfig.SERVER_NAME);
        if (serverName != null && serverName.length() > 0 ) {
            return serverName;
        } else {
            return "JusbyHttpServer";
        }
    }

    public static String getRestBaseUri() {
        String restBaseUri = getValue(ServerConfig.REST_BASE_URI);
        if (restBaseUri != null && restBaseUri.length() > 0 ) {
            return restBaseUri;
        } else {
            return "/api";
        }
    }

    public static String getRestResourcePath() {
        String restResourcePath = getValue(ServerConfig.REST_RESOURCE_PATH);
        if (restResourcePath != null && restResourcePath.length() > 0 ) {
            return restResourcePath;
        } else {
            return "com.mapbar";
        }
    }

    public static String getServerVersion() {
        String serverVersion = getValue(ServerConfig.SERVER_VERSION);
        if (serverVersion != null && serverVersion.length() > 0 ) {
            return serverVersion;
        } else {
            return "0.0.9";
        }
    }

    public static String getCompressionMode() {
        String compressionMode = getValue(ServerConfig.COMPRESSION_MODE);
        if (compressionMode != null && compressionMode.length() > 0 ) {
            return compressionMode;
        } else {
            return "ON";
        }
    }

    public static String getCompressableMimeTypes() {
        String compressableMimeTypes = getValue(ServerConfig.COMPRESSABLE_MINE_TYPES);
        if (compressableMimeTypes != null && compressableMimeTypes.length() > 0 ) {
            return compressableMimeTypes;
        } else {
            return "text/plain," + "text/html," + "binary/octet-stream";
        }
    }

    public static boolean getStandalone() {
        String standalone = getValue(ServerConfig.STANDALONE);
        if (standalone != null && standalone.length() > 0 ) {
            return Boolean.parseBoolean(standalone);
        } else {
            return false;
        }
    }

    public static boolean getKeepAlive() {
        String keepAlive = getValue(ServerConfig.KEEP_ALIVE);
        if (keepAlive != null && keepAlive.length() > 0 ) {
            return Boolean.parseBoolean(keepAlive);
        } else {
            return false;
        }
    }

    public static boolean getTcpNoDelay() {
        String tcpNoDelay = getValue(ServerConfig.TCP_NO_DELAY);
        if (tcpNoDelay != null && tcpNoDelay.length() > 0 ) {
            return Boolean.parseBoolean(tcpNoDelay);
        } else {
            return false;
        }
    }

    public static boolean getIsAccessLog() {
        String isAccessLog = getValue(ServerConfig.IS_ACCESS_LOG);
        if (isAccessLog != null && isAccessLog.length() > 0 ) {
            return Boolean.parseBoolean(isAccessLog);
        } else {
            return false;
        }
    }

    public static String getAccessLogPath() {
        String accessLogPath = getValue(ServerConfig.ACCESS_LOG_PATH);
        if (accessLogPath != null && accessLogPath.length() > 0 ) {
            return accessLogPath;
        } else {
            return "/tmp/access.log";
        }
    }

    public static String getIndexPath() {
        String indexPath = getValue(ServerConfig.INDEX_PATH);
        if (indexPath != null && indexPath.length() > 0 ) {
            return indexPath;
        } else {
            return "/index";
        }
    }

    public static String getDataPath() {
        String dataPath = getValue(ServerConfig.DATA_PATH);
        if (dataPath != null && dataPath.length() > 0 ) {
            return dataPath;
        } else {
            return "/data";
        }
    }

    public static int getCoreThreadPoolSize() {
        String coreThreadPoolSize = getValue(ServerConfig.CORE_THREAD_POOL_SIZE);
        int coreSize = Runtime.getRuntime().availableProcessors() * 2;
        if (coreThreadPoolSize != null && coreThreadPoolSize.length() > 0 ) {
            try {
                coreSize = Integer.parseInt(coreThreadPoolSize);
                if(coreSize <= 0) {
                    coreSize = Runtime.getRuntime().availableProcessors() * 2;
                }
            } catch (NumberFormatException nfe) {
                coreSize = Runtime.getRuntime().availableProcessors() * 2;
            }
        } 
        return coreSize;
    }

    public static int getMaxThreadPoolSize() {
        String maxThreadPoolSize = getValue(ServerConfig.MAX_THREAD_POOL_SIZE);
        int maxSize = Runtime.getRuntime().availableProcessors() * 2;
        if (maxThreadPoolSize != null && maxThreadPoolSize.length() > 0 ) {
            try {
                maxSize = Integer.parseInt(maxThreadPoolSize);
                if(maxSize <= 0) {
                    maxSize = Runtime.getRuntime().availableProcessors() * 2;
                }
            } catch (NumberFormatException nfe) {
                maxSize = Runtime.getRuntime().availableProcessors() * 2;
            }
        } 
        return maxSize;
    }

    public static int getQueueLimit() {
        String queueLimit = getValue(ServerConfig.QUEUE_LIMIT);
        int queueLimitSize = 1000;
        if (queueLimit != null && queueLimit.length() > 0 ) {
            try {
                queueLimitSize = Integer.parseInt(queueLimit);
                if(queueLimitSize <= 0) {
                    queueLimitSize = 1000;
                }
            } catch (NumberFormatException nfe) {
                queueLimitSize = 1000;
            }
        } 
        return queueLimitSize;
    }

    public static int getSelectorRunners() {
        String selectorRunners = getValue(ServerConfig.SELECTOR_RUNNERS);
        int selectorRunnersSize = Runtime.getRuntime().availableProcessors();
        if (selectorRunners != null && selectorRunners.length() > 0 ) {
            try {
                selectorRunnersSize = Integer.parseInt(selectorRunners);
                if(selectorRunnersSize <= 0) {
                    selectorRunnersSize = Runtime.getRuntime().availableProcessors();
                }
            } catch (NumberFormatException nfe) {
                selectorRunnersSize = Runtime.getRuntime().availableProcessors();
            }
        } 
        return selectorRunnersSize;
    }

    public static int getTransactionTimeout() {
        String transactionTimeout = getValue(ServerConfig.TRANSACTION_TIMEOUT);
        int transactionTimeoutSize = Runtime.getRuntime().availableProcessors();
        if (transactionTimeout != null && transactionTimeout.length() > 0 ) {
            try {
                transactionTimeoutSize = Integer.parseInt(transactionTimeout);
                if(transactionTimeoutSize <= 0) {
                    transactionTimeoutSize = 10;
                }
            } catch (NumberFormatException nfe) {
                transactionTimeoutSize = 10;
            }
        } 
        return transactionTimeoutSize;
    }

    public static Map<String, Object> getApiList() {
        String apiList = getValue(ServerConfig.API_LIST);
        Map <String, Object> apiMap = new HashMap<String, Object>();
        if (apiList != null && apiList.length() > 0 ) {
            String [] apiArray = apiList.split(";");
            for (int i = 0; i < apiArray.length; i++) {
                String [] api = apiArray[i].split(":");
                try {
                    apiMap.put(api[1], Class.forName(api[0]).newInstance());
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return apiMap;
        } else {
            return apiMap;
        }
    }

    @SuppressWarnings("rawtypes")
    public static Object getIOStrategy() {
        String ioStrategy = getValue(ServerConfig.IO_STRATEGY);
        Object strategy = null;
        try {
            Constructor constructor = Class.forName(ioStrategy).getDeclaredConstructor();   
            constructor.setAccessible(true);   
            strategy = constructor.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strategy;
    }

}