package clientewsperiodictable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;



public class ClienteWSPeriodicTable {

    public static void main(String[] args) throws IOException, Exception {
        while(true){
            String op = Menu();
            switch (op) {
                case "1":   numAtom();                            
                            break;
                case "2":   pesAtom();
                            break;
                case "3":   getAllAtom();
                            break;
                case "4":   symbolAtom();
                            break;
                case "5":   obtainData();
                            break;
                default:    System.out.println("Saliendo del programa...");
                            System.exit(0);
                            break;
            }
        }   
    }
    private static String Menu() throws IOException{
        System.out.println("---------------------Menu-------------------------------");       
        System.out.println("Ponga un número del 1 al 5 con las siguientes opciones: ");
        System.out.println("1. Obtener el número atómico");
        System.out.println("2. Obtener el peso atómico");
        System.out.println("3. Obtener una tabla con todos los elementos");
        System.out.println("4. Obtener el símbolo del elemento");
        System.out.println("5. Obtener todos los datos de un elemento");
        System.out.println("Otras opciones harán que el programa finalice");
        System.out.println("--------------------------------------------------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String op = br.readLine();       
        return op;
    }

    private static String getAtomicWeight(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicWeight(elementName);
    }
    private static String getElementSymbol(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getElementSymbol(elementName);
    }

    private static String getAtomicNumber(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicNumber(elementName);
    }

    private static void numAtom() throws IOException {
        System.out.println("Por favor, ingrese el elemento en cuestión: ");  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ele = br.readLine();
        String atomicNumber = parseResponse(getAtomicNumber(ele), "</AtomicNumber>");
        System.out.println("El número del elemento " + ele + "es igual a: " + atomicNumber);
    }

    private static void pesAtom() throws IOException {
        System.out.println("Por favor, ingrese el elemento en cuestión: ");                 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ele = br.readLine();  
        String weight = parseResponse(getAtomicWeight(ele), "</AtomicWeight>");
        System.out.println("El peso del elemento " + ele + "es igual a: " + weight);
    }    

    private static void symbolAtom() throws IOException {
        System.out.println("Por favor, ingrese el elemento en cuestión: ");  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ele = br.readLine();  
        String symbol = parseResponse(getElementSymbol(ele), "</Symbol>");
        System.out.println("El símbolo del elemento " + ele + "es igual a: " + symbol);
    }   
    private static String parseResponse(String response, String endTag) {
        String beginTag = endTag.replace("/", "");
        final int from = response.indexOf(beginTag);
        final int to = response.lastIndexOf(endTag);
        final String beginTagAndContent = response.substring(from, to);
        return beginTagAndContent.substring(beginTagAndContent.indexOf(">") + 1);
    }
    private static String[] parseResponseGroup(String response) {
        String[] res = new String[120];
        response = response.replaceAll("<NewDataSet>", "\n");
        response = response.replaceAll("</NewDataSet>", "\n");
        response = response.replaceAll("<Table>", "\n");
        response = response.replaceAll("</Table>", "\n");
        response = response.replaceAll("</ElementName>", "\n");
        response = response.replaceAll("<ElementName>", "\n");
        response = response.replace("/", ""); 
        response = response.replace(" ", ""); 
        response = response.replace("\r", ""); 
        response = response.trim();
        return response.split("\n");
    }
    private static String getAtoms() {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtoms();
    }
    private static void getAllAtom() {
        String[] atomicNumber = parseResponseGroup(getAtoms());
        for (int i = 0; i< atomicNumber.length;i+=7) {
            String pa = daPeso(atomicNumber[i]);
            String nu = daNum(atomicNumber[i]);
            String sim = daSim(atomicNumber[i]);
            System.out.print("Elemento: "+ atomicNumber[i]+ ", con un peso atómico = " + pa + ", con un número atómico = " + nu);   
            System.out.println(", con el símbolo: "+ sim);
            System.out.println("\n");
        }
        atomicNumber = null;
        
    }    
   private static String daPeso(String name) {
        return parseResponse(getAtomicWeight(name), "</AtomicWeight>");
    }
    private static String daNum(String name) {
        return parseResponse(getAtomicNumber(name), "</AtomicNumber>");
    }
    private static String daSim(String name) {
       return parseResponse(getElementSymbol(name), "</Symbol>");
    }

    private static void obtainData() throws Exception {
        System.out.println("Por favor, ingrese el elemento en cuestión: ");  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ele = br.readLine();
        Serializer serializer = new Persister();
        String source = getAtomicNumber(ele);
        NewDataSet1 NewDataSet = new NewDataSet1();
        serializer.read(NewDataSet, source);
        System.out.println("Número atómico: "+NewDataSet.getTable().getAtomicNumber());
        //System.out.println(NewDataSet.getTable().getElementName());
        System.out.println("Símbolo: "+NewDataSet.getTable().getSymbol());
        System.out.println("Peso atómico: "+NewDataSet.getTable().getAtomicWeight());
        System.out.println("Punto de fusión: "+NewDataSet.getTable().getBoilingPoint());
        System.out.println("Potencial de ionización: "+NewDataSet.getTable().getIonisationPotential());
        System.out.println("Electronegatividad: "+NewDataSet.getTable().getEletroNegativity());
        System.out.println("Radio atómico: "+NewDataSet.getTable().getAtomicRadius());
        System.out.println("Punto de fusión: "+NewDataSet.getTable().getMeltingPoint());
        System.out.println("Densidad: "+NewDataSet.getTable().getDensity());
    }
}
