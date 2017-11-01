import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//For parsing the command line arguments
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

//Telnet client to properly execute on Windows environments
import org.apache.commons.net.telnet.TelnetClient;

public class Main {

	/*
	public static final String ADB_ROOT = "C:\\Users\\JC\\AppData\\Local\\Android\\sdk\\platform-tools\\";
	public static final String ADB_INPUT = ADB_ROOT + "adb shell input ";
	public static final String TELNET_TOKEN = "zT1y79dpWWhWmcuX";
	//public static final String EMULATOR_PORT = "5554";
	public static final Integer EMULATOR_PORT = 5554;
	public static TelnetClient telnetClient = null;
	*/
	
	public static final String ADB_ROOT = "C:\\Users\\jarias\\AppData\\Local\\Android\\sdk\\platform-tools\\";
	public static final String ADB_INPUT = ADB_ROOT + "adb shell input ";
	public static final String TELNET_TOKEN = "CfrYbOKMZT9j5uc3";
	//public static final String EMULATOR_PORT = "5554";
	public static final Integer EMULATOR_PORT = 5554;
	public static TelnetClient telnetClient = null;
	
	private static BufferedWriter connectToTelnet() throws IOException {
		//Runtime rt = Runtime.getRuntime();
		//Process telnet = rt.exec("telnet localhost "+EMULATOR_PORT);
		//return new BufferedWriter(new OutputStreamWriter(telnet.getOutputStream()));
		telnetClient = new TelnetClient();
        telnetClient.connect("localhost", EMULATOR_PORT);
        return new BufferedWriter(new OutputStreamWriter(telnetClient.getOutputStream()));
	}
	
	public static void rotate() throws IOException {
		BufferedWriter out = connectToTelnet();
		out.write("auth "+TELNET_TOKEN+"\n");
		out.write("rotate\n");
		out.write("quit\n");
		out.flush();
	}

	public static void network() throws IOException {
		BufferedWriter out = connectToTelnet();
		out.write("auth "+TELNET_TOKEN+"\n");
		out.write("network speed 500 600\n");
		//Sets the speed back to its full state
		out.write("network speed full\n");
		out.write("quit\n");
		out.flush();
	}

	public static void sensor() throws IOException {
		BufferedWriter out = connectToTelnet();
		out.write("auth "+TELNET_TOKEN+"\n");
		out.write("sensor set acceleration 2.23517e-07:9.77631:0.812348\n");
		out.write("quit\n");
		out.flush();
	}
	
	public static void main(String[] args) {
		
		int commandsDistribution = 0;
		//The events distribution is set to 50 as the default, this is the total of events to distribute between the commands selected by the user.
		int eventsDistribution = 50;
		int tapDistribution = 0;
		int textDistribution = 0;
		int swipeDistribution = 0;
		int keyEventDistribution = 0;
		int rotateDistribution = 0;
		int networkDistribution = 0;
		int sensorDistribution = 0;
		int tapExecutions = 0;
		int textExecutions = 0;
		int swipeExecutions = 0;
		int keyEventExecutions = 0;
		int rotateExecutions = 0;
		int networkExecutions = 0;
		int sensorExecutions = 0;
		String APKSourcePath = "";
		String APKName = "";
		String APKPackageName = "";
		boolean apkInfo1 = false;
		boolean apkInfo2 = false;
		boolean apkInfo3 = false;
		
		CommandLine commandLine;
		Option option_Events = Option.builder("Events").argName("Events").hasArg().desc("Number of events to execute").build();
		Option option_Tap = Option.builder("Tap").argName("Tap").hasArg().desc("Tap command").build();
		Option option_Text = Option.builder("Text").argName("Text").hasArg().desc("Text command").build();
		Option option_Swipe = Option.builder("Swipe").argName("Swipe").hasArg().desc("Swipe command").build();
		Option option_Keyevent = Option.builder("Keyevent").argName("Keyevent").hasArg().desc("Keyevent command").build();
		Option option_Rotate = Option.builder("Rotate").argName("Rotate").hasArg().desc("Rotate command").build();
		Option option_Network = Option.builder("Network").argName("Network").hasArg().desc("Network command").build();
		Option option_Sensor = Option.builder("Sensor").argName("Sensor").hasArg().desc("Sensor command").build();
		Option option_APKSourcePath = Option.builder("APKSourcePath").argName("APKSourcePath").hasArg().desc("APK Source Path, Sample: E:\\\\03 Talleres\\\\07 Taller 7\\\\Monkey\\\\, Include the double quotes at the beginning and at the end").build();
		Option option_APKName = Option.builder("APKName").argName("APKName").hasArg().desc("APK Name, Sample: org.tasks_481.apk").build();
		Option option_APKPackageName = Option.builder("APKPackageName").argName("APKPackageName").hasArg().desc("APK Package Name, Sample: org.tasks").build();
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		
		options.addOption(option_Events);
		options.addOption(option_Tap);
		options.addOption(option_Text);
		options.addOption(option_Swipe);
		options.addOption(option_Keyevent);
		options.addOption(option_Rotate);
		options.addOption(option_Network);
		options.addOption(option_Sensor);
		options.addOption(option_APKSourcePath);
		options.addOption(option_APKName);
		options.addOption(option_APKPackageName);

		String header = "\nOptions may be in any order\n\n";
		String footer = "\nUsing Apache Commons CLI 1.3.1\n\n";
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Main", header, options, footer, true);
		
		Runtime rt = Runtime.getRuntime();
		
		try
		{
			commandLine = parser.parse(options, args);

			if (commandLine.hasOption("Events")) {
				System.out.print("Option Events is present, default is 50. The total number of events to execute is: ");
				System.out.println(commandLine.getOptionValue("Events"));
				eventsDistribution = Integer.parseInt(commandLine.getOptionValue("Events"));
			} else {
				System.out.println("Option Events is not present, the default value of 50 will be used. 50 events will be executed.");
			}

			if (commandLine.hasOption("Tap")) {
				System.out.print("Option Tap is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Tap"));
				tapDistribution = Integer.parseInt(commandLine.getOptionValue("Tap"));
				commandsDistribution += tapDistribution;
			}

			if (commandLine.hasOption("Text")) {
				System.out.print("Option Text is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Text"));
				textDistribution = Integer.parseInt(commandLine.getOptionValue("Text"));
				commandsDistribution += textDistribution;
			}
			
			if (commandLine.hasOption("Swipe")) {
				System.out.print("Option Swipe is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Swipe"));
				swipeDistribution = Integer.parseInt(commandLine.getOptionValue("Swipe"));
				commandsDistribution += swipeDistribution;
			}

			if (commandLine.hasOption("Keyevent")) {
				System.out.print("Option Keyevent is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Keyevent"));
				keyEventDistribution = Integer.parseInt(commandLine.getOptionValue("Keyevent"));
				commandsDistribution += keyEventDistribution;
			}

			if (commandLine.hasOption("Rotate")) {
				System.out.print("Option Rotate is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Rotate"));
				rotateDistribution = Integer.parseInt(commandLine.getOptionValue("Rotate"));
				commandsDistribution += rotateDistribution;
			}

			if (commandLine.hasOption("Network")) {
				System.out.print("Option Network is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Network"));
				networkDistribution = Integer.parseInt(commandLine.getOptionValue("Network"));
				commandsDistribution += networkDistribution;
			}

			if (commandLine.hasOption("Sensor")) {
				System.out.print("Option Sensor is present. The distribution is: ");
				System.out.println(commandLine.getOptionValue("Sensor"));
				sensorDistribution = Integer.parseInt(commandLine.getOptionValue("Sensor"));
				commandsDistribution += sensorDistribution;
			}

			if (commandLine.hasOption("APKSourcePath")) {
				System.out.print("Option APKSourcePath is present: ");
				System.out.println(commandLine.getOptionValue("APKSourcePath"));
				APKSourcePath = commandLine.getOptionValue("APKSourcePath");
				apkInfo1 = true;
			}

			if (commandLine.hasOption("APKName")) {
				System.out.print("Option APKName is present: ");
				System.out.println(commandLine.getOptionValue("APKName"));
				APKName = commandLine.getOptionValue("APKName");
				apkInfo2 = true;
			}
			
			if (commandLine.hasOption("APKPackageName")) {
				System.out.print("Option APKPackageName is present: ");
				System.out.println(commandLine.getOptionValue("APKPackageName"));
				APKPackageName = commandLine.getOptionValue("APKPackageName");
				apkInfo3 = true;
			}
						
			//When using the APK parameters, all of the three parameters are needed, for the proper execution of the program
			if (apkInfo1 == true & apkInfo2 == true & apkInfo3 == true) {
				//OK
			} else {
				System.out.println();
				System.out.println("APK Input Error: All parameters for APK are required.");
				System.out.println();
				System.exit(0);
			}
			
			//Commands distribution
			System.out.println();
			System.out.println("Commands distribution: " + commandsDistribution);
			System.out.println();
			
			if (commandsDistribution != 100){
				System.out.println();
				System.out.println("Input Error: The commands distribution must be equal to 100.");
				System.out.println();
				System.exit(0);
			}
			
			//Sets the distribution to the commands, percentage is used.
			tapExecutions = (eventsDistribution * tapDistribution)/100;
			textExecutions = (eventsDistribution * textDistribution)/100;
			swipeExecutions = (eventsDistribution * swipeDistribution)/100;
			keyEventExecutions = (eventsDistribution * keyEventDistribution)/100;
			rotateExecutions = (eventsDistribution * rotateDistribution)/100;
			networkExecutions = (eventsDistribution * networkDistribution)/100;
			sensorExecutions = (eventsDistribution * sensorDistribution)/100;
			
			// Params: APKSourcePath, APKName, APKPackageName
			// APKSourcePath    : "E:\\03 Talleres\\07 Taller 7\\Monkey\\"
			// APKName          : org.tasks_481.apk
			// APKPackageName   : org.tasks
			
			//It is necessary to copy the APK so it can be properly installed.
			Path FROM = Paths.get(APKSourcePath + APKName);
			Path TO = Paths.get(ADB_ROOT + APKName);
			CopyOption[] copyOptions = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
			};
			Files.copy(FROM, TO, copyOptions);
			
			//Installs the APK
			rt.exec(ADB_ROOT + "adb install -r " + APKName);
			
			//It is required to hold the program (timer)
			Thread.sleep(3000);
			
			//Launches the app
			rt.exec(ADB_ROOT + "adb shell monkey -p " + APKPackageName + " -c android.intent.category.LAUNCHER 1");

			//It is required to hold the program (timer)
			Thread.sleep(3000);
			
			//Uninstalls the app, uses the package name
			//adb uninstall org.tasks
			
			//Random to be used on the commands
			Random random = new Random(12345);
			int i = 0;

			//Telnet Executions - Rotate
			while(i < rotateExecutions) {
				int rotate = random.nextInt(2);
				if (rotate == 1) {
					Main.rotate();
				}
				i++;
				Thread.sleep(1000);
			}

			//Telnet Executions - Network
			i = 0;
			while(i < networkExecutions) {
				int network = random.nextInt(2);
				if (network == 1) {
					Main.network();
				}
				i++;
				Thread.sleep(1000);
			}

			//Telnet Executions - Sensor
			i = 0;
			while(i < sensorExecutions) {
				int sensor = random.nextInt(2);
				if (sensor == 1) {
					Main.sensor();
				}
				i++;
				Thread.sleep(1000);
			}
			
			//tapExecutions
			i = 0;
			while(i < tapExecutions) {
				int x = random.nextInt(1080);
				int y = random.nextInt(1920);
				rt.exec(ADB_INPUT + "tap " + x + " " + y);
				i++;
				Thread.sleep(1000);
			}
			
			//textExecutions
			i = 0;
			while(i < textExecutions) {
				int x = random.nextInt(1080);
				int y = random.nextInt(1920);
				rt.exec(ADB_INPUT + "tap " + x + " " + y);
				Thread.sleep(1000);
				rt.exec(ADB_INPUT + "text Paris");
				i++;
				Thread.sleep(1000);
			}
			
			//swipeExecutions
			i = 0;
			while(i < swipeExecutions) {
				int x = random.nextInt(1080);
				int y = random.nextInt(1920);
				int x1 = random.nextInt(1080);
				int y1 = random.nextInt(1920);
				rt.exec(ADB_INPUT + "swipe " + x + " " + y + " " + x1 + " " + y1);
				i++;
				Thread.sleep(1000);
			}
			
			//keyEventExecutions
			i = 0;
			while(i < keyEventExecutions) {
				rt.exec(ADB_INPUT + "keyevent 26");
				rt.exec(ADB_INPUT + "keyevent 26");
				i++;
				Thread.sleep(1000);
			}

			/*
			rt.exec(ADB_INPUT + "tap 929 144");
			Thread.sleep(1000);
			rt.exec(ADB_INPUT + "tap 155 273");
			*/
		}
		catch (ParseException exception) {
			System.out.print("Parse error: ");
			System.out.println(exception.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}