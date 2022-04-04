# CS Custom Biomes
CSCustomBiomes is an addon for the [Custom Structures](https://github.com/ryandw11/CustomStructures) Minecraft server plugin which allows server admins to limit
structures to only spawning in Custom Biomes.

## Usage
Download the correct jar file for your Java version from [ci.ryandw11.com](https://ci.ryandw11.com/job/CSCustomBiomes/).  
  
Put the jar file in your `plugins` folder and start the server.
  
You can now configure your structure config file with the CustomBiomes configuration section.  
  
Example (1.15 - 1.17):
```yml
CustomBiomes:
  BiomeKey:
    Namespace: 'minecraft'
    Key: 'midlands_end'
  Depth: [0;1]
  Scale: [0;1]
  BaseTemp: [0;1]
  Category: 'taiga'
```
Example (1.18+):
```yml
CustomBiomes:
  BiomeKey:
    Namespace: 'minecraft'
    Key: 'midlands_end'
  DownFall: [0;1]
  BaseTemp: [0;1]
  Category: 'taiga'
```

## Compiling
Compiling CSCustomBiomes is a little different since it needs to support multiple Java and Minecraft versions.
  
You must build the following versions of spigot first:
```
java -jar BuildTools.jar --rev 1.18.2 --remapped
java -jar BuildTools.jar --rev 1.18 --remapped
java -jar BuildTools.jar --rev 1.17.1
java -jar BuildTools.jar --rev 1.16.4
java -jar BuildTools.jar --rev 1.15.2
```
  
### Compiling for Java 8
To compile for Java 8 run the following Maven command:
```
mvn clean package -Djava.version=1.8
```
That will give you a Java 8 jar file that supports Minecraft versions 1.15.x and 1.16.5. You
can find the jar compiled at `\CSCustomBiomes\Base\target\CSCustomBiomes-x.x.jar`.

### Compiling for Java 16+
To compile for Java 16+ run the following Maven command:
```
mvn clean package -Djava.version=16
```
That will give you a Java 16 jar file that supports Minecraft versions 1.15.x, 1.16.5, 1.17.x, and 1.18.x. You
can find the jar compiled at `\CSCustomBiomes\Base\target\CSCustomBiomes-x.x.jar`.