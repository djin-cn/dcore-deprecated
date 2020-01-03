## Github的 Maven库
1. 保持和Maven仓库一致的目录结构(repository/{groupId}/{artifactId}/{name}/{version})
2. 在项目的pom.xml内添加repository节点,重点是url
```  
<repositories>
    <repository>
        <id>djin-github</id>
        <name>djin's maven repository</name>
        <!--<url>https://raw.githubusercontent.com/{username}/{project}/repos/</url>-->
        <url>https://raw.githubusercontent.com/djin-cn/dcore/repos/</url>
    </repository>
</repositories>
```
3. Maven默认的setting.xml内不要有mirror.id=*的配置, 否则上一步可能失效, 以下示例为阿里云maven配置
```xml
	<mirrors>
		<mirror>
			<id>alimaven</id>
			<name>aliyun maven</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
			<mirrorOf>central</mirrorOf>
		</mirror>
	</mirrors>
```