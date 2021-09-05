package ir.ut;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

import ir.ut.exception.BolbolestanException;
import ir.ut.offering.Offering;
import ir.ut.student.Student;

public class CommandHandler {
    private Bolbolestan bolbolestan;

    public CommandHandler(Bolbolestan bolbolestan) {
        this.bolbolestan = bolbolestan;
    }

    private JSONObject createSuccessResponse(String message) {
        JSONObject resp = new JSONObject();

        resp.put("success", true);
        resp.put("data", message);

        return resp;
    }

    private JSONObject createSuccessResponse(ArrayList<JSONObject> objects) {
        JSONObject resp = new JSONObject();

        resp.put("success", true);
        resp.put("data", objects);

        return resp;
    }

    private JSONObject createSuccessResponse(JSONObject object) {
        JSONObject resp = new JSONObject();

        resp.put("success", true);
        resp.put("data", object);

        return resp;
    }

    private JSONObject createErrorResponse(String message) {
        JSONObject resp = new JSONObject();

        resp.put("success", false);
        resp.put("error", message);

        return resp;
    }

    public void run(InputStream inputStream, PrintStream printStream) {
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            String commandLine = scanner.nextLine();
            String[] parts = commandLine.split(" ", 2);
            String command = parts[0];
            String jsonData = parts.length > 1 ? parts[1] : "";
            JSONObject parsedData = new JSONObject(jsonData);

            try {
                switch (command) {
                    case "addStudent":
                        this.bolbolestan.addStudent(Student.deserialize(parsedData));
                        printStream.println(this.createSuccessResponse("Student is created."));
                        break;
                    case "addOffering":
                        this.bolbolestan.addOffering(Offering.deserialize(parsedData));
                        printStream.println(this.createSuccessResponse("Offering is created."));
                        break;
                    case "getOfferings":
                        this.bolbolestan.getStudent(parsedData.getString("studentId"));
                        ArrayList<JSONObject> objects = new ArrayList<>();
                        for (Offering offering : this.bolbolestan.getOfferings()) {
                            objects.add(offering.serialize());
                        }
                        printStream.println(this.createSuccessResponse(objects));
                        break;
                    case "getOffering":
                        this.bolbolestan.getStudent(parsedData.getString("studentId"));
                        printStream.println(this.createSuccessResponse(this.bolbolestan.getOffering(parsedData.getString("code")).serialize()));
                        break;
                    case "addToWeeklySchedule":
                        this.bolbolestan.addToWeeklySchedule(parsedData.getString("studentId"), parsedData.getString("code"));
                        printStream.println(this.createSuccessResponse("Offering is added to schedule."));
                        break;
                    case "removeFromWeeklySchedule":
                        this.bolbolestan.removeFromWeeklySchedule(parsedData.getString("studentId"), parsedData.getString("code"));
                        printStream.println(this.createSuccessResponse("Offering is removed from schedule."));
                        break;
                    case "getWeeklySchedule":
                        printStream.println(createSuccessResponse(this.bolbolestan.getWeeklySchedule(parsedData.getString("studentId")).serialize()));
                        break;
                    case "finalize":
                        this.bolbolestan.finalizeSchedule(parsedData.getString("studentId"));
                        printStream.println(this.createSuccessResponse("Student weekly schedule is finalized."));
                        break;
                    default:
                        printStream.println(this.createErrorResponse("Invalid Command."));
                }
            } catch (BolbolestanException e) {
                printStream.println(this.createErrorResponse(e.getMessage()));
            }
        }

        scanner.close();
    }
}
