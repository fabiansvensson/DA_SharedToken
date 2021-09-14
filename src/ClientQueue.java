public class ClientQueue {

    public ClientQueue() {
    }

    String[] nodes = new String[5000];
    private int front = -1;

    public ClientQueue getQueue() {
        return this;
    }

    public String front() {
        return nodes[front];
    }

    public String get(int index) {
        if(index < 0 || index > front) return null;
        return nodes[index];
    }

    public boolean add(String value) {
        if(front >= 10000) return false;
        if(front >= nodes.length) {
            String aux[] = new String[nodes.length*2];
            for(int i = 0; i<nodes.length;i++) {
                aux[i] = nodes[i];
            }
            nodes = aux;
        }
        nodes[++front] = value;
        return true;
    }

    public String pop() {
        if(front == -1) return null;
        return nodes[front--];
    }

    public int size() {
        return front;
    }

    public boolean isEmpty() {
        return front == -1;
    }


}
