
import java.util.Random;
import java.util.Scanner;

public class TokenRing {
	private int n; // Number of processes
	private int[] state;
	private boolean token;

	public TokenRing(int n) {
		this.n = n;
		state = new int[n];
	}

	public void run() {
		// Initialize the ring
		for (int i = 0; i < n; i++) {
			state[i] = 0; // not in critical section
		}
		// Randomly select the process that will hold the token initially
		int curr = new Random().nextInt(n);
		token = true;
		while (true) {
			if (token) {
				if (state[curr] == 0) { // not in critical section
					enterCritical(curr);
					state[curr] = 1; // in critical section
				} else { // in critical section
					exitCritical(curr);
					state[curr] = 0; // not in critical section
					curr = (curr + 1) % n; // pass the token
					token = true;
				}
			}
		}
	}

	private void enterCritical(int id) {
		// Enter critical section
		System.out.println("Process " + id + " enters critical section");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e);
		}
	}

	private void exitCritical(int id) {
		// Exit critical section
		System.out.println("Process " + id + " exits critical section");
		token = false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e);
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("How many processes would you like to create?\nPlease enter a number: ");
		int n = sc.nextInt();
		sc.close();
		TokenRing tr = new TokenRing(n); // Instantiate a ring of n processes
		tr.run(); // Run the token ring algorithm
	}
}
