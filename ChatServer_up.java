import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ChatServer
{
    private static final String LOG_FILE = "ChatHistory.txt";

    public static void main(String A[]) throws Exception
    {
        System.out.println("Server Application is running...");

        ServerSocket ssobj = new ServerSocket(2100);

        System.out.println("Server is waiting at port 2100");

        Socket sobj = ssobj.accept();

        System.out.println("Client request get accepted successfully");

        PrintStream pobj = new PrintStream(sobj.getOutputStream());   // Bolnara

        BufferedReader bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));   // aiknara

        BufferedReader bobj2 = new BufferedReader(new InputStreamReader(System.in));  // keyboard kadun input ghenara

        System.out.println("--------------------------------------"); // Banner for decoration
        System.out.println("-------Marvellous Chat Server-------");
        System.out.println("--------------------------------------"); // Banner for decoration

        // Thread that keeps listening for client messages independently,
        // so server can also type and send messages without waiting for client
        Thread listener = new Thread(() ->
        {
            try
            {
                String str1;
                while((str1 = bobj1.readLine()) != null)
                {
                    System.out.println("Client says : " + str1);
                    logMessage("Client", str1);

                    if(str1.equalsIgnoreCase("end"))
                    {
                        System.out.println("Client ended the chat.");
                        System.exit(0);
                    }
                }
            }
            catch(IOException e)
            {
                System.out.println("Connection closed by client.");
            }
        });
        listener.start();

        String str2;
        System.out.println("Enter message for Client : ");

        while(!(str2 = bobj2.readLine()).equalsIgnoreCase("end"))
        {
            pobj.println(str2);
            System.out.println("Enter message for Client : ");
        }

        pobj.println("end");
        System.out.println("You ended the chat.");

        sobj.close();
        ssobj.close();
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
