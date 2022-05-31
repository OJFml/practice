/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Executors;

// Proyecto 3
// Juarez Cruz Oscar Daniel
// Sistemas Distribuidos - 4CM11
public class WebServer {
    private String[] listBooks = {
        "Adler,_Elizabeth__1991_._La_esmeralda_de_los_Ivanoff_[10057].txt",
        "Adler_Olsen,_Jussi__1997_._La_casa_del_alfabeto_[7745].txt",
        "Aguilera,_Juan_Miguel__1998_._La_locura_de_Dios_[5644].txt",
        "Alameddine,_Rabih__2008_._El_contador_de_historias_[5735].txt",
        "Albom,_Mitch__2002_._Martes_con_mi_viejo_profesor_[382].txt",
        "Alcott,_Louisa_May__1868_._Mujercitas_[11086].txt",
        "Alcott,_Louisa_May__1871_._Hombrecitos_[15392].txt",
        "Alders,_Hanny__1987_._El_tesoro_de_los_templarios_[13014].txt",
        "Alexander,_Caroline__1998_._Atrapados_en_el_hielo_[15727].txt",
        "Allende,_Isabel__1982_._La_casa_de_los_espíritus_[563].txt",
        "Allende,_Isabel__1984_._De_amor_y_de_sombra_[6283].txt",
        "Alten,_Steve__2001_.__Trilogía_maya_01__El_testamento_maya_[8901].txt",
        "Alten,_Steve__2008_._Al_borde_del_infierno_[12141].txt",
        "Amis,_Martin__1990_._Los_monstruos_de_Einstein_[8080].txt",
        "Anderson,_Sienna__2008_._No_me_olvides_[15047].txt",
        "Anónimo__1554_._Lazarillo_de_Tormes_[11043].txt",
        "Anónimo__2004_._Robin_Hood_[11853].txt",
        "Archer,_Jeffrey__1979_._Kane_y_Abel_[1965].txt",
        "Asimov,_Isaac__1950_._Yo,_robot_[10874].txt",
        "Asimov,_Isaac__1967_._Guía_de_la_Biblia__Antiguo_Testamento__[6134].txt",
        "Asimov,_Isaac__1985_._El_monstruo_subatómico_[167].txt",
        "Bach,_Richard__1970_._Juan_Salvador_Gaviota_[15399].txt",
        "Baum,_Lyman_Frank__1900_._El_Mago_de_Oz_[15715].txt",
        "Beevor,_Antony__1998_._Stalingrado_[10491].txt",
        "Benítez,_J._J.__1984_.__Caballo_de_Troya_01__Jerusalén_[4826].txt",
        "Dickens,_Charles__1843_._Cuento_de_Navidad_[3285].txt",
        "Dostoievski,_Fiódor__1865_._Crimen_y_castigo_[13400].txt",
        "Ende,_Michael__1973_._Momo_[1894].txt",
        "Esquivel,_Laura__1989_._Como_agua_para_chocolate_[7750].txt",
        "Flaubert,_Gustave__1857_._Madame_Bovary_[3067].txt",
        "Fromm,_Erich__1947_._El_miedo_a_la_libertad_[11619].txt",
        "Gaarder,_Jostein__1991_._El_mundo_de_Sofía_[6571].txt",
        "Gaiman,_Neil__2002_._Coraline_[1976].txt",
        "García_Márquez,_Gabriel__1967_._Cien_años_de_soledad_[8376].txt",
        "García_Márquez,_Gabriel__1985_._El_amor_en_los_tiempos_del_cólera_[874].txt",
        "García_Márquez,_Gabriel__1989_._El_general_en_su_laberinto_[875].txt",
        "Golding,_William__1954_._El_señor_de_las_moscas_[6260].txt",
        "Goleman,_Daniel__1995_._Inteligencia_emocional_[4998].txt",
        "Gorki,_Máximo__1907_._La_madre_[1592].txt",
        "Harris,_Thomas__1988_._El_silencio_de_los_inocentes_[11274].txt",
        "Hawking,_Stephen__1988_._Historia_del_tiempo_[8536].txt",
        "Hemingway,_Ernest__1952_._El_viejo_y_el_mar_[1519].txt",
        "Hesse,_Herman__1919_._Demian_[2612].txt",
        "Hitler,_Adolf__1935_._Mi_lucha_[11690].txt",
        "Hobbes,_Thomas__1651_._Leviatán_[2938].txt",
        "Huxley,_Aldous__1932_._Un_mundo_feliz_[293].txt"
    };    
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";

    private final int port;
    private HttpServer server;

    public static void main(String[] args) {
        int serverPort = Integer.parseInt( args[0] );
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String phrase = new String(requestBytes, StandardCharsets.UTF_8);
        searchWord(phrase);
        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("La operacion tomo %d nanosegundos", finishTime - startTime);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(null, exchange);
    }

    private void searchWord(String phrase) {
        InputStream ins = null;
        Reader r = null;
        BufferedReader br = null;
        String[] listWords = phrase.split(" ");
        LinkedList<InfoWord> listInfoWords = new LinkedList<InfoWord>();
        LinkedList<InfoBook> listInfoBooks = new LinkedList<InfoBook>();
        boolean flag = true;
        for (String word : listWords) {
            int books = 0;
            int[] timesBook = new int[listBooks.length];
            int iterBook = 0;
            for (String book : listBooks) {
                int count = 0;
                try {
                    String s;
                    int wordsInBook = 0;
                    ins = new FileInputStream("books/" + book);
                    r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
                    br = new BufferedReader(r);
                    while ((s = br.readLine()) != null) {
                        String line = s.toUpperCase();
                        String pattern =  word.toUpperCase();
                        if(flag)
                            wordsInBook += line.split("\\s+|\n").length;
                        int lastIndex = 0;
                        while(lastIndex != -1){
                            lastIndex = line.indexOf(pattern, lastIndex);
                            if(lastIndex != -1){
                                count++;
                                lastIndex += pattern.length();
                            }
                        } 
                    }
                    // System.out.println("La palabra: " + word + " aparece " + count + " veces" + " en el libro " + book);
                    if( count > 0 )
                        books++;
                    listInfoBooks.add( new InfoBook(book, wordsInBook, 0.0));
                    timesBook[iterBook] = count;
                    iterBook++;
                }
                catch (Exception e){
                    System.out.println("Exeption");
                    System.err.println(e.getMessage()); // handle exception
                }
                finally {
                    if (br != null) { try { br.close(); } catch(Throwable t) { /* ensure close happens */ } }
                    if (r != null) { try { r.close(); } catch(Throwable t) { /* ensure close happens */ } }
                    if (ins != null) { try { ins.close(); } catch(Throwable t) { /* ensure close happens */ } }
                }
                
            }
            flag = false;
            listInfoWords.add( new InfoWord(timesBook, word, books) );
        }
        
        for (int i = 0; i < listBooks.length; i++) {
            InfoBook auxBook = listInfoBooks.get(i);
            int words = auxBook.getWords();
            double pfSum = 0;
            for (InfoWord infoWord : listInfoWords) {
                double pf = infoWord.calculate(i, listBooks.length, words);
                pfSum += pf;
            }
            auxBook.setPf(pfSum);
            if(pfSum > 0)
                System.out.println(auxBook.getBook());            
        }
        Collections.sort(listInfoBooks, Comparator.comparingDouble(obj -> ((InfoBook) obj).getPf()).reversed());
        for (InfoBook infoBook : listInfoBooks) {
            System.out.println("Libro" + infoBook.getBook() + " - " + " PF: " + infoBook.getPf());
        }
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor esta vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}