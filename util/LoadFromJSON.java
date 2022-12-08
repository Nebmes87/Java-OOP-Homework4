package util;

import model.Priority;
import model.WorkJob;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadFromJSON {
    public static List<WorkJob> load() throws IOException, ParseException {
        List<WorkJob> list = new ArrayList<WorkJob>();
        if (new File(Settings.db).exists()) {
            FileReader reader = new FileReader(Settings.db);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            jsonObj.keySet().forEach(keyStr ->
            {
                JSONObject jsonRec = (JSONObject) jsonObj.get(keyStr);
                Priority priority = new Priority();
                switch ((String)jsonRec.get("Priority")) {
                    case "MIDDLE" -> priority.setPriorityMiddle();
                    case "HIGH" -> priority.setPriorityHigh();
                    default -> priority.setPriorityLow();
                }
                WorkJob wj = new WorkJob(
                        priority
                        , (String)jsonRec.get("Subject")
                        , (String)jsonRec.get("Author")
                        , LocalDateTime.parse((String)jsonRec.get("Created"), Settings.formatter)
                        , LocalDateTime.parse((String)jsonRec.get("Deadline"), Settings.formatter)
                );
                list.add(wj);
            });
        }
        return list;
    }
}