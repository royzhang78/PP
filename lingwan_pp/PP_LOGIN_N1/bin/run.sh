nohup java -server -Djava.ext.dirs=/appjava/serverApp/pp_login_new/lib -classpath /appjava/serverApp/pp_login_new/bin -Xloggc:/data/serverApp/dev_pp/jvmlog/gc.log -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Djava.io.tmpdir:/data/serverApp/dev_pp/log/tmp -Xms128m -Xmx1024m -XX:+UseParallelGC -XX:ParallelGCThreads=4 -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxPermSize=100m -XX:MaxTenuringThreshold=1  org.tp.zb.server.TPServer > /appjava/serverApp/pp_login_new/bin/console.log & echo $! > /appjava/serverApp/pp_login_new/bin/login.pid &