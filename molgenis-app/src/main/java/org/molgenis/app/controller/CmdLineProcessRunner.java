package org.molgenis.app.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import org.molgenis.data.Entity;
import org.molgenis.data.Repository;
import org.molgenis.jobs.model.JobExecution.Status;

public class CmdLineProcessRunner {

  public File createFile(
      String fileName,
      String extension,
      String template,
      LinkedList<String> attrs,
      Repository<Entity> repo,
      String prefix,
      String postfix,
      boolean createHeader)
      throws IOException {
    File file = new File(fileName + extension);
    FileWriter fw = new FileWriter(file);
    fw.write(prefix);
    if (createHeader) {
      fw.write(String.format(template, attrs.toArray()));
    }
    for (Entity entity : repo) {
      String line = getLine(template, attrs, entity);
      fw.write(line);
    }
    fw.write(postfix);
    fw.close();
    return file;
  }

  public Status runCommand(String template, LinkedList<String> attrs, Entity entity)
      throws IOException {
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec(getLine(template, attrs, entity));
    PrintStream prtStrm = new PrintStream(pr.getOutputStream());
    prtStrm.println();
    return Status.SUCCESS;
  }

  private String getLine(String template, LinkedList<String> attrs, Entity entity) {
    LinkedList<Object> values = getValues(entity, attrs);
    return String.format(template, values.toArray());
  }

  private LinkedList<Object> getValues(Entity entity, LinkedList<String> attrs) {
    LinkedList<Object> result = new LinkedList<>();
    attrs.forEach(attr -> result.add(entity.get(attr)));
    return result;
  }
}
