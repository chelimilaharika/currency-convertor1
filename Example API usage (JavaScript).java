//Example API usage (JavaScript):
fetch(`https://api.exchangerate-api.com/v4/latest/USD`)
  .then(response => response.json())
  .then(data => {
    const rate = data.rates['EUR'];
    const converted = amount * rate;
    console.log(converted);
  });

ACTUALCODE 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class CurrencyConverter extends JFrame
 {
    private JComboBox<String> fromCurrency, toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;
    private final String[] currencies = {"USD", "EUR", "INR", "GBP", "JPY"};
    private final String API_KEY = "YOUR_API_KEY"; // Replace with your API key
    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        fromCurrency = new JComboBox<>(currencies);
        toCurrency = new JComboBox<>(currencies);

        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel("Converted amount will appear here");
        resultLabel.setForeground(new Color(0, 128, 0));
        convertButton.addActionListener(e -> convertCurrency());
        setLayout(new GridLayout(6, 1, 10, 10));
        add(amountLabel);
        add(amountField);
        add(new JLabel("From Currency:"));
        add(fromCurrency);
        add(new JLabel("To Currency:"));
        add(toCurrency);
        add(convertButton);
        add(resultLabel);
    }
    private void convertCurrency() {
        String amountText = amountField.getText();
        if (amountText.isEmpty() || !amountText.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }
        double amount = Double.parseDouble(amountText);
        String from = (String) fromCurrency.getSelectedItem();
        String to = (String) toCurrency.getSelectedItem();
        try {
            String urlStr = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + from;
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder jsonText = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonText.append(inputLine);
            }
            in.close();
            JSONObject json = new JSONObject(jsonText.toString());
            double rate = json.getJSONObject("conversion_rates").getDouble(to);
            double result = rate * amount;

            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, from, result, to));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching exchange rate:\n" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        // Ensure JSON library is available
        try {
            Class.forName("org.json.JSONObject");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "JSON library (org.json) not found.\nPlease include it in your project.");
            return;
        }
        SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true));
    }}
