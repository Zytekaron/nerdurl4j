package tk.zytekaron.nerdurl4j;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final NerdUrl nerdUrl = new NerdUrl();
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        while (true) {
            System.out.println("Please enter a URL to shorten:");
            String url = scanner.nextLine();
            if (url.isEmpty()) break;
            System.out.println("Shortened URL: " + nerdUrl.shorten(url).get());
        }
    }
}