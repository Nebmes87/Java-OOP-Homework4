package view;

import model.Job;
import model.WorkJob;
import model.Priority;
import model.WorkJobStream;
import util.Settings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class View {
    private final Logger log;
    public View(Logger log) {
        this.log = log;
    }
    public void info() {
        log.info("/info");
        System.out.println("Программа - планировщик личных дел");
        System.out.println("Для помощи наберите \"/help\"");
    }
    public void bye() {
        log.info("/quit");
        System.out.println("Программа завершена.");
    }
    public void errorCommand(String cmd) {
        log.info(String.format("Error command: \"%s\"\n",cmd));
        System.out.println("Неверная команда. Для помощи наберите \"help\"");
    }
    public void help() {
        log.info("/help");
        System.out.println("Программа - поддерживает следующие команды:");
        System.out.println("/help - вывод помощи");
        System.out.println("/info - вывод информации о программе");
        System.out.println("/printAll - печать всей базы целиком c учетом приоритетов");
        System.out.println("/printCurrent - печать текущей записи базы данных");
        System.out.println("/addRecord - добавление новой задачи");
        System.out.println("/editCurrent - редактирование текущей записи");
        System.out.println("/setRecord - установка указателя текущей задачи на требуемую запись");
        System.out.println("/save - сохранение базы в файл в формате JSON");
        System.out.println("/load - чтение из файла в формате JSON базы данных");
    }

    public void printOneJob(Job job) {
        log.info(String.format("/printCurrent: %s%n",((WorkJob)job).toString()));
        System.out.println("Текущая задача:");
        System.out.println("ID: "+((WorkJob)job).getId().toString());
        System.out.println("Приоритет: "+((WorkJob)job).getPriority());
        System.out.println("Автор: "+((WorkJob)job).getAuthor());
        System.out.println("Текст задачи: "+((WorkJob)job).getSubject());
        System.out.println("Задача создана: "+((WorkJob)job).getCreationDT().format(Settings.formatter));
        System.out.println("Дедлайн: "+((WorkJob)job).getDeadlineDT().format(Settings.formatter));
    }
    public WorkJob recordAddEdit(boolean edit, WorkJob job) {
        WorkJob newRecord = new WorkJob();
        log.info(edit ? String.format("/editRecord: %s%n", ((WorkJob)job).toString()) : "/addRecord");
        Scanner scan = new Scanner(System.in);
        String inputLine = "";
        boolean isCorrect = false;
        System.out.println(edit ? "Редактирование текущей записи" : "Добавление новой записи");

        if (edit) {
            System.out.println("Если хотите оставить поле без изменений - просто нажмите Enter");
            System.out.printf("Текст задачи: %s%n", job.getSubject());
        }
        System.out.print("Введите текст задачи: ");
        inputLine = scan.nextLine();
        newRecord.setSubject((edit) ? (inputLine.length()>0) ? inputLine : job.getSubject() : inputLine);

        do {
            if (edit) {
                System.out.printf("Текущий приоритет %s%n", job.getPriority());
            }
            System.out.printf("Введите приоритет задачи: %s1 - LOW; 2 - MIDDLE; 3 - HIGH: ",
                                (edit) ? "0 - не меняем; " : "");
            inputLine = scan.nextLine();

            if (inputLine != null && inputLine.matches("[0-9]+")) {
                if (0 <= Integer.parseInt(inputLine) && Integer.parseInt(inputLine) < 4) {
                    isCorrect = true;
                }
            }
            if (!isCorrect) {
                System.out.println("Некорректный ввод! повторите!");
            }
        } while (!isCorrect);
        if (edit && Integer.parseInt(inputLine) == 0) {
            newRecord.setPriority(job.getPriority().getPriority());
        }
        else {
            switch (Integer.parseInt(inputLine)) {
                case Priority.MIDDLE -> newRecord.setPriority(Priority.MIDDLE);
                case Priority.HIGH -> newRecord.setPriority(Priority.HIGH);
                default -> newRecord.setPriority(Priority.LOW);
            }
        }

        if (edit) {
            System.out.printf("Автор: %s%n", job.getAuthor());
        }
        System.out.print("Введите автора: ");
        inputLine = scan.nextLine();
        newRecord.setAuthor((edit) ? (inputLine.length()>0) ? inputLine : job.getAuthor() : inputLine);

        if (edit) {
            newRecord.setCreationDT(job.getCreationDT());
        }
        else {
            newRecord.setCreationDT(LocalDateTime.now());
        }

        isCorrect = false;
        do {
            if (edit) {
                System.out.printf("Текущий дедлайн: %s%n", job.getDeadlineDT().format(Settings.formatter));
            }
            System.out.print("Введите дату и время дедлайна в формате (dd-MM-yyyy HH:mm:ss): ");
            inputLine = scan.nextLine();
            try {
                isCorrect = true;
            } catch (Exception e) {
                if (edit && inputLine.length() == 0) {
                    isCorrect = true;
                }
                else {
                    System.out.println("Некорректный ввод! повторите!");
                }
            }
        } while (!isCorrect);
        if (edit && inputLine.length() == 0) {
            newRecord.setDeadlineDT(job.getDeadlineDT());
        }
        else {
            newRecord.setDeadlineDT(LocalDateTime.parse(inputLine,Settings.formatter));
        }

        System.out.print("Сохранить информацию? [Y/n]");
        inputLine = scan.nextLine();
        if (inputLine.equals("Y") || inputLine.isEmpty()) {
            log.info(String.format("%s%n%s%n",(edit) ? "Отредактированная запись:":"Новая запись:",newRecord.toString()));
            System.out.println("Информация сохранена");
            return newRecord;
        }
        else {
            log.info("Операция отменена.");
            return null;
        }
    }
    public void printAll(List<WorkJob> localList,Integer index) {
        WorkJobStream stream = new WorkJobStream(localList);
        WorkJob job = new WorkJob();
        log.info("/printAll");
        System.out.println("| ID|Приор.|      Дедлайн      |                      Задача                      |             Автор            |       Создано     |");
        System.out.printf("+%s+%s+%s+%s+%s+%s+%n"
                , "-".repeat(3)
                , "-".repeat(6)
                , "-".repeat(19)
                , "-".repeat(50)
                , "-".repeat(30)
                , "-".repeat(19)
        );
        stream.mySort();
        while (stream.hasNext()) {
            job = stream.next();
            System.out.printf("%s%3d|%6s|%19s|%50s|%30s|%19s|%n"
                    , (job.getId() == index) ? "*" : "|"
                    , job.getId()
                    , ((Priority)job.getPriority()).toString()
                    , job.getDeadlineDT().format(Settings.formatter)
                    , job.getSubject()
                    , job.getAuthor()
                    , job.getCreationDT().format(Settings.formatter)
                    );
        }
    }
    public Integer setIndex() {
        log.info("/setRecord");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Введите новый индекс текущей записи: ");
            String inputLine = scan.nextLine();
            if (inputLine != null && inputLine.matches("[0-9]+")) {
                if (0 < Integer.parseInt(inputLine)) {
                    return Integer.parseInt(inputLine);
                }
            }
                System.out.println("Некорректный ввод! повторите!");
        }
    }
}