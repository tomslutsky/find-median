import java.util.Random;

class MaxHeap{
	private int[] heap = new int[1024];
	private int heapSize = 0;

	private int parent(int pos){
		return (int)Math.floor(pos/2);
	}

	private int left(int pos){
		return 2*pos;
	}

	private int right(int pos){
		return 2*pos+1;
	}

	private void maxHeapify(int pos){
		int largest, temp;
		int left = left(pos);
		int right = right(pos);
		if(left <= heapSize && heap[left] > heap[pos]){
			largest = left;
		} else {
			largest = pos;
		}
		if(right <= heapSize && heap[right] > heap[largest]){
			largest = right;
		}
		if(largest != pos){
			temp = heap[pos];
			heap[pos] = heap[largest];
			heap[largest] = temp;
			maxHeapify(largest);
		}
	}

	private void increaseKey(int pos, int value){
		int temp;
		if(value < heap[pos]){
			System.out.println("new value is smaller the current value");
		}
		heap[pos] = value;
		while(pos > 0 && heap[parent(pos)] < heap[pos]){
			temp = heap[pos];
			heap[pos] = heap[parent(pos)];
			heap[parent(pos)] = temp;
			pos = parent(pos);
		}
	}

	public void insert(int value){
		heapSize++;
		heap[heapSize-1] = Integer.MIN_VALUE;
		increaseKey(heapSize-1, value);
	}

	public void printHeap(){
		int i;
		for(i=0; i<heapSize; i++){
			System.out.println(heap[i]);
		}
	}

	public int getHeapsize(){
		return heapSize;
	}

	public int getMax(){
		return heap[0];
	}

	public int extractMax(){
		int max;
		if(heapSize<=0){
			System.out.println("heap underflow");
		}
		max = heap[0];
		heap[0] = heap[heapSize-1];
		heapSize--;
		maxHeapify(0);
		return max;
	}
}




class MinHeap{
	private int[] heap = new int[1024];
	private int heapSize = 0;

	private int parent(int pos){
		return (int)Math.floor(pos/2);
	}

	private int left(int pos){
		return 2*pos;
	}

	private int right(int pos){
		return 2*pos+1;
	}

	private void minHeapify(int pos){
		int smallest, temp;
		int left = left(pos);
		int right = right(pos);
		if(left <= heapSize && heap[left] < heap[pos]){
			smallest = left;
		} else {
			smallest = pos;
		}
		if(right <= heapSize && heap[right] < heap[smallest]){
			smallest = right;
		}
		if(smallest != pos){
			temp = heap[pos];
			heap[pos] = heap[smallest];
			heap[smallest] = temp;
			minHeapify(smallest);
		}
	}

	private void decreaseKey(int pos, int value){
		int temp;
		if(value > heap[pos]){
			System.out.println("new value is smaller the current value");
		}
		heap[pos] = value;
		while(pos > 0 && heap[parent(pos)] > heap[pos]){
			temp = heap[pos];
			heap[pos] = heap[parent(pos)];
			heap[parent(pos)] = temp;
			pos = parent(pos);
		}
	}

	public void insert(int value){
		heapSize++;
		heap[heapSize-1] = Integer.MAX_VALUE;
		decreaseKey(heapSize-1, value);
	}

	public void printHeap(){
		int i;
		for(i=0; i<heapSize; i++){
			System.out.println(heap[i]);
		}
	}

	public int getHeapsize(){
		return heapSize;
	}

	public int getMin(){
		return heap[0];
	}

	public int extractMin(){
		int min;
		if(heapSize<=0){
			System.out.println("heap underflow");
		}
		min = heap[0];
		heap[0] = heap[heapSize-1];
		heapSize--;
		minHeapify(0);
		return min;
	}
}

class DynamicMedian{
	// max heap contains upper bound of n/2 elements
	// min heap contains lower bound of n/2 elements
	private MaxHeap smallerHeap = new MaxHeap();
	private MinHeap largerHeap = new MinHeap();
	private int size = 0;

	public void insert(int value){
		int temp, med;
		if(size == 0){
			smallerHeap.insert(value);
			size++;
		} else {
			med = smallerHeap.getMax();
			size++;
			if(value > med){
				largerHeap.insert(value);
				if(smallerHeap.getHeapsize() < largerHeap.getHeapsize()){
					temp = largerHeap.extractMin();
					smallerHeap.insert(temp);
				}
			} else {
				smallerHeap.insert(value);
				if(smallerHeap.getHeapsize() > largerHeap.getHeapsize()+1){
					temp = smallerHeap.extractMax();
					largerHeap.insert(temp);
				}
			}
		}
	}

	public int median(){
		return smallerHeap.getMax();
	}

	public void printHeaps(){
		System.out.println("\nsmaller heap is:");
		smallerHeap.printHeap();
		System.out.println("\nlarger heap is:");
		largerHeap.printHeap();
		System.out.println("\n-------------------------");
	}


}

class FindMedian{

	public static void med3(int[] arr, int n1, int n2, int n3){
		DynamicMedian med = new DynamicMedian();
		int i;
		for(i=0; i<arr.length; i++){
			med.insert(arr[i]);
			if(i==n1-1 || i == n2-1 || i == n3-1){
				System.out.println("median at breakpoint " + (1+i) + " is: " + med.median());
			}
		}		
	}

	public static void main(String args[]){
		int[] A = new int[200];
		int[] B = new int[400];
		int[] C = new int[800];
		int i, j, temp;
		Random rand = new Random();

		for(i=0; i<A.length;i++){
			A[i] = rand.nextInt(1023)+1;
		}

		for(i=0; i<B.length;i++){
			B[i] = rand.nextInt(1023)+1;
		}

		for(i=0; i<C.length;i++){
			C[i] = rand.nextInt(1023)+1;
		}

		System.out.println("\nArray A:");
		med3(A, 10, 50, 100);
		System.out.println("\nArray B :");
		med3(B, 10, 50, 100);
		System.out.println("\nArray C :");
		med3(C, 10, 50, 100);

		System.out.println("\nArray A:");
		med3(A, A.length/4, A.length/2, (3*A.length)/4);
		System.out.println("\nArray B:");
		med3(B, B.length/4, B.length/2, (3*B.length)/4);
		System.out.println("\nArray C:");
		med3(C, C.length/4, C.length/2, (3*C.length)/4);

	}
}
