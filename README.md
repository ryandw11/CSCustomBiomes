# CS Custom Biomes
CSCustomBiomes is an addon for the [Custom Structures](https://github.com/ryandw11/CustomStructures) Minecraft server plugin which allows server admins to limit
structures to only spawning in Custom Biomes.

## Usage
Download the latest version addon's jar file from [ci.ryandw11.com](https://ci.ryandw11.com/job/CSCustomBiomes/).  
  
Put the jar file in your `plugins` folder and start the server.
  
You can now configure your structure config file with the CustomBiomes or CustomBiomeRange configuration sections.  
 
Example:
```yml
CustomBiomes:
  Namespaces:
    - minecraft:snowy_slopes
    - custom:my_custom_biome
    
CustomBiomeRange:
  BaseTemp: 0.4
  DownFall: [0;1]
```

## Compiling
In order to compile CSCustomBiomes, you must have the required versions of Spigot available for maven to reference.  
You can do this by running build tools on the user you want to compile the plugin with. (Make sure to use a remapped jar.)

Here are the required versions (Java 22+ required):
```
java -jar BuildTools.jar --rev 1.21.5 --remapped
java -jar BuildTools.jar --rev 1.21 --remapped
java -jar BuildTools.jar --rev 1.20.6 --remapped
```
Here are the required versions (Java 17-21 required):
```
java -jar BuildTools.jar --rev 1.20.4 --remapped
java -jar BuildTools.jar --rev 1.20.2 --remapped
java -jar BuildTools.jar --rev 1.20 --remapped
java -jar BuildTools.jar --rev 1.19.4 --remapped
java -jar BuildTools.jar --rev 1.19 --remapped
java -jar BuildTools.jar --rev 1.18.2 --remapped
```
Then run,
```
mvn clean package
```
You can find the jar compiled at `\CSCustomBiomes\Base\target\CSCustomBiomes-x.x.jar`.
