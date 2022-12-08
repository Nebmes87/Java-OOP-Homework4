package util;

import model.WorkJob;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

public class SaveToJSON {
    public static void save(List<WorkJob> localList) throws IOException {
        JSONObject jsonObj = new JSONObject();
        for(WorkJob job: localList) {
            JSONObject jsonRec = new JSONObject();
            jsonRec.put("Priority", job.getPriority().toString());
            jsonRec.put("Subject", job.getSubject());
            jsonRec.put("Author", job.getAuthor());
            jsonRec.put("Created", job.getCreationDT().format(Settings.formatter));
            jsonRec.put("Deadline", job.getDeadlineDT().format(Settings.formatter));
            jsonObj.put(job.getId().toString(),jsonRec);
        }
        Files.write(Paths.get(Settings.db), jsonObj.toJSONString().getBytes());
    }
}