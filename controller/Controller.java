package controller;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import model.WorkJob;
import org.json.simple.parser.ParseException;
import view.View;
import util.SaveToJSON;
import util.LoadFromJSON;

public class Controller {
    private final Logger log = Logger.getLogger(Controller.class.getName());
    private Integer index;
    View view = new View(this.log);
    List<WorkJob> list = new ArrayList<>();
    public Controller() {
        try {
            LogManager.getLogManager().readConfiguration(
                    Controller.class.getResourceAsStream("../log.config"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }
    public void run() throws IOException, ParseException {
        boolean ex = false;
        Scanner scan = new Scanner(System.in);
        String inputLine = "";
        view.info();
        loadFromJSON();
        while (!ex) {
            System.out.print(">>> ");
            inputLine = scan.nextLine();
            if (inputLine.length()>0) {
                switch (inputLine) {
                    case "/quit" -> ex = true;
                    case "/info" -> this.view.info();
                    case "/help" -> this.view.help();
                    case "/addRecord" -> addRecord();
                    case "/editCurrent" -> editRecord();
                    case "/printAll" -> this.view.printAll(this.list,this.index);
                    case "/printCurrent" -> printCurrent();
                    case "/setRecord" -> setRecord();
                    case "/save" -> saveToJSON();
                    case "/load" -> loadFromJSON();
                    default -> view.errorCommand(inputLine);
                }
            }
        }
        saveToJSON();
        view.bye();
    }

    private void printCurrent() {
        if (this.index<this.list.size()) {
            this.view.printOneJob(list.get(this.index));
        }
    }
    private void addRecord() {
        WorkJob job = this.view.recordAddEdit(false, null);
        if (job != null) {
            this.list.add(job);
            this.index = job.getId();
        }
    }
    private void editRecord() {
        int idx = -1;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.index == list.get(i).getId()) {
                idx = i;
                break;
            }
        }
        if (idx > -1) {
            WorkJob job = this.view.recordAddEdit(true, this.list.get(idx));
            if (job != null) {
                this.list.set(idx, job);
                this.index = job.getId();
            }
        }
    }
    private void saveToJSON() throws IOException {
        log.info("/save");
        SaveToJSON.save(this.list);
        System.out.println("Данные записаны!");
    }
    private void loadFromJSON() throws IOException, ParseException {
        log.info("/load");
        this.list.clear();
        this.list = LoadFromJSON.load();
        if (this.list.size()>0) {
            this.index = this.list.get(0).getId();
        }
        System.out.println("Данные прочитаны.");
    }
    private void setRecord() {
        Integer tmpIndex = view.setIndex();
        for(WorkJob job: this.list) {
            if (job.getId() == tmpIndex) {
                this.index = tmpIndex;
                System.out.printf("Новое значение текущей записи: %d%n", this.index);
                log.info(String.format("/setIndex -> %d", this.index));
            }
        }
    }
}