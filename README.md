# java-code-fixer
A plugin that reformats the style of java code according to the Google standards.

## Getting Started

**1. Clone the application**

```bash
git clone https://github.com/ankitpradhan123/java-code-fixer.git
```
**2. Build the plugin**

Go to the root directory of the plugin folder or the cloned repository and run
```bash
mvn install
```
**3. Add plugin in pom.xml file of your project**

```bash
    <build>  
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>com.coditation</groupId>
                    <artifactId>javacodefixer-maven-plugin</artifactId>
                    <version>1.0-SNAPSHOT</version>
                    <dependencies>
                        <dependency>
                            <groupId>com.google.googlejavaformat</groupId>
                            <artifactId>google-java-format</artifactId>
                            <version>1.7</version>
                        </dependency>
                    </dependencies>
                    <configuration>
                        <!--suppress UnresolvedMavenProperty -->
                        <path>${fix.path}</path>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
```
**3. Execute the plugin**

For running the plugin for all the java files present in src folder
```bash
mvn javacodefixer:fix -Dfix.path=src/main/java
```
For running the plugin for a specific Java file
```bash
mvn javacodefixer:fix -Dfix.path=${filePath}
```
```bash
mvn javacodefixer:fix -Dfix.path=src/main/java/Main.java
```
To avoid any build errors run the full command
```bash
mvn:com.coditation:javacodefixer-maven-plugin:fix -Dfix.path=${filePath}
```
