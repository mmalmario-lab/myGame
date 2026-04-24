<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

## My Game - Java Project

This is a Java game project using Maven as the build system.

### Project Information
- **Type**: Java Maven Project
- **Java Version**: 11
- **Build System**: Maven
- **Main Class**: com.example.Main

### Build Commands
- Compile: `mvn clean compile`
- Run: `mvn exec:java -Dexec.mainClass="com.example.Main"`
- Package: `mvn clean package`
- Test: `mvn test`

### Project Structure
- `src/main/java/com/example/` - Main source code
- `src/test/java/com/example/` - Test code
- `target/` - Build output directory
- `pom.xml` - Maven configuration file

### Development Guidelines
- Maintain package structure under `com.example`
- Add new classes in appropriate packages
- Write unit tests in `src/test/java`
- Update `pom.xml` when adding dependencies
