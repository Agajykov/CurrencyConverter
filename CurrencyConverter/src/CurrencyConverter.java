import java.util.Scanner;

/**
 * CurrencyConverter class that allows users to convert currencies.
 * Author: Avdyrahman Agajykov
 */
public class CurrencyConverter {
	static boolean dontWantToExit = true;

	/**
	 * Main method to execute the currency conversion process.
	 * It displays currency options, asks the user for input, performs conversion, and prints results.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		do {
			Scanner scanner = new Scanner(System.in);

			displayCurrencyOptionPannel();

			System.out.println("Please select the Source Currency by pressing the button");
			System.out.print("Source Currency Selection: ");
			int sourceCurrencyButton = scanner.nextInt();

			System.out.println("Please select the Target Currency by pressing the button");
			System.out.print("Target Currency Selection: ");
			int targetCurrencyButton = scanner.nextInt();

			double conversionRate = getConversionRate(sourceCurrencyButton, targetCurrencyButton);

			String sourceCurrencyName = getCurrencyName(sourceCurrencyButton);
			String targetCurrencyName = getCurrencyName(targetCurrencyButton);

			System.out.printf("Amount to Convert from %s to %s: ", sourceCurrencyName, targetCurrencyName);
			double amountToConvert = scanner.nextDouble();

			double targetAmount = convertToTarget(amountToConvert, conversionRate);

			printConversionDetails(sourceCurrencyName, targetCurrencyName, conversionRate, amountToConvert, targetAmount);

			// Ask user if they want to continue
			isUserFinished();
		} 
		while (dontWantToExit);
	}

	/**
	 * Prints the conversion details in a formatted manner.
	 * 
	 * @param baseCurrencyName the name of the base currency
	 * @param targetCurrencyName the name of the target currency
	 * @param conversionRate the conversion rate from base currency to target currency
	 * @param amountInBaseCurrency the amount in the base currency to convert
	 * @param targetAmount the converted amount in the target currency
	 */
	private static void printConversionDetails(String baseCurrencyName, String targetCurrencyName, 
												double conversionRate, double amountInBaseCurrency, double targetAmount) {
		System.out.println("=========================================");
		System.out.println("   Currency Conversion Details");
		System.out.println("=========================================");
		System.out.printf("Currency: %s to %s\n", baseCurrencyName, targetCurrencyName);
		System.out.printf("Exchange Rate: 1 %s = %.2f %s\n", baseCurrencyName, conversionRate, targetCurrencyName);
		System.out.printf("Amount in %s: %.2f\n", baseCurrencyName, amountInBaseCurrency);
		System.out.printf("Converted Amount in %s: %.2f\n", targetCurrencyName, targetAmount);
		System.out.println("=========================================");
	}

	/**
	 * Retrieves the name of the currency based on the given index.
	 * 
	 * @param currencyIndex the index of the currency
	 * @return the name of the currency
	 */
	private static String getCurrencyName(int currencyIndex) {
		if (currencyIndex >= 1 && currencyIndex <= Currencies.values().length) {
			return Currencies.values()[currencyIndex - 1].name();
		} else {
			return "Invalid index";
		}
	}

	/**
	 * Returns the conversion rate between the base and target currencies.
	 * Handles both direct (USD to other currencies) and reverse (other currencies to USD) conversions.
	 * 
	 * @param baseCurrency the index of the base currency
	 * @param targetCurrency the index of the target currency
	 * @return the conversion rate between the two currencies
	 */
	private static double getConversionRate(int baseCurrency, int targetCurrency) {
		// Get rates for base and target currencies
		double sourceRate = Currencies.values()[baseCurrency - 1].getRate();
		double targetRate = Currencies.values()[targetCurrency - 1].getRate();

		// If base currency is USD, return the target currency rate directly
		if (baseCurrency == 1) {
			return targetRate;  // USD to target currency
		} else if (targetCurrency == 1) {
			return 1 / sourceRate; // Target currency to USD
		} else {
			// Calculate non-USD to non-USD conversion
			return targetRate / sourceRate;
		}
	}

	/**
	 * Asks the user if they want to perform more conversions and exits if the user inputs 'n'.
	 * 
	 * @param scanner the scanner to capture user input
	 */
	private static void isUserFinished() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like to do more conversions? press 'y' for yes 'n' for no");
		String userInput = scanner.nextLine();
		if ("n".equals(userInput)) {
			dontWantToExit = false;
			System.out.println("Bye");
		}
	}

	/**
	 * Converts the given amount from the base currency to the target currency.
	 * 
	 * @param amountToConvert the amount in the base currency to convert
	 * @param conversionRate the conversion rate between the base and target currencies
	 * @return the converted amount in the target currency
	 */
	private static double convertToTarget(double amountToConvert, double conversionRate) {
		return amountToConvert * conversionRate;
	}

	/**
	 * Enum for defining various currencies and their respective conversion rates to USD.
	 */
	public enum Currencies {
		USD(1),
		EUR(0.88),
		GBP(0.75),
		JPY(142.38),
		INR(85.38),
		TKM(3.49);

		private final double conversionRate;

		Currencies(double conversionRate) {
			this.conversionRate = conversionRate;
		}

		/**
		 * Returns the conversion rate of the currency.
		 * 
		 * @return the conversion rate of the currency
		 */
		public double getRate() {
			return conversionRate;
		}
	}

	/**
	 * Displays a panel with available currencies and their selection buttons.
	 * 
	 * For the simplicity I used numbers as selection criteria to resemble 
	 * buttons.
	 */
	private static void displayCurrencyOptionPannel() {
		System.out.println("----- Welcome to Currency Conversion app -----");
		System.out.printf("%-10s | %s\n", "Currency", "Button");
		System.out.println("------------------------");
		int i = 1;
		for (Currencies currency : Currencies.values()) {
			System.out.printf("%-10s | %d\n", currency.name(), i++);
		}
	}
}
