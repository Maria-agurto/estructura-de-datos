package grupo1.estructura.hash;

public class TablaHash<K, V> {
    private static final int DEFAULT_CAPACITY = 10;
    private HashNode<K, V>[] buckets;
    private int size;

    // Nodo interno para el encadenamiento (Listas Enlazadas)
    private static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public TablaHash() {
        buckets = new HashNode[DEFAULT_CAPACITY];
        size = 0;
    }

    // Función de dispersión (Hash Function)
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % buckets.length;
    }

    // Método de Inserción O(1) promedio
    public void insertar(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = buckets[bucketIndex];

        // Verificar si la llave ya existe para actualizar su valor
        HashNode<K, V> temp = head;
        while (temp != null) {
            if (temp.key.equals(key)) {
                temp.value = value;
                return;
            }
            temp = temp.next;
        }

        // Insertar nuevo nodo al inicio de la lista enlazada (Bucket)
        size++;
        newNodeSetup(bucketIndex, key, value);
    }

    private void newNodeSetup(int bucketIndex, K key, V value) {
        HashNode<K, V> head = buckets[bucketIndex];
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = head;
        buckets[bucketIndex] = newNode;
    }

    // Método de Búsqueda O(1) promedio
    public V buscar(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = buckets[bucketIndex];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null; // Retorna nulo si la llave no existe
    }

    public int getSize() {
        return size;
    }
}
