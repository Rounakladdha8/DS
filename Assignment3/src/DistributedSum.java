import mpi.MPI;

public class DistributedSum {

	public static void main(String[] args) {
		MPI.Init(args);
		int rank = MPI.COMM_WORLD.Rank(); // get the rank of current process
		int size = MPI.COMM_WORLD.Size(); // get the total number of processes

		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int n = arr.length;
		int local_n = n / size; // number of elements to be processed by each process
		int remainder = n % size;

		int[] local_arr = new int[local_n + (rank < remainder ? 1 : 0)];
		int offset = rank * local_n + Math.min(rank, remainder); // compute the offset for the current process
		for (int i = 0; i < local_arr.length; i++) {
			local_arr[i] = arr[offset + i];
		}

		int local_sum = 0; // compute the sum of the local elements
		for (int i = 0; i < local_arr.length; i++) {
			local_sum += local_arr[i];
		}

		int[] global_sums = new int[size]; // array to hold the global sum from each process
		MPI.COMM_WORLD.Allgather(new int[] { local_sum }, 0, 1, MPI.INT, global_sums, 0, 1, MPI.INT);

		// print the intermediate and final sums

		if (rank == 0) {
			System.out.println("Number of Processes Entered: " + size);
			System.out.println("\nIntermediate Sums:");
			int sum = 0;
			for (int i = 0; i < size; i++) {
				sum += global_sums[i];
				System.out.println("Process " + i + ": " + global_sums[i]);
			}
			System.out.println("Total Sum: " + sum);
		}
		MPI.Finalize();
	}
}
