# nerdurl4j
### Version: 1.0.0
[![](https://jitpack.io/v/tk.zytekaron/nerdurl4j.svg)](https://jitpack.io/#tk.zytekaron/nerdurl4j)

This package was created to house various Java utilities.

<br/>

## Installation

### Gradle
```groovy
repositories {
    maven { url = 'https://jitpack.io' }
}
```
```groovy
depencencies {
    compile 'tk.zytekaron:nerdurl4j:1.0.0'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>tk.zytekaron</groupId>
        <artifactId>nerdurl4j</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

<br/>

## Usage Examples
[src/test/java/tk/zytekaron/nerdurl4j/Main.java](src/test/java/tk/zytekaron/nerdurl4j/Main.java)
```java
// tk.zytekaron.nerdurl4j.NerdUrl
NerdUrl nerdUrl = new NerdUrl();
nerdUrl.shorten("google.com").get();
```

<br/>

## License
<b>nerdurl4j</b> is licensed under the [GNU Lesser General Public License Version 3](https://github.com/Zytekaron/discord.bio/blob/master/LICENSE)