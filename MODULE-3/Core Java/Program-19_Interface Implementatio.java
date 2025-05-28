// Program 19: Interface Implementation
interface Playable {
    void play();
}

class Guitar implements Playable {
    public void play() {
        System.out.println("Strumming the guitar");
    }
}

class Piano implements Playable {
    public void play() {
        System.out.println("Playing the piano");
    }

    public static void main(String[] args) {
        Playable guitar = new Guitar();
        Playable piano = new Piano();
        guitar.play();
        piano.play();
    }
}