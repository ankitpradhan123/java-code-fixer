package com.coditation;

import com.google.googlejavaformat.java.Formatter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "fix", defaultPhase = LifecyclePhase.INITIALIZE)
public class JavaCodeFixerMojo extends AbstractMojo {

  private List<File> javaFilesPresentInTheProject = new ArrayList<>();

  @Parameter(property = "fix.path", required = true)
  private String path;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Running Java code fixer...");
    try {
      if (path.equals("src/main/java")) {
        // Specify the source code directory
        File sourceDirectory = new File("src/main/java");
        // Retrieve all Java source files in the source directory
        List<File> javaFiles = findJavaFiles(sourceDirectory);
        if (javaFiles.isEmpty()) {
          getLog().info("No java files found");
        } else {
          for (File javaFile : javaFiles) {
            formatFile(javaFile);
          }
        }
      } else {
        File file = new File(path);
        if (!isJavaFile(file.getName())) {
          throw new MojoExecutionException("Not a java file: " + file.getPath());
        }
        formatFile(file);
      }
    } catch (MojoExecutionException e) {
      throw new MojoExecutionException(e.getMessage());
    }
  }

  // Function to return the list of java files.
  private List<File> findJavaFiles(File directory) {
    for (File file : directory.listFiles()) {
      if (file.isDirectory()) {
        findJavaFiles(file);
      }
      // Checking if the file is a java file and adding only that file to the list
      String fileName = file.getName();
      if (isJavaFile(fileName)) {
        javaFilesPresentInTheProject.add(file);
      }
    }
    return javaFilesPresentInTheProject;
  }

  private void formatFile(File javaFile) throws MojoExecutionException {
    try {
      String content;
      try {
        content = new String(Files.readAllBytes(javaFile.toPath()), StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      // Apply code formatting using Google Java Format
      String formattedContent = new Formatter().formatSource(content);
      Files.write(javaFile.toPath(), formattedContent.getBytes(StandardCharsets.UTF_8));
      if (content.equals(formattedContent)) {
        getLog().info("[OK] " + javaFile.getPath());
      } else {
        getLog().info("[FIXED] " + javaFile.getPath());
      }
    } catch (Exception e) {
      throw new MojoExecutionException(
          "Failed to fix code in the file: " + javaFile.getPath() + " due to " + e.getMessage());
    }
  }

  private boolean isJavaFile(String fileName) {
    boolean isJavaFile = false;
    int index = fileName.lastIndexOf('.');
    if (index > 0) {
      if (fileName.substring(index + 1).equals("java")) {
        isJavaFile = true;
      }
    }
    return isJavaFile;
  }
}
