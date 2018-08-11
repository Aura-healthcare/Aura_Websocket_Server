# Aura_Websocket_Server
A websocket server handling Aura's data transmitted by Aura's mobile app.

## How to contribute
Please refer to [How To Contribute](https://github.com/Aura-healthcare/Aura_infrastructure/blob/master/CONTRIBUTING.md)

## tl;dr
Build application artefact (jar file) with the following command :
```
gradle clean test fatJar
```

Jar file should be generated in directory : build/libs.
Current usage is the following :
```
usage: java -jar WebSocketServer-[version].jar
 -c,--configfile <path/to/configFile>   use given config file
 -d                                     debug mode
```

## Docker integration
Docker integration is now properly configured, check out the [repo on docker hub](https://hub.docker.com/r/elrib/aura_websocket_server/)

Alternatively, you can build the image locally, and then run it with the following commands :
```
 docker image build -t wsserver .
 docker run -d \
 --name wsserver \
 --mount type=bind,source="$(pwd)"/wsserver.properties,target=/home/conf/wsserver.properties \
 wsserver
```

