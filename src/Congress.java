public class Congress {

	private State[] queue;
	private int numberOfStates;
	private int seatsAvailable;
	private int N;
	private int capacity;

	public Congress() {
		N = 0;
		capacity = 10;
		queue = new State[10];
	}

	
	public void allocateSeat() {
		queue[1].giveSeat();
		seatsAvailable--;
		sink(1);
	}
	
	private void resize(int size) {
		State[] newQueue = new State[size];
		for (int i = 0; i < N + 1; i++) {
			newQueue[i] = queue[i];
		}
		queue = newQueue;
		capacity = size;
	}

	public void insert(State s) {
		if (N + 1 == capacity)
			resize(2 * capacity);
		N++;
		queue[N] = s;
		seatsAvailable--;
		swim(N);
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(j,j+1)) j++;
			if (less(k,j) == false) break;
			exch(k, j);
			k = j;
		}
	}
	
	private  void exch(int k, int j) {
		State  t = queue[k];
		queue[k] = queue[j];
		queue[j] = t;
	}
	
	private boolean less(int v, int W) {
		return queue[v].compareTo(queue[W]) < 0;
	}
	
	public int getCapacity() { return capacity;	}
	public int getSize() { return N; }
	
	public void printQueue() {
		
		for (int i = 1; i < N + 1; i++) {
			StdOut.println(queue[i].toString());
		}
		
		StdOut.println();
		
	}

	public static void main(String[] args) {

		Congress c = new Congress();

		c.numberOfStates = StdIn.readInt();
		c.seatsAvailable = StdIn.readInt();

		while (!StdIn.isEmpty()) {
			StdIn.readLine();
			c.insert(new State(StdIn.readLine().trim(), StdIn.readInt()));
		}
		
		while(c.seatsAvailable > 0) {
			// allocate seat to top
			c.allocateSeat();
		}

		c.printQueue();
	}

}

class State implements Comparable<State> {
	private String name;
	private long population;
	private int seats;
	private double D;
	private double PD;

	State(String name, long population) {
		this.name = name;
		this.population = population;
		seats = 1;
		D = Math.sqrt(seats * (seats + 1));
		PD = population / D;
	}
	
	public String toString() {
		String s = "Name: " + name + " Seats: " + seats;
		return s;
	}
	
	public void giveSeat() {
		seats++;
		D = Math.sqrt(seats * (seats + 1));
		PD = population / D;
	}

	@Override
	public int compareTo(State o) {
		if (this.PD > o.PD) {
			return 1;
		} else if (this.PD < o.PD) {
			return -1;
		} else {
			return 0;
		}
	}
}
