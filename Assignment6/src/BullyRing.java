import java.util.Scanner;

// create process class for creating a process having id and status 
class Process {
	public int id;
	public String status;

	public Process(int id) {
		this.id = id;
		this.status = "active";
	}
}

public class BullyRing {
	private Scanner sc;
	private Process[] processes;
	private int n;

	// initialize Scanner class object in constructor
	public BullyRing() {
		sc = new Scanner(System.in);
	}

	// create ring() method for initializing the ring
	public void ring() {
		// get input from the user for processes
		System.out.print("Enter total number of processes: ");
		n = sc.nextInt();
		// initialize processes array
		processes = new Process[n];
		for (int i = 0; i < n; i++) {
			processes[i] = new Process(i);
		}
	}

	// create election() method for electing process
	public void performElection() {
		// we use the sleep() method to stop the execution of the current thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// show failed process
		int maxValue = getMaxValue();
		System.out.println("Process " + processes[maxValue].id + " fails");
		// change status to Inactive of the failed process
		processes[maxValue].status = "Inactive";
		int idOfInitiator = 0;
		boolean overStatus = true;
		while (overStatus) {
			boolean higherProcesses = false;
			// iterate all the processes
			System.out.println();
			for (int i = idOfInitiator + 1; i < n; i++) {
				if (processes[i].status.equals("active")) {
					System.out.println("Process " + idOfInitiator + " Passes Election(" + idOfInitiator
							+ ") message to Process " + i);
					higherProcesses = true;
				}
			}
			// check for higher process
			if (higherProcesses) {
				System.out.println();
				for (int i = idOfInitiator + 1; i < n; i++) {
					if (processes[i].status.equals("active")) {
						System.out
								.println("Process " + i + " passes Ok(" + i + ") message to Process " + idOfInitiator);
					}
				}
				idOfInitiator++;
			} else {
				// get the last process from the processes that will become coordinator
				int coordinator = processes[getMaxValue()].id;
				// show process that becomes the coordinator
				System.out.println("Finally Process " + coordinator + " Becomes Coordinator");
				for (int i = coordinator - 1; i >= 0; i--) {
					if (processes[i].status.equals("active")) {
						System.out.println("Process " + coordinator + " passes Coordinator(" + coordinator
								+ ") message to Process " + i);
					}
				}
				System.out.println("\nEnd of Election");
				overStatus = false;
			}
		}
	}

	// create getMaxValue() method that returns index of max process
	private int getMaxValue() {
		int mxId = Integer.MIN_VALUE;
		int mxIdIndex = 0;
		for (int i = 0; i < processes.length; i++) {
			if (processes[i].status.equals("active") && processes[i].id > mxId) {
				mxId = processes[i].id;
				mxIdIndex = i;
			}
		}
		return mxIdIndex;
	}

	public static void main(String[] args) {
		BullyRing bully = new BullyRing();
		bully.ring();
		bully.performElection();
	}
}
