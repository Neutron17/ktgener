import com.neutron.ktgener.loadTemplate
import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface TestI {
	val fileName: String
	val projectName: String
	val userName: String
}

class MVNTest : TestI {
	override val fileName: String = "templates/maven.genert"
	override val projectName: String = "testmvnProject"
	override val userName: String = "TestUName"
	var time: String = ""

	@Test
	fun hierarchytest() {
		loadTemplate(fileName, projectName, userName)
		time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
		main = "package com.TestUName.testmvnProject;\n" +
				"/*\n" +
				" * Created at $time\n" +
				" */\n" +
				"public class Main {\n" +
				"\tpublic static void main(String[] args) {\n" +
				"\t\tSystem.out.println(\"Hello World!\\n\");\n" +
				"\t}\n" +
				"}"
		assertTrue(File(projectName).isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/pom.xml").isFile, "Hierarchy check failed")
		assertTrue(File("$projectName/src").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main/java").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main/java/com").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main/java/com/$userName").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main/java/com/$userName/$projectName").isDirectory, "Hierarchy check failed")
		assertTrue(File("$projectName/src/main/java/com/$userName/$projectName/Main.java").isFile, "Hierarchy check failed")


	}
	@Test(dependsOnMethods = ["hierarchytest"])
	fun pomContentTest() {
		val lines = File("$projectName/pom.xml").bufferedReader().readLines()
		assertEquals(lines, pom.split('\n'), "pom.xml doesn't match")
	}
	@Test(dependsOnMethods = ["hierarchytest"])
	fun mainContentTest() {
		val lines = File("$projectName/src/main/java/com/$userName/$projectName/Main.java").bufferedReader().readLines()
		assertEquals(lines, main.split('\n'), "Main.java doesn't match")
	}
	@Test(dependsOnMethods = ["pomContentTest", "mainContentTest"])
	fun final() {
		println(File(projectName).deleteRecursively())
	}
	lateinit var main: String

	val pom = (
			"<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
					"  <modelVersion>4.0.0</modelVersion>\n" +
					"\n" +
					"  <!-- Project artifact -->\n" +
					"  <groupId>com.$userName</groupId>\n" +
					"  <artifactId>$projectName</artifactId>\n" +
					"  <version>0.0.1</version>\n" +
					"\n" +
					"  <!-- Project information -->\n" +
					"  <name>$projectName</name>\n" +
					"  <description>This is just a boilerplate project.</description>\n" +
					"  <url>http://www.example.com</url>\n" +
					"\n" +
					"  <!-- Project build properties -->\n" +
					"  <properties>\n" +
					"    <mainClass>com.TestUName.testmvnProject.Main</mainClass>\n" +
					"    <javaVersion>1.8</javaVersion>\n" +
					"    <!-- Don't modify this -->\n" +
					"    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
					"  </properties>\n" +
					"\n" +
					"  <!-- Dependencies imports -->\n" +
					"  <dependencies>\n" +
					"    <dependency>\n" +
					"      <groupId>junit</groupId>\n" +
					"      <artifactId>junit</artifactId>\n" +
					"      <version>4.11</version>\n" +
					"      <scope>test</scope>\n" +
					"    </dependency>\n" +
					"  </dependencies>\n" +
					"\n" +
					"  <!-- Build settings -->\n" +
					"  <build>\n" +
					"    <!-- Name of your compiled artifact -->\n" +
					"    <finalName>\${artifactId}</finalName>\n" +
					"    <!-- Clean old builds and install dependencies before compile -->\n" +
					"    <defaultGoal>clean install</defaultGoal>\n" +
					"    <!-- Package files inside resource directory in to the jar -->\n" +
					"    <resources>\n" +
					"      <resource>\n" +
					"        <directory>src/main/resources</directory>\n" +
					"        <filtering>true</filtering> <!-- Filter variables -->\n" +
					"      </resource>\n" +
					"    </resources>\n" +
					"    <!-- Compilation settings starts here -->\n" +
					"    <plugins>\n" +
					"      <plugin>\n" +
					"        <artifactId>maven-clean-plugin</artifactId>\n" +
					"        <version>3.1.0</version>\n" +
					"      </plugin>\n" +
					"      <plugin>\n" +
					"        <artifactId>maven-resources-plugin</artifactId>\n" +
					"        <version>3.0.2</version>\n" +
					"      </plugin>\n" +
					"      <!-- Compile with specified java version -->\n" +
					"      <plugin>\n" +
					"        <artifactId>maven-compiler-plugin</artifactId>\n" +
					"        <version>3.8.1</version>\n" +
					"        <configuration>\n" +
					"          <source>\${javaVersion}</source>\n" +
					"          <target>\${javaVersion}</target>\n" +
					"        </configuration>\n" +
					"      </plugin>\n" +
					"      <plugin>\n" +
					"        <artifactId>maven-surefire-plugin</artifactId>\n" +
					"        <version>2.22.1</version>\n" +
					"      </plugin>\n" +
					"      <plugin>\n" +
					"        <artifactId>maven-install-plugin</artifactId>\n" +
					"        <version>2.5.2</version>\n" +
					"      </plugin>\n" +
					"      <plugin>\n" +
					"        <groupId>org.apache.maven.plugins</groupId>\n" +
					"        <artifactId>maven-assembly-plugin</artifactId>\n" +
					"        <executions>\n" +
					"          <execution>\n" +
					"            <phase>package</phase>\n" +
					"            <goals>\n" +
					"              <goal>single</goal>\n" +
					"            </goals>\n" +
					"            <configuration>\n" +
					"              <archive>\n" +
					"                <manifest>\n" +
					"                  <mainClass>\${mainClass}</mainClass>\n" +
					"                </manifest>\n" +
					"              </archive>\n" +
					"              <descriptorRefs>\n" +
					"                <descriptorRef>jar-with-dependencies</descriptorRef>\n" +
					"              </descriptorRefs>\n" +
					"            </configuration>\n" +
					"          </execution>\n" +
					"        </executions>\n" +
					"      </plugin>\n" +
					"    </plugins>\n" +
					"  </build>\n" +
					"</project>" );
}