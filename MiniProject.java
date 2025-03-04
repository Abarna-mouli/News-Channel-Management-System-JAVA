import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.*;

abstract class Channel {
    private String programName;
    private String anchor;
    private int month;
    private double trpRating;
    private double business;

    public Channel(String programName, String anchor, int month, double trpRating, double business) {
        this.programName = programName;
        this.anchor = anchor;
        this.month = month;
        this.trpRating = trpRating;
        this.business = business;
    }

    public String getProgramName() { return programName; }
    public String getAnchor() { return anchor; }
    public int getMonth() { return month; }
    public double getTrpRating() { return trpRating; }
    public double getBusiness() { return business; }
    public abstract void display();
}

class NewsChannel extends Channel {
    public NewsChannel(String programName, String anchor, int month, double trpRating, double business) {
        super(programName, anchor, month, trpRating, business);
    }

    @Override
    public void display() {
        System.out.println("Program: " + getProgramName() + ", Anchor: " + getAnchor());
    }
}

public class NewChannelApp extends Application {
    private List<Channel> programs = new ArrayList<>();
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("News Channel Management");
        BorderPane layout = new BorderPane();

        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #336699;");

        Button addProgramButton = new Button("Add Program");
        Button maxCollectionButton = new Button("Max Collection Month");
        Button busyAnchorButton = new Button("Busiest Anchor");
        Button sortByTRPButton = new Button("Sort by TRP");
        Button leastTRPButton = new Button("Least TRP Program");

        controlPanel.getChildren().addAll(addProgramButton, maxCollectionButton, busyAnchorButton, sortByTRPButton, leastTRPButton);

        outputArea = new TextArea();
        outputArea.setFont(Font.font("Verdana", 14));
        outputArea.setStyle("-fx-control-inner-background: #e6f2ff;");

        layout.setLeft(controlPanel);
        layout.setCenter(outputArea);

        addProgramButton.setOnAction(e -> addProgram());
        maxCollectionButton.setOnAction(e -> findMaxCollectionMonth());
        busyAnchorButton.setOnAction(e -> findBusyAnchor());
        sortByTRPButton.setOnAction(e -> sortByTRP());
        leastTRPButton.setOnAction(e -> findLeastTRPProgram());

        primaryStage.setScene(new Scene(layout, 700, 500));
        primaryStage.show();
    }

    private void addProgram() {
        try {
            String programName = showInputDialog("Enter Program Name:");
            if (programName.isEmpty()) { showWarning("Program Name cannot be empty!"); return; }

            String anchor = showInputDialog("Enter Anchor Name:");
            if (anchor.isEmpty()) { showWarning("Anchor Name cannot be empty!"); return; }

            int month = Integer.parseInt(showInputDialog("Enter Month (1-12):"));
            if (month < 1 || month > 12) { showWarning("Month must be between 1 and 12!"); return; }

            double trpRating = Double.parseDouble(showInputDialog("Enter TRP Rating:"));
            double business = Double.parseDouble(showInputDialog("Enter Business Profit (in millions):"));

            programs.add(new NewsChannel(programName, anchor, month, trpRating, business));
            outputArea.appendText("Added: " + programName + "\n");
        } catch (NumberFormatException e) {
            showWarning("Invalid input! Please enter numbers for Month, TRP Rating, and Business Profit.");
        }
    }

    private String showInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("").trim();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void findMaxCollectionMonth() {
        double[] monthlyTotal = new double[12];
        for (Channel program : programs) {
            monthlyTotal[program.getMonth() - 1] += program.getBusiness();
        }
        int maxMonth = 0;
        double maxProfit = 0;
        for (int i = 0; i < 12; i++) {
            if (monthlyTotal[i] > maxProfit) {
                maxProfit = monthlyTotal[i];
                maxMonth = i + 1;
            }
        }
        outputArea.appendText("\nMonth with max profit: " + maxMonth + "\n");
    }

    private void findBusyAnchor() {
        Map<String, Integer> anchorCount = new HashMap<>();
        for (Channel program : programs) {
            anchorCount.put(program.getAnchor(), anchorCount.getOrDefault(program.getAnchor(), 0) + 1);
        }
        int maxPrograms = Collections.max(anchorCount.values());
        if (maxPrograms == 1) {
            outputArea.appendText("\nNo busy anchors\n");
            return;
        }
        List<String> busiestAnchors = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : anchorCount.entrySet()) {
            if (entry.getValue() == maxPrograms) {
                busiestAnchors.add(entry.getKey());
            }
        }
        outputArea.appendText("\nBusiest Anchor(s): " + String.join(", ", busiestAnchors) + "\n");
    }

    private void sortByTRP() {
        programs.sort(Comparator.comparingDouble(Channel::getTrpRating).reversed());
        outputArea.appendText("\nPrograms sorted by TRP:\n");
        for (Channel program : programs) {
            outputArea.appendText(program.getProgramName() + " - TRP: " + program.getTrpRating() + "\n");
        }
    }

    private void findLeastTRPProgram() {
        if (programs.isEmpty()) {
            outputArea.appendText("\nNo programs available to analyze.\n");
            return;
        }
        Channel leastTRP = Collections.min(programs, Comparator.comparingDouble(Channel::getTrpRating));
        outputArea.appendText("\nProgram with least TRP: " + leastTRP.getProgramName() + " - TRP: " + leastTRP.getTrpRating() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
