import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ChatClient
{
    private static final String LOG_FILE = "ChatHistory.txt";

    public static void main(String A[]) throws Exception
    {
        System.out.println("Client Application is running...");

        Socket sobj = new Socket("localhost", 2100);

        System.out.println("Connection is successful with server");

        PrintStream pobj = new PrintStream(sobj.getOutputStream());   // Bolnara

        BufferedReader bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));   // aiknara

        BufferedReader bobj2 = new BufferedReader(new InputStreamReader(System.in));  // keyboard kadun input ghenara

        System.out.println("--------------------------------------"); // Banner for decoration
        System.out.println("-------Marvellous Chat Client-------");
        System.out.println("--------------------------------------"); // Banner for decoration

        // Thread that keeps listening for server messages independently,
        // so client can also type and send messages without waiting for server's reply
        Thread listener = new Thread(() ->
        {
            try
            {
                String str2;
                while((str2 = bobj1.readLine()) != null)
                {
                    System.out.println("Server says : " + str2);
                    logMessage("Server", str2);

                    if(str2.equalsIgnoreCase("end"))
                    {
                        System.out.println("Server ended the chat.");
                        System.exit(0);
                    }
                }
            }
            catch(IOException e)
            {
                System.out.println("Connection closed by server.");
            }
        });
        listener.start();

        String str1;
        System.out.println("Enter message for Server : ");

        while(!(str1 = bobj2.readLine()).equalsIgnoreCase("end"))
        {
            pobj.println(str1);
            System.out.println("Enter message for Server : ");
        }

        pobj.println("end");
        System.out.println("You ended the chat.");

        sobj.close();
    }

    // Appends every chat message to ChatHistory.txt with sender name and timestamp
    private static synchronized void logMessage(String sender, String message)
    {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = "[" + timestamp + "] " + sender + " : " + message;

        try(FileWriter fw = new FileWriter(LOG_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw))
        {
            pw.println(entry);
        }
        catch(IOException e)
        {
            System.out.println("Failed to write to log file : " + e.getMessage());
        }
    }
}
