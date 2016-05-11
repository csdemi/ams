package travelpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cody on 3/15/2016.
 */
public class utilities {
    private static final Scanner kb = new Scanner(System.in);

    private static void parse(SystemManager res){
        String inFile;
        try
        {
            System.out.println("Enter an input file name: ");
            inFile = readFileName();
            Scanner inputFile = openFile(inFile);
            readFile(inputFile,res);
        }
        catch(Exception e)
        {
            System.out.println("input file open failure");
        }

    }

    private static void readFile(Scanner inputFile, SystemManager res) {
        String information="";
        while(inputFile.hasNextLine()){
            information=inputFile.nextLine();
        }
        parseString(information,res);
    }

    private static void parseString(String information, SystemManager res) {
        String air;
        Pattern airports=Pattern.compile("\\[([A-Z]{3}?\\,\\s)*([A-Z]{3}?)\\]");
        Pattern airport= Pattern.compile("([A-Z]{3}?)");
        Pattern data=Pattern.compile("\\{(.*?)\\}");
        Pattern airline= Pattern.compile("(\\{[A-Z]{1,6})|(\\]\\]\\,\\s[A-Z]{1,6})");
        Pattern airlines=Pattern.compile("[A-Z]{1,6}");
        Matcher m =airports.matcher(information);
        m.find();
        air=information.substring(m.start(),m.end());
        Matcher n = airport.matcher(air);
        while(n.find()){
            String find =air.substring(n.start(),n.end());
            res.createAirport(find);
        }
        Matcher d =data.matcher(information);
        d.find();
        String infer=information.substring(d.start(), d.end());
        Matcher c = airline.matcher(infer);
        String usable="";
        while(c.find()){
            usable +=infer.substring(c.start(),c.end());
        }
        Matcher airLine = airlines.matcher(usable);
        while(airLine.find()){
            String AirLine=usable.substring(airLine.start(),airLine.end());
            res.createAirline(AirLine);
            Pattern lineStringPattern=Pattern.compile("("+AirLine+".+?(?=\\]\\]\\,\\s))|("+AirLine+".+?(?=\\]\\]))");
            Matcher lineMatcher = lineStringPattern.matcher(infer);
            while(lineMatcher.find()) {
                String lineString=infer.substring(lineMatcher.start(),lineMatcher.end());
                Pattern flightIDs=Pattern.compile("(\\[(.+))");
                Matcher flIDS=flightIDs.matcher(lineString);
                String flightIDInfo="";
                while(flIDS.find()) {
                    flightIDInfo += lineString.substring(flIDS.start(), flIDS.end());
                }
                Pattern flight = Pattern.compile("([A-Z0-9].+?(?=\\]\\,))|([A-Z0-9](.)*)");
                Matcher f = flight.matcher(flightIDInfo);
                while(f.find()){
                    String flights=flightIDInfo.substring(f.start(),f.end());
                    Pattern id = Pattern.compile("([A-Z0-9]+)");
                    Matcher idf = id.matcher(flights);
                    idf.find();
                    String fid = flights.substring(idf.start(),idf.end());

                    Pattern year = Pattern.compile("[0-9]{4}");
                    Matcher fyear = year.matcher(flights);
                    fyear.find();
                    int yr =Integer.parseInt(flights.substring(fyear.start(), fyear.end()));

                    Pattern month = Pattern.compile("[0-9]{1,2}");
                    Matcher fmonth = month.matcher(flights);
                    fmonth.find(fyear.end());
                    int mth=Integer.parseInt(flights.substring(fmonth.start(),fmonth.end()));

                    Pattern day = Pattern.compile("[0-9]{1,2}");
                    Matcher fday = day.matcher(flights);
                    fday.find(fmonth.end());
                    int dy=Integer.parseInt(flights.substring(fday.start(),fday.end()));

                    Pattern hour = Pattern.compile("[0-9]{1,2}");
                    Matcher fhour = hour.matcher(flights);
                    fhour.find(fday.end());
                    int hr=Integer.parseInt(flights.substring(fhour.start(),fhour.end()));

                    Pattern min = Pattern.compile("[0-9]{1,2}");
                    Matcher fmin = min.matcher(flights);
                    fmin.find(fhour.end());
                    int minute=Integer.parseInt(flights.substring(fmin.start(),fmin.end()));

                    Matcher airportMatcher = airport.matcher(flights);
                    airportMatcher.find(fmin.end());
                    String orgin = flights.substring(airportMatcher.start(),airportMatcher.end());
                    airportMatcher.find(airportMatcher.end());
                    String dest = flights.substring(airportMatcher.start(),airportMatcher.end());
                    res.createFlight(AirLine,dest,orgin,yr,mth,dy,fid,hr,minute);
                    Pattern section = Pattern.compile("\\[[A-Z]{1}(.)*");
                    Matcher fSection =section.matcher(flights);
                    while(fSection.find()) {
                        String sections = flights.substring(fSection.start(), fSection.end());
                        Pattern sect = Pattern.compile("([A-Z]\\:[0-9]+\\:[A-Z]\\:[0-9]+)");
                        Matcher fsect = sect.matcher(sections);
                        while(fsect.find()){
                            String aSection = sections.substring(fsect.start(),fsect.end());
                            Pattern sectionInfo = Pattern.compile("[A-Z]");
                            Matcher sclassMatcher = sectionInfo.matcher(aSection);
                            sclassMatcher.find();
                            String seatClassType = aSection.substring(sclassMatcher.start(), sclassMatcher.end());

                            Pattern price = Pattern.compile("[0-9]+");
                            Matcher fprice = price.matcher(aSection);
                            fprice.find(sclassMatcher.end());
                            int value = Integer.parseInt(aSection.substring(fprice.start(),fprice.end()));

                            Pattern seatLayout = Pattern.compile("[A-Z]");
                            Matcher fSeatLayout = seatLayout.matcher(aSection);
                            fSeatLayout.find(fprice.end());
                            String row = aSection.substring(fSeatLayout.start(),fSeatLayout.end());


                            Pattern rows = Pattern.compile("[0-9]+");
                            Matcher frows = rows.matcher(aSection);
                            frows.find(fSeatLayout.end());
                            int numRows = Integer.parseInt(aSection.substring(frows.start(),frows.end()));


                            res.createSection(AirLine,fid,numRows,SeatLayoutEnum.toLayout(row).getCols(),SeatClass.toType(seatClassType),value,SeatLayoutEnum.toLayout(row));
                        }
                    }
                }
            }
        }
    }

    private static String readFileName() {
        String input = kb.nextLine();
        while(!validateFileName(input))
        {
            System.out.println("Invalid file name.");
            System.out.println("Please enter a different file name");
            input = kb.nextLine();
        }
        return input;
    }

    private static boolean validateFileName(String input) {
        boolean isValid = false;

        if(input.length() > 0 && input.length() <= 50)
        {
            if(/*input.contains("SecureProgram/") && */input.endsWith(".txt"))
            {
                isValid = true;
            }
        }
        return isValid;
    }

    private static Scanner openFile(String fileName) throws IOException {
        Scanner fileReader;
        fileReader = new Scanner(new File(fileName));
        return fileReader;
    }

    private static void changePrice(SystemManager res) {
        String airline,orgin,destination,type;
        int price;
        airline = getAirlineName();
        destination = getOrginPort();
        orgin = getDestinationPort();
        type = getSeatClassType();
        price = getPrice();
        res.changeFlightSectionPrice(orgin, destination, airline, type, price);
    }

    private static int getPrice() {
        String temp;
        int price=0;
        System.out.println("Enter new price:");
        temp = kb.nextLine();
        boolean priceParsed = true;
        try{
            price=Integer.valueOf(temp);
        }catch (NumberFormatException e){
            e.printStackTrace();
            priceParsed=false;
        }
        while(!priceParsed){
            System.out.println("Enter new price:");
            temp = kb.nextLine();
            priceParsed = true;
            try{
                price=Integer.valueOf(temp);
            }catch (NumberFormatException e){
                e.printStackTrace();
                priceParsed=false;
            }
        }
        return price;
    }

    private static String getSeatClassType() {
        String type;
        System.out.println("Enter an seat type(Economy,Business,First)");
        type=kb.nextLine().toUpperCase().substring(0,1);
        while(type==null){
            System.out.println("Enter an seat type(Economy,Business,First)");
            type=kb.nextLine().toUpperCase().substring(0,1);
        }
        return type;
    }
    private static String getLayoutClassType() {
        String type;
        System.out.println("Enter a layout(S,M,W)");
        type=kb.nextLine().toUpperCase();
        while(type==null&&type.length()<2&&type.length()>0){
            System.out.println("Enter a layout(S,M,W)");
            type=kb.nextLine().toUpperCase();
        }
        return type;
    }
    private static String getDestinationPort() {
        String orgin;
        System.out.println("Enter a Destination airport(max 3 characters)");
        orgin=kb.nextLine().toUpperCase();
        while(orgin.length()!=3){
            System.out.println("Enter a Destination airport(max 3 characters)");
            orgin=kb.nextLine().toUpperCase();
        }
        return orgin;
    }

    private static String getOrginPort() {
        String destination;
        System.out.println("Enter Orgin airport(max 3 characters)");
        destination=kb.nextLine().toUpperCase();
        while(destination.length()!=3){
            System.out.println("Enter Orgin airport(max 3 characters)");
            destination=kb.nextLine().toUpperCase();
        }
        return destination;
    }
    private static String getAirportName() {
        String airportName;
        System.out.println("Enter airport name(max 3 characters)");
        airportName=kb.nextLine().toUpperCase();
        while(airportName.length()!=3){
            System.out.println("Enter airport name(max 3 characters)");
            airportName=kb.nextLine().toUpperCase();
        }
        return airportName;
    }
    private static String getAirlineName() {
        String airline;
        System.out.println("Enter an Airline name(max 6 characters)");
        airline=kb.nextLine().toUpperCase();
        while(airline.length()>7){
            System.out.println("Enter an Airline name(max 6 characters)");
            airline=kb.nextLine().toUpperCase();
        }
        return airline;
    }

    public static void executeOrder66() {
        SystemManager res = new SystemManager();
        String choice;
        do
        {
            choice = menu();
            try {
                excecuteChoice(choice, res);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }while (!choice.equals('q'));
    }

    private static String menu() {
        System.out.println(""
                +"a.) Create an airport system by using information provided in an input file.\n"
                +"b.) Change the price associated with seats in a flight section (all seats in a flight section have the same price).\n"
                +"c.) Query for flights.\n"
                +"d.) Change the seat class (e.g., economy) pricing for an origin and destination for a given airline.\n"
                +"e.) Book a seat given a specific seat on a flight.\n"
                +"f.) Book a seat on a flight by seating preference\n"
                +"g.) Display details of the airport system.)\n"
                +"h.) Store information about the airport system in a specified file.\n"
                +"i.) Create Airport\n"
                +"j.) Create Airline\n"
                +"k.) Create Flight\n"
                +"l.) Quit");
        System.out.print("Choice-->");
        String letter = utilities.kb.nextLine();
        while(letter.equals('a') || letter.equals('l') )
        {
            System.out.println("Invalid input, try again");
            letter = utilities.kb.next();
        }
        return letter;

    }// end menu

    private static void excecuteChoice(String choice, SystemManager res) throws FileNotFoundException {
        switch (choice) {
            case "a":
                parse(res);
                break;
            case "b":
                changePrice(res);
                break;
            case "c":
                specifyType(res);
                break;
            case "d":
                changePrice(res);
                break;
            case "e":
                res.bookSeat();
                break;
            case "f":
                classSeatingPreference(res);
                break;
            case "g":
                res.displaySystemDetails();
                break;
            case "h":
                res.writeFile();
                break;
            case "i":
                res.createAirport(getAirportName());
                break;
            case "j":
                res.createAirline(getAirlineName());
                break;
            case "k":
                getFlightInfo(res);
                break;
            default:
                System.out.println("Exiting program.");
                System.exit(0);
        }
    }

    private static void getFlightInfo(SystemManager res) {
        String AirLine,dest,orgin;
        AirLine=getAirlineName();
        dest=getDestinationPort();
        orgin=getOrginPort();
        System.out.println("Please input FlightID:");
        String fid = kb.nextLine();
        System.out.println("Year:");
        int yr=Integer.parseInt(kb.nextLine());
        System.out.println("Month:");
        int mth = Integer.parseInt(kb.nextLine());
        System.out.println("Day:");
        int dy = Integer.parseInt(kb.nextLine());
        System.out.println("Hour:");
        int hr = Integer.parseInt(kb.nextLine());
        System.out.println("Minute:");
        int minute = Integer.parseInt(kb.nextLine());
        System.out.println("How many sections will this flight have:");
        int count = Integer.parseInt(kb.nextLine());
        res.createFlight(AirLine,dest,orgin,yr,mth,dy,fid,hr,minute);
        for(int i = 0;i<count;i++){
            String classType = getSeatClassType();
            int price=getPrice();
            String layout = getLayoutClassType();
            System.out.println("Please indicate number of rows in this section:");
            String row = kb.nextLine();
            //createSection(String name, String flID, int rows, int cols, SeatClass s, int value,SeatLayoutEnum sle)
            res.createSection(AirLine,fid,Integer.parseInt(row),SeatLayoutEnum.toLayout(layout).getCols(),SeatClass.toType(classType),price,SeatLayoutEnum.toLayout(layout));
            //res.createSection(AirLine,fid,Integer.parseInt(row),SeatLayoutEnum.toLayout(row).getCols(),SeatClass.toType(classType),price,SeatLayoutEnum.toLayout(layout));
            System.out.println("Created flight");
        }


    }

    private static void classSeatingPreference(SystemManager res) {
        System.out.println("Please input FlightID:");
        String fid = kb.nextLine();
        String type = getSeatClassType();
        System.out.println("Preference of seating: Window/Asile/None");
        String pref = kb.nextLine().toLowerCase();
        res.seatByPref(fid,type,pref);
    }

    private static void specifyType(SystemManager res) {
        int year,month,day;
        String orgin = getDestinationPort();
        String destination = getOrginPort();
        String type = getSeatClassType();
        System.out.println("What day do you want to arrive?");
        day=Integer.parseInt(kb.nextLine());
        System.out.println("What month do you want to arrive?");
        month=Integer.parseInt(kb.nextLine());
        System.out.println("What year do you want to arrive?");
        year=Integer.parseInt(kb.nextLine());
        res.queryFlights(month,day,year,orgin,destination,type);
    }
}
