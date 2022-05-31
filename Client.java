import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.util.LinkedList;
import java.util.concurrent.Future;

// Proyecto 3
// Juarez Cruz Oscar Daniel
// Sistemas Distribuidos - 4CM11
public class Client extends Thread {
  private String uri;
  private LinkedList<String> listWords;

  public Client(String uri, LinkedList<String> listWords){
      this.uri = uri;
      this.listWords = listWords;
  }

  @Override
  public void run() {
    HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).build();    
    while(listWords.size() > 0){
          if( listWords.size() > 0 ){
            String word = listWords.pop();
            HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofByteArray(word.getBytes(StandardCharsets.UTF_8)))
                .uri(URI.create("http://" + uri + "/task"))
                .build();
            Future<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body);
            try{
              System.out.println( uri + " - "+ response.get());
            }catch(Exception e){
              e.printStackTrace();
            }
          }
      }
  }
}